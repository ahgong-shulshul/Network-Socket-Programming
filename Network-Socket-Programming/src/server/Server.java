package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8890);
        System.out.println("Server Ready");

        while (true) {
            Socket clientSocket = serverSocket.accept();     // 클라이언트 연결 대기
            new Thread(new ClientHandler(clientSocket)).start();
        }

//        BufferedReader keyRead = new BufferedReader(new InputStreamReader(System.in));      // 키보드 입력을 받기 위함
//        OutputStream ostream = sock.getOutputStream();                  // 클라이언트의 메세지를 받기 위한 스트림
//        PrintWriter pwrite = new PrintWriter(ostream, true);    // 클라이언트에게 보낼 출력 스트림
//
//        InputStream istream = sock.getInputStream();
//        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
//
//        String receiveMsg, sendMsg;
//        System.out.println("Server >> Message Writing");

    }
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try (
                    ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ) {
                String receivedData = (String) in.readObject();
                System.out.println("Server received data from client: " + receivedData);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
