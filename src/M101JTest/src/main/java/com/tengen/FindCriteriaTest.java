package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 22/10/13
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public class FindCriteriaTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("findCriteriaTest");

        //Drop remove all documents and features
        collection.drop();

        //insert 10 documents with a andom integer as the value of field "x"
        for (int i = 0; i < 10; i++) {
            collection.insert(
                    new BasicDBObject("x", new Random().nextInt(2))
                            .append("y", new Random().nextInt(100)));
        }


        //Document to find
        DBObject query = new BasicDBObject("x", 0)
                .append("y", new BasicDBObject("$gt", 10).append("$lt", 90));
        //QueryBuilder generate document to find
        QueryBuilder builder = QueryBuilder.start("x").is(0)
                .and("y").greaterThan(10).lessThan(90);

        System.out.println("\nCount: ");
        long count = collection.count(builder.get());
        System.out.println(count);

        System.out.println("\nFind all: ");
        DBCursor cursor = collection.find(builder.get());
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
