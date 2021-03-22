package net.iceyleagons.icicle.storage.handlers.mongo;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;

import java.util.HashSet;
import java.util.Set;

public class MongoHelpers {

    public static <T> Set<T> getFromMongoIterable(MongoIterable<T> iterable) {
        Set<T> set = new HashSet<>();
        for (T t : iterable) {
            set.add(t);
        }
        return set;
    }

    public static boolean collectionExists(final String collectionName, final MongoDatabase mongoDatabase) {
        Set<String> collectionNames = getFromMongoIterable(mongoDatabase.listCollectionNames());
        for (final String name : collectionNames) {
            if (name.equalsIgnoreCase(collectionName)) {
                return true;
            }
        }
        return false;
    }

    public static Document getIDFilter(Object id, String idName) {
        Document document = new Document();
        document.append(idName, id);
        return document;
    }
}
