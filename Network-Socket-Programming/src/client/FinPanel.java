package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class FinPanel extends JPanel {
    JLabel finLabel = new JLabel("이용해주셔서 감사합니다.");
    JButton home = new JButton();

    public static void main(String[] args) {
        new FinPanel();
    }

    public FinPanel() {
        // Frame 기본 설정
        this.setSize(400, 700);
        this.setLayout(null);

        add(finLabel);
        add(home);
        setVisible(true);
    }

}
