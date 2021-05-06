package net.iceyleagons.icicle.common.storage.handlers.sql;

import net.iceyleagons.icicle.common.storage.handlers.AbstractSQLHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MariaDB extends AbstractSQLHandler {

    private final String host;
    private final String databaseName;
    private final String username;
    private final String password;

    /**
     * @param host         db host
     * @param databaseName db name
     * @param username     username used for authentication
     * @param password     password used for authentication
     */
    public MariaDB(String host, String databaseName, String username, String password) {
        this.host = host;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
    }

    @Override
    public void init() throws Exception {
        Class.forName("org.mariadb.jdbc.Driver");
    }

    @Override
    public void cleanup() { }

    @Override
    public String getDatabaseName() {
        return this.databaseName;
    }

    @Override
    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                String.format("jdbc:mariadb://%s/%s", host, databaseName), username, password);
    }
}
