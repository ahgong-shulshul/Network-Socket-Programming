package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class tempUI extends JFrame {
    public static void main(String[] args) {
        new tempUI();
    }

    public tempUI() {
        // 임시 데이터
        Object[][] data = {
            {"1", "John Doe", "30"},
            {"2", "Jane Doe", "25"},
            {"3", "Bob Smith", "40"},
            // Add more data as needed
        };
        SearchData home = new SearchData();
        home.drawSearchTable(data);
        add(home);

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
    
}
