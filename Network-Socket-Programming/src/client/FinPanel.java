package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class FinPanel extends JPanel {
    JPanel jPanel = new JPanel();
    JLabel finLabel = new JLabel("이용해주셔서 감사합니다.");
    JButton homeButton = new JButton("홈으로 가기");
    JButton logoutButton = new JButton("로그아웃");

    public static void main(String[] args) {
        new FinPanel();
    }

    public FinPanel() {
        // Frame 기본 설정
        this.setSize(400, 700);
        // this.setLayout(null);

        jPanel.setLayout(new GridLayout(3, 1));

        jPanel.add(finLabel);
        jPanel.add(homeButton);
        jPanel.add(logoutButton);
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(jPanel);
        setVisible(true);

        // 버튼 이벤트 추가
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InitUI home = new InitUI();
                remove(jPanel);
                add(home);
                revalidate();
                repaint();
            }
        });   
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 서버로 전송
                // sendToServer("FinPanel", null);
                // 창 전환
                StartUI home = new StartUI();
                remove(jPanel);
                add(home);
                revalidate();
                repaint();
            }
        });   
    }
    private static void sendToServer(String eventClass, ArrayList<String> data) {
        try (Socket socket = new Socket("172.20.6.21", 8890);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                out.writeObject(eventClass);
                out.writeObject(data);
                System.out.println("Event sent to the Server: " + data);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
