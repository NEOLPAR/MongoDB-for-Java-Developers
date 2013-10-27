package com.tengen;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import freemarker.template.SimpleDate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: neolpar
 * Date: 27/10/13
 * Time: 17:49
 * To change this template use File | Settings | File Templates.
 */
public class ImportTweets {
    public static void main(String[] args) throws IOException, ParseException {
        final String screenName = args.length > 0 ? args[0] : "neolpar";

        List<DBObject> tweets = getLatestTweets(screenName);

        MongoClient client = new MongoClient();
        DBCollection tweetsCollection = client.getDB("course").getCollection("twitter");

        for (DBObject cur : tweets) {
            cur.put("screen_name", screenName);
            massageTweetId(cur);
            massageTweet(cur);
            /*with inser fail when the tweet is on DB, we will use update.
             *
             * tweetsCollection.insert(cur);
             */
            tweetsCollection.update(new BasicDBObject("_id", cur.get("_id")), cur, true, false);
        }

        System.out.println("Tweet count: " + tweetsCollection.count());

        client.close();
    }

    @SuppressWarnings("unchecked")
    private static List<DBObject> getLatestTweets (String screenName) throws IOException {
        URL url = new URL("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name=" + screenName + "&include_rts=1");

        InputStream is = url.openStream();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int retVal;
        while ((retVal = is.read()) != -1) {
            os.write(retVal);
        }

        final String tweetsString = os.toString();

        return (List<DBObject>) JSON.parse(tweetsString);
    }

    private static void massageTweetId(final DBObject cur) {
        Object id = cur.get("id");
        cur.removeField("id");
        cur.put("_id", id);
    }

    private static void massageTweet (final DBObject cur) throws ParseException {
        SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM d H:m:s Z y");
        cur.put("created_at", fmt.parse((String) cur.get("created_at")));

        DBObject userDoc = (DBObject) cur.get("user");
        Iterator<String> keyIterator = userDoc.keySet().iterator();
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            if (!(key.equals("id") || key.equals("name") || key.equals("screen_name"))) {
                keyIterator.remove();
            }
        }
    }

}
