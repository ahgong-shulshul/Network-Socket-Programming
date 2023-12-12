package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8890);
        System.out.println("Server Ready");

        while (true) {
            Socket clientSocket = serverSocket.accept();     // 클라이언트 연결 대기
            System.out.println("Client Ready");
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    private static class ClientHandler implements Runnable {
        private static Socket clientSocket;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try (
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
//                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())
            ) {
                Object firstObject = in.readObject();
                Object secondObject = in.readObject();

                System.out.println(firstObject);
                System.out.println(secondObject);

                if (firstObject instanceof String) {
                    handleButtonClickEvent((String) firstObject, (ArrayList<String>) secondObject);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("catch 안임");
                e.printStackTrace();
            }
        }
        private void handleButtonClickEvent(String eventName, ArrayList<String> data) throws IOException {
            if ("UserLogin".equals(eventName)) {
                DatabaseMysql.putUsername(data.get(0).toString());
            }
            else if ("UserLogout".equals(eventName)) {
                DatabaseMysql.delUsername();
            }
            else if ("saveProviderData".equals(eventName)) {
                System.out.println(data);
                DatabaseMysql.saveProviderData(data);
                System.out.println("Received Event From Client");
            }
            else if ("getAvailableList".equals(eventName)) {
                ArrayList<ArrayList<String>> totalProvider;
                totalProvider = DatabaseMysql.getAvailableList(data.get(0).toString());
                System.out.println(totalProvider);

                sendToClient("getAvailableList", totalProvider);
            }
            else if ("makeReservation".equals(eventName)) {
                DatabaseMysql.makeReservation(data.get(0).toString());
            }
            else if ("".equals(eventName)) {
                // 추가 이벤트
            }
        }
        private static void sendToClient(String eventClass, ArrayList<ArrayList<String>> data) {
            try (ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {
                out.writeObject(eventClass);
                out.writeObject(data);
                System.out.println("Event sent to the Client: " + data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
