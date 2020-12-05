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

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import net.iceyleagons.icicle.storage.Storage;
import net.iceyleagons.icicle.storage.StorageType;
import net.iceyleagons.icicle.storage.entities.Container;
import net.iceyleagons.icicle.storage.entities.ContainerData;
import net.iceyleagons.icicle.storage.entities.DataType;
import org.bson.Document;

import javax.print.Doc;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

/**
 * @author TOTHTOMI
 */
public class MongoDB extends Storage {

    private final String host;
    private final String username;
    private final String password;
    private final String database;

    private MongoClient mongoClient;


    public MongoDB(String host, String username, String password, String database, Logger logger) {
        super(StorageType.MONGO_DB, logger);
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    /**
     * Documented in {@link Storage}
     */
    @Override
    protected boolean init() {
        return true;
    }

    /**
     * Documented in {@link Storage}
     */
    @Override
    protected boolean openConnection() {
        MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, password.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(host),
                Collections.singletonList(credential));
        return true;
    }

    /**
     * Documented in {@link Storage}
     */
    @Override
    protected boolean closeConnection() {
        mongoClient.close();
        mongoClient = null;
        return false;
    }

    /**
     * Opens a connection and returns a db with the given databaseName
     * @return the {@link MongoDatabase}
     */
    private MongoDatabase getDB() {
        if (mongoClient == null) openConnection();
        return mongoClient.getDatabase(database);
    }

    /**
     * Documented in {@link Storage}
     * Customized to fit MongoDB
     */
    @Override
    public void deleteData(String key, Object value, String containerName) {
        Document document = new Document();
        document.put(key,value);

        getDB().getCollection(containerName).findOneAndDelete(document);
        closeConnection();
    }

    /**
     * Documented in {@link Storage}
     * Customized to fit MongoDB
     */
    @Override
    public List<ContainerData> getData(Container container) {
        MongoDatabase db = getDB();
        MongoCollection<Document> dbCollection = db.getCollection(container.getContainerName());
        MongoCursor<Document> iterator = dbCollection.find().iterator();

        List<ContainerData> containerData = new ArrayList<>();

        while(iterator.hasNext()) {
            Document dbObject = iterator.next();
            DataType[] keyTypes = container.getDataKeyTypes();
            String[] keys = container.getDataKeyNames();
            Object[] values = new Object[keys.length];

            for (int i = 0; i < keys.length; i++) {
                values[i] = dbObject.get(keys[i]);
            }
            containerData.add(new ContainerData(keyTypes,keys,values,(long) dbObject.get("_id")));


        }
        closeConnection();
        return containerData;
    }

    /**
     * Documented in {@link Storage}
     * Customized to fit MongoDB
     */
    @Override
    public void applyChanges() {
        lastUpdated = System.currentTimeMillis();
        MongoDatabase database = getDB();
        containerMap.forEach((tableName,container) -> { //looping through our queue
            if (!container.getQueue().isEmpty()) {
                List<ContainerData> queue = container.getQueue();
                if (database.getCollection(tableName) == null)
                    database.createCollection(tableName,null);

                MongoCollection<Document> collection = database.getCollection(tableName);

                List<Long> blackListed = new ArrayList<>();

                for (ContainerData data : queue) {
                    if (blackListed.contains(data.getId())) continue;
                    Document document = new Document();
                    document.append("_id",data.getId());

                    String[] keys = data.getKeys();
                    Object[] values = data.getValues();
                    for (int i = 0; i < keys.length; i++) {
                        document.append(keys[i],values[i]);
                    }

                    Document filter = new Document();
                    document.append("_id",data.getId());

                    //collection.findAndModify(new BasicDBObject("_id",data.getId()),object);
                    getDB().getCollection(container.getContainerName()).findOneAndDelete(filter);
                    collection.insertOne(document);
                    blackListed.add(data.getId());
                }
                container.getQueue().clear();
            }
        });
        closeConnection();
    }
}
