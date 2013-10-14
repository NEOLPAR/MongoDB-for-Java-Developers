package com.tengen;

import static spark.Spark.*;
import spark.*;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 14/10/13
 * Time: 12:56
 * To change this template use File | Settings | File Templates.
 */
public class HelloWorldSparkStyle {
    public static void main(String[] args) {
        get(new Route("/") {
            @Override
            public Object handle(Request request,
                                 Response response) {
                return "Hello World from Spark";
            }
        });
    }

}
