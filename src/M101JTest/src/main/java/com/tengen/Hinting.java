package com.tengen;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 04/11/13
 * Time: 20:43
 * To change this template use File | Settings | File Templates.
 */
public class Hinting {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB db = client.getDB("test");

        BasicDBObject query = new BasicDBObject("a", 40000);
        query.append("b", 40000);
        query.append("c", 40000);

        DBCollection c = db.getCollection("foo");

        DBObject doc = c.find(query).hint("a_1_b_-1_c_1").explain();

        //  BasicDBObject myHint = new BasicDBObject("a",1).append("b",-1).append("c",1);
        //  DBObject doc = c.find(query).hint(myHint).explain();

        for(String s: doc.keySet()) {
            System.out.printf("%25s:%s\n", s, doc.get(s));
        }
    }
}
