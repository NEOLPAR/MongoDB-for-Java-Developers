package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 26/10/13
 * Time: 22:29
 * To change this template use File | Settings | File Templates.
 */
public class DotNotationTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("DotNotationTest");

        //Drop remove all documents and features
        collection.drop();

        Random rand = new Random();

        //insert 10 documents with random start and end points
        for (int i = 0; i < 10; i++) {
            collection.insert(
                    new BasicDBObject("_id", i)
                            .append("start",
                                    new BasicDBObject("x", rand.nextInt(90) + 10)
                                            .append("y", rand.nextInt(90) + 10)
                            )
                            .append("end",
                                    new BasicDBObject("x", rand.nextInt(90)+10)
                                            .append("y", rand.nextInt(90) + 10)
                            )
            );
        }


        //Document to find
        QueryBuilder builder = QueryBuilder.start("start.x").greaterThan(50);

        //All docs from DB
        DBCursor cursor = collection.find(builder.get(),
                new BasicDBObject("start.y", true).append("_id", false));

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
