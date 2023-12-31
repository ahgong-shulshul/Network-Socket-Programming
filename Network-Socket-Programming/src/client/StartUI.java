package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;


public class StartUI extends JFrame {
    JPanel jPanel = new JPanel();
    JPanel inputPanel = new JPanel();
    JLabel userIDLabel = new JLabel("User ID 입력하기");
    JTextField userIDInput = new JTextField(20);
    JPanel btPanel = new JPanel();
    JButton loginButton = new JButton("로그인하기");
    

    public static void main(String[] args) {
        new StartUI();
    }

    public StartUI() {
        // Frame 기본 설정
        setTitle("주차장 제공/대여 서비스");
        setSize(400, 700);
        userIDLabel.setHorizontalAlignment(JLabel.CENTER);

        inputPanel.add(userIDLabel);
        inputPanel.add(userIDInput);
        btPanel.add(loginButton);
        jPanel.add(inputPanel);
        jPanel.add(btPanel);
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(jPanel);


        // 버튼 이벤트 추가
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> idInputValue = new ArrayList<String>();
                idInputValue.add(userIDInput.getText());
                // 서버로 전송
                 sendToServer("UserLogin", idInputValue);
                // 창 전환

                InitUI home = new InitUI();
                dispose();
                add(home);
                revalidate();
                repaint();
            }
        });


        // 화면 중앙에 띄우기
        Dimension frameSize = getSize();
        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2);

        // 프레임을 닫았을 때 메모리에서 제거되도록 설정
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }
    private static void sendToServer(String eventClass, ArrayList<String> data) {
        try (Socket socket = new Socket("172.20.19.60", 8891);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                out.writeObject(eventClass);
                out.writeObject(data);
                System.out.println("Client to Server from " + eventClass + ":: content ->" + data);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    
    
}
