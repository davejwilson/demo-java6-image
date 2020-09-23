package org.demo.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataService {
    private Connection connection;

    public DataService() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "bitnami");
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
