package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMysql {
    private static final String URL = "jdbc:mysql://localhost/network_pj";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Min02choi!";

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void saveToDatabase(String data) {
        try (Connection connection = connect()) {
            String sql = "insert into testdb (testtext) values (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, data);
                preparedStatement.executeUpdate();
                System.out.println("Data saved to the database: " + data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<String> getDatabaseList() {
        List<String> databaseList = new ArrayList<>();

        try (Connection connection = connect()){
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getCatalogs();

            while (resultSet.next()) {
                String dbName = resultSet.getString("location");
                databaseList.add(dbName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseList;
    }
}
