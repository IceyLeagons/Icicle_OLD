/*
 * MIT License
 *
 * Copyright (c) 2020 IceyLeagons (Tamás Tóth and Márton Kissik) and Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.iceyleagons.icicle.storage.impl;

import lombok.SneakyThrows;
import net.iceyleagons.icicle.storage.Storage;
import net.iceyleagons.icicle.storage.StorageException;
import net.iceyleagons.icicle.storage.StorageType;
import net.iceyleagons.icicle.storage.entities.Container;
import net.iceyleagons.icicle.storage.entities.ContainerData;
import net.iceyleagons.icicle.storage.entities.DataType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Handles all SQL based databases
 *
 * @author TOTHTOMI
 * @version 1.0.0
 * @since  1.3.0-SNAPSHOT"
 */
public abstract class SQLDatabase extends Storage {

    private final String databaseName;
    protected Connection connection = null;

    /**
     *
     * @param databaseName the databaseName
     * @param type the {@link StorageType}
     * @param logger the {@link Logger} to use
     */
    public SQLDatabase(String databaseName, StorageType type, Logger logger) {
        super(type,logger);
        this.databaseName = databaseName;
    }

    /**
     * Documented in {@link Storage}
     */
    @Override
    protected abstract boolean init() throws StorageException;

    /**
     * Documented in {@link Storage}
     */
    @Override
    protected abstract boolean openConnection() throws StorageException;

