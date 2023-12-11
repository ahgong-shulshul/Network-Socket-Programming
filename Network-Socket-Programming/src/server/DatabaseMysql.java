package server;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseMysql {
    private static final String URL = "jdbc:mysql://localhost/network_pj";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Min02choi!";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void saveProviderData(ArrayList<String> data) {
        System.out.println("database.java 파일");
        System.out.println(data);

        String str;
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }

        String username = "jinyoung";
        String location = data.get(0);
        String av_time_st = data.get(1);
        String av_time_end = data.get(2);
        int price = Integer.parseInt(data.get(3));
        int is_available = 1;

        try (Connection connection = connect()) {
            String sql = "insert into car_place (username, location, av_time_st, av_time_end, price, is_available) values (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, location);
                preparedStatement.setString(3, av_time_st);
                preparedStatement.setString(4, av_time_end);
                preparedStatement.setInt(5, price);
                preparedStatement.setInt(6, is_available);
                System.out.println("try 문 안에 들어옴");

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Data inserted successfully.");
                } else {
                    System.out.println("Failed to insert data.");
                }
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
                String dbName = resultSet.getString("TABLE_CAT");
                databaseList.add(dbName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return databaseList;
    }
}
