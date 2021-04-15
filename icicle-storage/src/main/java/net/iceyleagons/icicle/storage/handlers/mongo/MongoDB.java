package net.iceyleagons.icicle.storage.handlers.mongo;

import com.google.common.base.Preconditions;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import net.iceyleagons.icicle.reflect.Reflections;
import net.iceyleagons.icicle.storage.AbstractStorageHandler;
import net.iceyleagons.icicle.storage.container.ContainerField;
import net.iceyleagons.icicle.storage.container.StorageContainer;
import org.bson.Document;

import static net.iceyleagons.icicle.storage.handlers.mongo.MongoHelpers.getIDFilter;

public class MongoDB extends AbstractStorageHandler {

    private final String host;
    private final String database;
    private final String username;
    private final String password;

    private MongoClient mongoClient;

    public MongoDB(String host, String database, String username, String password) {
        this.host = host;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    private MongoDatabase getDatabase() throws IllegalStateException {
        Preconditions.checkNotNull(mongoClient, new IllegalStateException("Cannot get database if client is null! Please initialize the StorageHandler!"));

        return mongoClient.getDatabase(database);
    }

    @Override
    public void init() throws Exception {
        MongoCredential credential = MongoCredential.createScramSha1Credential(username, database, password.toCharArray());
        mongoClient = new MongoClient(new ServerAddress(host),
                credential, MongoClientOptions.builder().build());
    }

    @Override
    public void cleanup() {
        mongoClient.close();
        mongoClient = null;
    }

    @Override
    public Object get(Object id, String containerName, ContainerField idField, ContainerField[] fields) throws Exception {
        MongoDatabase database = getDatabase();
        MongoCollection<Document> collection = database.getCollection(containerName);
        Document result = collection.find(getIDFilter(id, idField.getName())).first();
        if (result == null) return null;

        Object parent = idField.getField().getDeclaringClass().getConstructor().newInstance();

        for (ContainerField containerField : fields) {
            Object object = result.get(containerField.getName());

            if (containerField.getType().isInstance(object)) {
                Reflections.set(containerField.getField(), parent, object);
            } else throw new IllegalStateException("Cannot match type of stored object to field.");

        }


        return parent;
    }

    @Override
    public void save(String containerName, ContainerField idField, Object toSave, ContainerField[] fields) {
        Document document = new Document();
        Object id = Reflections.get(idField.getField(), Object.class, toSave);

        document.append(idField.getName(), id);
        for (ContainerField containerField : fields) {
            document.append(containerField.getName(), Reflections.get(containerField.getField(), Object.class, toSave));
        }

        MongoCollection<Document> mongoCollection = getDatabase().getCollection(containerName);
        mongoCollection.findOneAndUpdate(getIDFilter(id, idField.getName()), document);
    }

    @Override
    public void create(StorageContainer storageContainer) throws Exception {
        MongoDatabase mongoDatabase = getDatabase();

        if (!MongoHelpers.collectionExists(storageContainer.getName(), mongoDatabase)) {
            mongoDatabase.createCollection(storageContainer.getName(), new CreateCollectionOptions());
        }
    }
}
