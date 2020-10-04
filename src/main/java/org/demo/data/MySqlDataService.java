package org.demo.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlDataService {
    private Connection connection;

    public MySqlDataService() {
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

    public static void main(String[] args) throws SQLException {
        MySqlDataService mySqlDataService = new MySqlDataService();
        System.out.println(mySqlDataService.getHelpKeywords());
    }

    public List<String> getHelpKeywords() throws SQLException {
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
            throw e;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Warning: failed to close resultset");
                    // ignore, we have tried our best
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Warning: failed to close statement");
                    // ignore, we have tried our best
                }
            }
        }
    }
}
