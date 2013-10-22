package com.tengen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 14/10/13
 * Time: 14:19
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldSparkFreemarkerStyle {
    public static void main(String[] args) {
        //Freemarker configuration
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(
                HelloWorldSparkFreemarkerStyle.class, "/");

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
                    //Map with the name of the variable and value to match in the template
                    Map<String, Object> helloMap = new HashMap<String, Object>();
                    helloMap.put("name", "Freemarker");

                    //Process the template, passing the map and the writer
                    helloTemplate.process(helloMap, writer);

                } catch (Exception e) {
                    halt(500); //Spark should return a 500 error.
                    e.printStackTrace();
                }

                return writer;
            }
        });
    }
}
