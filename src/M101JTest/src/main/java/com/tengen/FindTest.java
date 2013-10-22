package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 22/10/13
 * Time: 22:35
 * To change this template use File | Settings | File Templates.
 */
public class FindTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("findTest");

        //Drop remove all documents and features
        collection.drop();

        //insert 10 documents with a andom integer as the value of field "x"
        for (int i = 0; i < 10; i++) {
            collection.insert(new BasicDBObject("x", new Random().nextInt(100)));
        }

        System.out.println("Find one: ");
        DBObject one = collection.findOne();
        System.out.println(one);

        System.out.println("\nFind all: ");
        DBCursor cursor = collection.find();
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            //Always close cursor
            cursor.close();
        }

        System.out.println("\nCount: ");
        long count = collection.count();
        System.out.println(count);

    }
}
