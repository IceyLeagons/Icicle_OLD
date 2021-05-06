package net.iceyleagons.icicle.common.storage.handlers.sql;

import net.iceyleagons.icicle.common.storage.handlers.AbstractSQLHandler;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLite extends AbstractSQLHandler {

    private final File databaseFile;

    /**
     * @param databaseFile the databaseFile
     */
    public SQLite(File databaseFile) {
        this.databaseFile = databaseFile;
    }

    @Override
    public void init() throws Exception {
        Class.forName("org.sqlite.JDBC");
    }

    @Override
    public void cleanup() { }

    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
                String.format("jdbc:sqlite:%s", databaseFile.getAbsolutePath()));
    }
}
