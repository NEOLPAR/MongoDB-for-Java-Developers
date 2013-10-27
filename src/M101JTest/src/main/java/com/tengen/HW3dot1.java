package com.tengen;

import com.mongodb.*;
import com.mongodb.util.JSON;

import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 27/10/13
 * Time: 18:52
 * To change this template use File | Settings | File Templates.
 */
public class HW3dot1 {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("school");
        DBCollection collection = db.getCollection("students");

        DBCursor cursor = collection.find();

        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
                BasicDBList arrayList = (BasicDBList) cur.get("scores");
                BasicDBObject hw1 = (BasicDBObject) arrayList.get(2);
                BasicDBObject hw2 = (BasicDBObject) arrayList.get(3);
                if(hw1.getDouble("score") > hw2.getDouble("score")) {
                    arrayList.remove(3);

                } else {
                    arrayList.remove(2);
                }

                collection.update(new BasicDBObject("_id", cur.get("_id")), cur, false, false);
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }

    }
}
