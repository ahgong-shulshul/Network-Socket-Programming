package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8890);
        System.out.println("Server Ready");

        Socket clientSocket = serverSocket.accept();     // 클라이언트 연결 대기
        System.out.println("Client Ready");
        new Thread(new ClientHandler(clientSocket)).start();
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try (
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
//                  ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())
            ) {
                Object firstObject = in.readObject();
                Object secondObject = in.readObject();

                System.out.println("where");
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
        private void handleButtonClickEvent(String eventName, ArrayList<String> data) {
            if ("Provider".equals(eventName)) {
                System.out.println(data);
                DatabaseMysql.saveProviderData(data);
                System.out.println("Received Event From Client");
            }
        }
    }
}
