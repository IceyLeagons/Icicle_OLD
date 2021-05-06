package net.iceyleagons.icicle.common.storage.handlers;

import com.google.common.base.Preconditions;
import net.iceyleagons.icicle.common.reflect.Reflections;
import net.iceyleagons.icicle.common.storage.AbstractStorageHandler;
import net.iceyleagons.icicle.common.storage.container.ContainerField;
import net.iceyleagons.icicle.common.storage.container.StorageContainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public abstract class AbstractSQLHandler extends AbstractStorageHandler {


    public abstract String getDatabaseName();
    public abstract Connection openConnection() throws SQLException;

    @Override
    public Object get(Object id, String containerName, ContainerField idField, ContainerField[] fields) throws Exception{
        try (Connection connection = openConnection()) {
            Class<?> clazz = idField.getField().getDeclaringClass();

            Object response = null;

            String query = getDatabaseName() == null ? String.format("SELECT DISTINCT * FROM %s WHERE %s = ?", containerName, idField.getName()) :
                    String.format("SELECT DISTINCT * FROM %s.%s WHERE %s = ?", getDatabaseName(), containerName, idField.getName());

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setObject(1, id);

                ResultSet rs = preparedStatement.executeQuery();
                if (rs.first()) {
                    response = clazz.getDeclaredConstructor().newInstance();

                    Reflections.set(idField.getField(), response, id);
                    for (ContainerField containerField : fields) {
                        Reflections.set(containerField.getField(), response, rs.getObject(containerField.getName()));
                    }

                }
            }
            return response;
        }
    }

    @Override
    public void save(String containerName, ContainerField idField, Object toSave, ContainerField[] fields) throws Exception {
        Object id = Reflections.get(idField.getField(), Object.class, toSave);
        boolean update = get(id, containerName, idField, fields) != null;

        try (Connection connection = openConnection()) {


            String query = getInsertQuery(idField, containerName, fields, update);
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                if (!update) {
                    preparedStatement.setObject(1, id);

                    for (int i = 2; i - 2 < fields.length; i++) {
                        preparedStatement.setObject(i, Reflections.get(fields[i - 2].getField(), Object.class, toSave));
                    }
                } else {
                    for (int i = 1; i - 1 < fields.length; i++) {
                        preparedStatement.setObject(i, Reflections.get(fields[i - 1].getField(), Object.class, toSave));
                    }
                    preparedStatement.setObject(fields.length + 1, id);
                }

                preparedStatement.executeUpdate();
            }
        }
    }

    @Override
    public void create(StorageContainer storageContainer) throws Exception{
        String query = getCreateTableQuery(storageContainer);
        if (query == null) return;


        try (Connection connection = openConnection()) {
            connection.prepareStatement(query).executeUpdate();
        }

        //IF OBJECT_ID(N'dbo.Cars', N'U') IS NULL BEGIN   CREATE TABLE dbo.Cars (Name varchar(64) not null); END;
    }

    private String getInsertQuery(ContainerField idField, String containerName, ContainerField[] fields, boolean update) {
        if (update) {
            String query = getDatabaseName() == null ? "UPDATE %s SET %s WHERE %s = ?" : "UPDATE %s.%s SET %s WHERE %s = ?";
            StringBuilder stringBuilder = new StringBuilder();
            for (ContainerField field : fields) {
                stringBuilder.append(field.getName()).append(" = ?, ");
            }

            return getDatabaseName() == null ? String.format(query, containerName, removeChar(stringBuilder.toString(), 2), idField.getName()) :
                    String.format(query, getDatabaseName(), containerName, removeChar(stringBuilder.toString(), 2), idField.getName());
        }


        String query = getDatabaseName() == null ? "INSERT INTO %s (%s) VALUES (%s)" : "INSERT INTO %s.%s (%s) VALUES (%s)";
        return getDatabaseName() == null ? String.format(query, containerName, getInsertQueryParamNames(idField, fields), getInsertQueryParamValues(fields)) :
                String.format(query, getDatabaseName(), containerName, getInsertQueryParamNames(idField, fields), getInsertQueryParamValues(fields));
    }

    private String getInsertQueryParamNames(ContainerField idField, ContainerField[] fields) {
        StringBuilder query = new StringBuilder();
        query.append(idField.getName()).append(", ");

        for (ContainerField containerField : fields) {
            query.append(containerField.getName()).append(", ");
        }

        return removeChar(query.toString(), 2);
    }

    private String getInsertQueryParamValues(ContainerField[] fields) {
        StringBuilder query = new StringBuilder("?, ");
        for (ContainerField containerField : fields) {
            query.append("?").append(", ");
        }
        return removeChar(query.toString(), 2);
    }

    private String getCreateTableQuery(StorageContainer storageContainer) {
        String name = storageContainer.getName();
        ContainerField idField = storageContainer.getIdField();
        ContainerField[] fields = storageContainer.getFields();

        StringBuilder columns = new StringBuilder();


        String idParamType = SQLParamType.getSQLType(idField.getType());
        Preconditions.checkNotNull(idParamType, "Unsupported ID type!");
        columns.append(inTick(idField.getName())).append("").append(idParamType).append(" PRIMARY KEY, ");

        String start = "CREATE TABLE IF NOT EXISTS %s (%s);";

        for (ContainerField containerField : fields) {
            String fieldParamType = SQLParamType.getSQLType(containerField.getType());
            Preconditions.checkNotNull(idParamType, String.format("Unsupported param type at field named %s", containerField.getName()));
            columns.append(inTick(containerField.getName())).append(" ").append(fieldParamType).append(", ");
        }

        return String.format(start, inTick(name), removeChar(columns.toString(), 2));
    }

    private String inTick(String value) {
        return String.format("`%s`", value);
    }

    private String removeChar(String value, int amount) {
        return value.substring(0, value.length()-amount);
    }

    enum SQLParamType {
        STRING("LONGTEXT", String.class),
        INTEGER("INTEGER", Integer.class, int.class),
        BOOLEAN("TINYINT(1)", Boolean.class, boolean.class),
        LONG("BIGINT", Long.class, long.class);


        public final String sqlType;
        public final Class<?>[] javaTypes;

        SQLParamType(String sqlType, Class<?>... javaTypes) {
            this.sqlType = sqlType;
            this.javaTypes = javaTypes;
        }

        public static String getSQLType(Class<?> javaType) {
            Optional<SQLParamType> sqlParamTypeOptional = Arrays.stream(values()).filter(paramType -> Arrays.stream(paramType.javaTypes).anyMatch(javaType::isAssignableFrom)).findFirst();
            return sqlParamTypeOptional.map(sqlParamType -> sqlParamType.sqlType).orElse(null);
        }
    }
}
