package com.tengen;

import com.mongodb.*;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.net.UnknownHostException;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 14/10/13
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldMongoDBSparkFreemarkerStyle {
    public static void main(String[] args) throws UnknownHostException {
        //Freemarker configuration
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                HelloWorldSparkFreemarkerStyle.class, "/");

        //Create an instance of MongoClient to connection
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        //Get a Database
        DB database = client.getDB("course");
        final DBCollection collection = database.getCollection("hello");

        //Defined a route for the slash route path of our website.
        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request,
                                 Response response) {
                //String writer that Freemarker processes the template into.
                StringWriter writer = new StringWriter();
                try {
                    //The handle method create a template for hello.ftl
                    Template helloTemplate = configuration.getTemplate("hello.ftl");

                    //get from DB
                    DBObject document = collection.findOne();

                    //Process the template, passing the document and the writer
                    helloTemplate.process(document, writer);

                } catch (Exception e) {
                    halt(500); //Spark should return a 500 error.
                    e.printStackTrace();
                }

                return writer;
            }
        });
    }
}
