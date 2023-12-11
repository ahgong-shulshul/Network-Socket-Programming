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


    public static void putUsername(String username) {
        try (Connection connection = connect()) {
            String sql = "insert into cur_user (user) values (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getUsername() {
        String username = "";
        try (Connection connection = connect()){
            String sql = "select * from cur_user";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
                username = resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static void delUsername() {
        try (Connection connection = connect()) {
            String sql = "delete from cur_user";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                ResultSet resultSet = preparedStatement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Provider -> Server: 주차공간 등록
    public static void saveProviderData(ArrayList<String> data) {
        System.out.println("database.java 파일");
        System.out.println(data);

        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }

        String username = DatabaseMysql.getUsername();
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
    public static ArrayList<ArrayList<String>> getAvailableList(String loc) {
        ArrayList<ArrayList<String>> totalProvider = new ArrayList<>();
        ArrayList<String> singleProvider = new ArrayList<>();

        String sql;
        try (Connection connection = connect()) {
            if (loc == null) {
                sql = "select * from car_place where is_available = 1";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            ResultSetMetaData metaData = resultSet.getMetaData();
                            int columnCount = metaData.getColumnCount();
                            for (int i = 1; i <= columnCount; i++) {
                                Object value = resultSet.getObject(i);
                                singleProvider.add((String) value);     // 확인 필요
                            }
                            totalProvider.add(singleProvider);
                        }
                    }
                }
            } else {
                sql = "select * from car_place where (location) like ? and is_available = 1";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, "%" + loc + "%");

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                            ResultSetMetaData metaData = resultSet.getMetaData();

                            int columnCount = metaData.getColumnCount();
                            for (int i = 1; i <= columnCount; i++) {
//                                String columnName = metaData.getColumnName(i);
                                Object value = resultSet.getObject(i);
                                singleProvider.add((String) value);     // 확인 필요
                            }
                            totalProvider.add(singleProvider);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalProvider;
    }

    public static void makeReservation() {

    }


}
