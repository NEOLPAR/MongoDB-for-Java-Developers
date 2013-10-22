package com.tengen;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 22/10/13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class InsertTest {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB courseDB = client.getDB("course");
        DBCollection collection = courseDB.getCollection("insertTest");

        //Drop remove all documents and features
        collection.drop();

        //If we want to put an ID
        DBObject doc = new BasicDBObject("_id", new ObjectId()).append("x",1);
        //Default, Mongo will create an ID automatically
        //DBObject doc2 = new BasicDBObject().append("x",2);

        //System.out.println(doc);

        //collection.insert(Arrays.asList(doc, doc2));
        collection.insert(doc);
        collection.insert(doc); //Exception, duplicate key error

        //Doc has inserted and now have the _id field
        //System.out.println(doc);

    }
}
