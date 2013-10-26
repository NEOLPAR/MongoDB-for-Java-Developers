package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 22/10/13
 * Time: 23:44
 * To change this template use File | Settings | File Templates.
 */
public class FieldSelectionTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("fieldSelectionTest");

        //Drop remove all documents and features
        collection.drop();

        Random rand = new Random();

        //insert 10 documents with a andom integer as the value of field "x"
        for (int i = 0; i < 10; i++) {
            collection.insert(
                    new BasicDBObject("x", rand.nextInt(2))
                            .append("y", rand.nextInt(100))
                            .append("z", rand.nextInt(1000)));
        }


        //Document to find
        DBObject query = QueryBuilder.start("x").is(0)
                .and("y").greaterThan(10).lessThan(90).get();

        DBCursor cursor = collection.find(query,
                new BasicDBObject("y", true).append("_id", false));
        //All docs from DB with criteria
        //DBCursor cursor = collection.find(new BasicDBObject(),
        //      new BasicDBObject("y", true).append("_id", false));
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            //Always close cursor
            cursor.close();
        }

    }
}
