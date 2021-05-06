package net.iceyleagons.icicle.common.storage.handlers.sql;

import net.iceyleagons.icicle.common.storage.handlers.AbstractSQLHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends AbstractSQLHandler {

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
    public MySQL(String host, String databaseName, String username, String password) {
        this.host = host;
        this.databaseName = databaseName;
        this.username = username;
        this.password = password;
    }

    @Override
    public void init() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
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
                String.format("jdbc:mysql://%s/%s?" +
                        "useUnicode=true&" +
                        "useJDBCCompliantTimezoneShift=true&" +
                        "useLegacyDatetimeCode=false&" +
                        "serverTimezone=UTC",
                        host, databaseName), username, password);
    }
}
