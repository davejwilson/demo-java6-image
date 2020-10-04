package org.demo.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleDataService {
    private Connection connection;

    public OracleDataService() {
        try {
            String hostname = System.getenv("ORACLE_HOSTNAME");
            if (hostname == null) {
                throw new RuntimeException("Environment ORACLE_HOSTNAME is not set");
            }
            String sysPassword = System.getenv("ORACLE_SYS_PASSWORD");
            if (sysPassword == null) {
                throw new RuntimeException("Environment ORACLE_SYS_PASSWORD is not set");
            }
            connection = DriverManager.getConnection("jdbc:oracle:thin:@" + hostname + ":1521:xe",
                    "sys as sysdba", sysPassword);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException("failed to connect to oracle db", throwables);
        }
    }
    public static void main(String[] args) throws SQLException {
        OracleDataService oracleDataService = new OracleDataService();
        System.out.println(oracleDataService.getTables());
    }

    public List<String> getTables() throws SQLException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement("select * from all_tables");
            resultSet = statement.executeQuery();
            List<String> keywords = new ArrayList<String>();
            while (resultSet.next()) {
                keywords.add(resultSet.getString("table_name"));
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
