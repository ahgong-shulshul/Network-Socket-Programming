package server;

import java.sql.*;
import java.util.ArrayList;


public class DatabaseMysql {
    private static final String URL = "jdbc:mysql://localhost/network_pj";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Min02choi!";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    private static boolean isUserExist(String user) {
        System.out.println("<isUserExist 메소드>");
        try (Connection connection = connect()) {
            String sql = "select count(*) FROM user WHERE uname = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, user);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int count = resultSet.getInt(1);
                        return count > 0;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void putUsername(String username) {
        System.out.println("<putUsername 메소드>");
        try (Connection connection = connect()) {
            // 중복인지 확인 필요
            if (!isUserExist(username)) {
                String sql = "insert into user (uname) values (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    preparedStatement.setString(1, username);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Username inserted successfully.");
                    } else {
                        System.out.println("Failed to insert username.");
                    }
                }
            }
            String sql = "insert into cur_user (user) values (?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Username inserted successfully.");
                } else {
                    System.out.println("Failed to insert username.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String getUsername() {
        System.out.println("<getUsername 메소드>");
        String username = "";
        try (Connection connection = connect()) {
            String sql = "select * from cur_user";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        username = resultSet.getString(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static void delUsername() {
        System.out.println("<delUsername 메소드>");
        try (Connection connection = connect()) {
            String sql = "delete from cur_user";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Username inserted successfully.");
                } else {
                    System.out.println("Failed to delete username.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Provider -> Server: 주차공간 등록
    public static void saveProviderData(ArrayList<String> data) {
        System.out.println("<saveProviderData 메소드>");
        System.out.println(data);

        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
        }

        String username = getUsername();
        System.out.println(username);       // 실험헤보기

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
        System.out.println("<getAvailableList 메소드>");
        ArrayList<ArrayList<String>> totalProvider = new ArrayList<>();
        ArrayList<String> singleProvider = new ArrayList<>();

        try (Connection connection = connect()) {
            System.out.println(loc);
            String sql = "select * from car_place where (location) like ? and (is_available) = 1";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, "%" + loc + "%");
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    System.out.println(resultSet.getRow());
                    while (resultSet.next()) {
                        System.out.println(resultSet.getMetaData().getColumnCount());
                        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                            Object value = resultSet.getObject(i);
                            System.out.println(value);
                            singleProvider.add(String.valueOf(value));
                        }
                        totalProvider.add(new ArrayList<>(singleProvider));
                        singleProvider.clear();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalProvider;
    }

    public static ArrayList<ArrayList<String>> getAvailableList2(String loc) {
        System.out.println(loc);
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
                    String temp = "%" + loc + "%";
                    System.out.println(temp);
                    preparedStatement.setString(1, temp);
                    System.out.println(sql);

                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        System.out.println(resultSet);
                        while (resultSet.next()) {
                            ResultSetMetaData metaData = resultSet.getMetaData();
                            System.out.println(metaData);

                            int columnCount = metaData.getColumnCount();
                            System.out.println(columnCount);
                            for (int i = 1; i <= columnCount; i++) {
//                                String columnName = metaData.getColumnName(i);
                                Object value = resultSet.getObject(i);
                                System.out.println(value);
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

    public static void makeReservation(String parking_loc) {
        // is_available=0 으로 바꾸고 테이블에 insert
        // 이거 안되면 개수 세서 하던가
        System.out.println("<makeReservation 메소드>");
        String cur_user = getUsername();
        System.out.println(cur_user);
        try (Connection connection = connect()) {
            String sql = "update car_place set is_available = 0 where place_no = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, parking_loc);
//                ResultSet resultSet = preparedStatement.executeQuery();
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Data update successfully.");
                } else {
                    System.out.println("Failed to insert data.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try (Connection connection = connect()) {
            String sql = "insert into occupied values (?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, parking_loc);
                preparedStatement.setString(2, cur_user);
                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Reservation Completed");
                } else {
                    System.out.println("Failed.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
