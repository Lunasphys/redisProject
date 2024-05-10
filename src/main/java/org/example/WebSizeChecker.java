package org.example;

import redis.clients.jedis.Jedis;
import java.net.*;
import java.io.*;

public class WebSizeChecker {
    private final Jedis jedis;

    public WebSizeChecker(String redisHost, int redisPort) {
        this.jedis = new Jedis(redisHost, redisPort);
    }

    public int getPageSize(String url) throws IOException {
        if (jedis.exists(url)) {
            return Integer.parseInt(jedis.get(url));
        } else {
            URL website = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
            connection.setRequestMethod("GET");
            int contentLength = connection.getContentLength();
            jedis.setex(url, 3600, String.valueOf(contentLength));
            return contentLength;
        }
    }

    public static void main(String[] args) throws IOException {
        WebSizeChecker checker = new WebSizeChecker("localhost", 6379);
        System.out.println("Page size: " + checker.getPageSize("https://example.com"));
    }
}
