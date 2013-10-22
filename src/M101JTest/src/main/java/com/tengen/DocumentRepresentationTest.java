package com.tengen;

import com.mongodb.BasicDBObject;

import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 22/10/13
 * Time: 21:52
 * To change this template use File | Settings | File Templates.
 */
public class DocumentRepresentationTest {
    public static void main(String[] args) {
        //Create a document
        BasicDBObject doc = new BasicDBObject();
        doc.put("userName","jyemin");
        doc.put("birthDate", new Date(234832423));
        doc.put("programmer",true);
        doc.put("age",8);
        doc.put("languajes", Arrays.asList("Java","C++"));
        //Subdocuments
        doc.put("address", new BasicDBObject("street","20 Main")
                .append("town","Westfield")
                .append("zip","45678"));

    }
}
