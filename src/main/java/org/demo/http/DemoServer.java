package org.demo.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DemoServer {
    public static void main(String[] args) throws Exception {
        final int port = 8000;
        HttpServer server = HttpServer.create( new InetSocketAddress("0.0.0.0", port), 10);
        server.createContext("/stats", new MyHandler());

        System.out.println("Server starting on port: " + port);
        server.start();
    }
}

class MyHandler implements HttpHandler {

    private int hits;

    public void handle(HttpExchange t) throws IOException {
        String str = "Hello World! " + (++hits);
        t.sendResponseHeaders(200, str.length());
        t.getResponseBody().write(str.getBytes());
        t.getResponseBody().close();
        t.close();
    }
}