package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 13/10/13
 * Time: 23:40
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldMongoDBStyle {
    public static void main(String[] args) throws UnknownHostException {
        //Create an instance of MongoClient to connection
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        //Get a Database
        DB database = client.getDB("course");
        DBCollection collection = database.getCollection("hello");

        DBObject document = collection.findOne();
        System.out.println(document);
    }
}
