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
            connection = DriverManager.getConnection("jdbc:mysql://" + hostname + "localhost:3306",
                    "root", rootPassword);
        } catch (SQLException throwables) {
            throw new RuntimeException("failed to connect to mysql db", throwables);
        }
    }

    public static void main(String[] args) throws SQLException {
        DataService dataService = new DataService();
        System.out.println(dataService.getHelpKeywords());
    }

    public List<String> getHelpKeywords() throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from mysql.help_keyword");
        ResultSet resultSet = statement.executeQuery();
        List<String> keywords = new ArrayList<String>();
        while (resultSet.next()) {
            keywords.add(resultSet.getString("name"));
        }
        resultSet.close();
        statement.close();
        return keywords;
    }
}
