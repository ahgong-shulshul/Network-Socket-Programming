package database;

import database.Data;
import java.util.ArrayList;

public class Database {
    public static void main(String[] args) {
        String path = "C:\\Users\\손수경\\Desktop\\Network-Socket-Programming\\Network-Socket-Programming\\src\\database\\database.txt";
        Data dt = new Data();
        dt.write(path, "\n");
        dt.write(path, "안녕하세요");

        String result = dt.read(path);
        System.out.println(result);

    }
}
