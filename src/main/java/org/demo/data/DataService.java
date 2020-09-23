package org.demo.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private Connection connection;

    public DataService() {
        try {
            String hostname = System.getenv("MARIADB_HOSTNAME");
            if (hostname == null) {
                throw new RuntimeException("Environment MARIADB_HOSTNAME is not set");
            }
            String rootPassword = System.getenv("MARIADB_ROOT_PASSWORD");
            if (rootPassword == null) {
                throw new RuntimeException("Environment MARIADB_ROOT_PASSWORD is not set");
            }
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + ":3306",
                    "root", rootPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("failed to connect to mysql db", throwables);
        }
    }

    public static void main(String[] args) throws RuntimeException {
        DataService dataService = new DataService();
        System.out.println(dataService.getHelpKeywords());
    }

    public List<String> getHelpKeywords() throws RuntimeException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select * from mysql.help_keyword");
            resultSet = statement.executeQuery();
            List<String> keywords = new ArrayList<String>();
            while (resultSet.next()) {
                keywords.add(resultSet.getString("name"));
            }
            return keywords;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("failed to get help keywords", e);
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    System.err.println("Warning: failed to close resultset");
                    // ignore, we have tried our best
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    System.err.println("Warning: failed to close statement");
                    // ignore, we have tried our best
                }
            }
        }
    }
}