    /**
     * Documented in {@link Storage}
     */
    @Override
    protected boolean closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                return true;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            return false;
        }
        return true;
    }

    /**
     * Closes the previous and returns a new fresh {@link Connection}
     *
     * @return the {@link Connection}
     */
    @SneakyThrows
    private Connection getConnection() {
        closeConnection();
        openConnection();
        return connection;
    }

    /**
     * Documented in {@link Storage}
     * Customized to fit the SQL database types
     */
    @Override
    public void deleteData(String key, Object value, String containerName) {
        String query;
        if (databaseName != null) query = String.format("DELETE FROM %s.%s WHERE ? = ?",databaseName,containerName);
        else query = String.format("DELETE FROM %s WHERE ? = ?",containerName);
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(0,key);
            preparedStatement.setObject(1,value);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Documented in {@link Storage}
     * Customized to fit the SQL database types
     */
    @Override
    public List<ContainerData> getData(Container container) {
        String containerName = container.getContainerName();
        List<ContainerData> containerData = new ArrayList<>();

        try {
            String query;
            if (databaseName != null) query = String.format("SELECT * FROM %s.%s WHERE 1",databaseName,containerName);
            else query = String.format("SELECT * FROM %s WHERE 1",containerName);
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                long id = resultSet.getLong("id");
                DataType[] dataTypes = container.getDataKeyTypes();

                Object[] values = new Object[dataTypes.length];
                for (int i = 0; i < dataTypes.length; i++) {
                    values[i] = resultSet.getObject(i+2);
                }
                containerData.add(new ContainerData(dataTypes,container.getDataKeyNames(),values,id));
            }

            getConnection().close();


        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return containerData;
    }

    private void applyInsertion(String query,List<ContainerData> insertList) {
        insertList.forEach(queueData -> { //looping through the insertMap
            try {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setLong(1,queueData.getId());
                DataType[] dataTypes = queueData.getDataTypes(); //Datatypes for the rows' columns'
                Object[] values = queueData.getValues(); //Values for the rows' columns'
                for (int i = 0; i < dataTypes.length; i++) {
                    DataType dataType = dataTypes[i];
                    Object value = values[i];
                    Object toEnter = dataType.getJavaRepresentation().cast(value);
                    //System.out.println(toEnter);

                    int toSet = i+2;
                    insert(dataType,toEnter,preparedStatement,toSet);
                }
                preparedStatement.executeUpdate();
                getConnection().close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        });
    }

    /**
     * Inserts data to the given {@link PreparedStatement}
     *
     * @param dataType the {@link DataType}
     * @param toEnter the value to insert
     * @param preparedStatement the {@link PreparedStatement}
     * @param toSet parameter indexs
     * @throws SQLException
     */
    private void insert(DataType dataType, Object toEnter, PreparedStatement preparedStatement, int toSet) throws SQLException {
        switch (dataType){
            case STRING:
                preparedStatement.setString(toSet,(String)toEnter);
                break;
            case BOOLEAN:
                preparedStatement.setBoolean(toSet,(Boolean)toEnter);
                break;
            case INTEGER:
                preparedStatement.setInt(toSet,(Integer)toEnter);
                break;
            case LONG:
                preparedStatement.setLong(toSet,(Long)toEnter);
                break;
            default:
                break;
        }
    }



    /**
     * Documented in {@link Storage}
     * Customized to fit the SQL database types
     */
    @Override
    public void applyChanges() {
        lastUpdated = System.currentTimeMillis();
        String startSchema;
        if (databaseName != null) startSchema = "REPLACE INTO %s.%s(id, %s) VALUES (?,%s)";
        else startSchema =  "REPLACE INTO %s(id, %s) VALUES (?,%s)";
        containerMap.forEach((tableName,container) -> { //looping through our queue
            if (!container.getQueue().isEmpty()) {
                List<ContainerData> queue = container.getQueue();
                createTableIfNotExists(tableName, queue); //creating table

                StringBuilder valuesBuilder = new StringBuilder();
                StringBuilder columnBuilder = new StringBuilder();

                List<ContainerData> insertList = new ArrayList<>(); //Map used to keep track of what we need to insert later on, first we create the query
                List<String> blacklist = new ArrayList<>(); //To prevent duplication

                queue.forEach(queueData -> { //looping through the data for a table to create query and to add to insertmap
                    String[] columns = queueData.getKeys();
                    for (String column : columns) { //looping through the columns
                        if (!blacklist.contains(column)) { //making sure we're not making duplicates
                            columnBuilder.append(column).append(", ");
                            valuesBuilder.append("?, ");
                            blacklist.add(column);
                        }
                    }

                    insertList.add(queueData); //ading to insertMap
                });

                String columns = columnBuilder.substring(0, columnBuilder.length() - 2);
                String values = valuesBuilder.substring(0, valuesBuilder.length() - 2);

                String query;
                if (databaseName != null) query = String.format(startSchema, databaseName, tableName, columns, values); //creating query
                else query = String.format(startSchema, tableName, columns, values); //creating query
                info("Running query " + query);

                applyInsertion(query, insertList); //handling insertion
                container.getQueue().clear();
            }
        });

    }

    /**
     * Creates tables if they're not exist from the given data list
     *
     * @param name the containerName
     * @param data the container's data
     */
    private void createTableIfNotExists(String name, List<ContainerData> data) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> blackList = new ArrayList<>(); //we need to keep track which key we have already put in to prevent Duplicate column names

        data.forEach(queueData -> { //looping through our data
            DataType[] dataTypes = queueData.getDataTypes(); //dataTypes for each data
            String[] keys = queueData.getKeys(); //columns for each data

            for (int i = 0; i < dataTypes.length; i++) {
                DataType dataType = dataTypes[i]; //dataType for a column
                String key = keys[i]; //column name

                if (!blackList.contains(key)) { //making sure not to have duplicate columns
                    stringBuilder.append(key).append(" ").append(dataType.getMysqlParameter()).append(", ");
                    blackList.add(key);
                }
            }
        });

        String values = stringBuilder.substring(0,stringBuilder.length()-2); //Removing the last comma and space
        String query;
        if (databaseName != null) query = String.format("CREATE TABLE IF NOT EXISTS %s.%s (id BIGINT PRIMARY KEY, %s)",databaseName,name,values); //Creating query
        else query = String.format("CREATE TABLE IF NOT EXISTS %s (id BIGINT PRIMARY KEY, %s)",name,values); //Creating query

        info("Running query " + query);
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query); //Creating prepared statement
            preparedStatement.executeUpdate(); //Running prepared statement
            getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
