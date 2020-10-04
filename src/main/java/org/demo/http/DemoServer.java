package org.demo.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.demo.data.MySqlDataService;
import org.demo.data.OracleDataService;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.List;

public class DemoServer {
    public static void main(String[] args) throws Exception {
        final int port = 8000;
        HttpServer server = HttpServer.create( new InetSocketAddress("0.0.0.0", port), 10);
        server.createContext("/stats", new MyHandler());
        server.createContext("/keywords", new KeywordsHandler());
        server.createContext("/tables", new TablesHandler());

        System.out.println("Server starting on port: " + port);
        server.start();
        System.out.println("Contexts created: /stats /keywords /tables");
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

class KeywordsHandler implements HttpHandler {
    private MySqlDataService mySqlDataService;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (mySqlDataService == null) {
                mySqlDataService = new MySqlDataService();
            }
            List<String> keywords = mySqlDataService.getHelpKeywords();
            String message = keywords.toString();
            httpExchange.sendResponseHeaders(200, message.length());
            httpExchange.getResponseBody().write(message.getBytes());
            httpExchange.getResponseBody().close();
            httpExchange.close();
        } catch (SQLException throwables) {
            String message = throwables.getMessage();
            httpExchange.sendResponseHeaders(500, message.length());
            httpExchange.getResponseBody().write(message.getBytes());
            httpExchange.getResponseBody().close();
            httpExchange.close();
        }
    }
}

class TablesHandler implements HttpHandler {
    private OracleDataService oracleDataService;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            if (oracleDataService == null) {
                oracleDataService = new OracleDataService();
            }
            List<String> tables = oracleDataService.getTables();
            String message = tables.toString();
            httpExchange.sendResponseHeaders(200, message.length());
            httpExchange.getResponseBody().write(message.getBytes());
            httpExchange.getResponseBody().close();
            httpExchange.close();
        } catch (SQLException throwables) {
            String message = throwables.getMessage();
            httpExchange.sendResponseHeaders(500, message.length());
            httpExchange.getResponseBody().write(message.getBytes());
            httpExchange.getResponseBody().close();
            httpExchange.close();
        }
    }
}