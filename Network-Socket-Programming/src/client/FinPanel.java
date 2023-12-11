package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

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
                StartUI home = new StartUI();
                remove(jPanel);
                add(home);
                revalidate();
                repaint();
            }
        });   
    }

}
