package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Provider extends JPanel {
    // 객체 생성
    JPanel jPanel = new JPanel();
    BorderLayout layout = new BorderLayout();
    JPanel inputPanel = new JPanel();
    // 주차장 위치 입력받기 위한 UI
    JPanel locationPanel = new JPanel();
    JLabel doLabel = new JLabel("광역시/도");
    JTextField doInput = new JTextField(20);
    JLabel siLabel = new JLabel("시/군/구");
    JTextField siInput = new JTextField(20);
    JLabel dongLabel = new JLabel("읍/면/동");
    JTextField dongInput = new JTextField(20);
    // 이용 가능 시간을 입력받기 위한 UI
    JPanel timeContainer = new JPanel();
    JPanel timePanel = new JPanel();
    JPanel timeLabelContainter = new JPanel();
    JLabel startTime = new JLabel("이용 시작시간");
    JLabel endTime = new JLabel("이용 종료시간");
    JLabel to = new JLabel("~");
    JList startTimeHour;
    JList startTimeMin;
    JList endTimeHour;
    JList endTimeMin;
    Integer[] hour = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 };
    String[] min = { "00~10", "10~20", "20~30", "30~40", "40~50", "50~60" };
    // 시간 당 요금 입력받기 위한 UI
    JPanel feePanel = new JPanel();
    JLabel feeLabel = new JLabel("시간 당 희망요금");
    JTextField feeInput = new JTextField(20);
    // 입력 완료 버튼
    JButton finButton = new JButton("입력 완료");

    public static void main(String[] args) {
        new Provider();
    }

    public Provider() {
        // jPanel.setBackground(Color.BLUE);
        setSize(400, 700);
        locationPanel.setLayout(new GridLayout(6, 1));
        timeLabelContainter.setLayout(new GridLayout(1, 2));
        timePanel.setLayout(new GridLayout(1, 5));
        timeContainer.setLayout(new GridLayout(2, 1));
        inputPanel.setLayout(new GridLayout(4, 1));
        feePanel.setLayout(new GridLayout(2, 1));
         jPanel.setLayout(new GridLayout(2, 1));

        // 버튼 UI
        /// 주차장 위치
        locationPanel.add(doLabel);
        locationPanel.add(doInput);
        locationPanel.add(siLabel);
        locationPanel.add(siInput);
        locationPanel.add(dongLabel);
        locationPanel.add(dongInput);
        locationPanel.setSize(300, 100);

        inputPanel.add(locationPanel);
        /// 이용 가능 시간
        startTime.setHorizontalAlignment(JLabel.CENTER);
        endTime.setHorizontalAlignment(JLabel.CENTER);
        timeLabelContainter.add(startTime);
        timeLabelContainter.add(endTime);
        timeContainer.add(timeLabelContainter);
        startTimeHour = new JList(hour);
        timePanel.add(new JScrollPane(startTimeHour));
        startTimeMin = new JList(min);
        timePanel.add(new JScrollPane(startTimeMin));
        to.setHorizontalAlignment(JLabel.CENTER);
        timePanel.add(to);
        endTimeMin = new JList(hour);
        timePanel.add(new JScrollPane(endTimeMin));
        endTimeMin = new JList(min);
        timePanel.add(new JScrollPane(endTimeMin));
        timeContainer.add(timePanel);
        inputPanel.add(timeContainer);
        /// 시간 당 요금
        feePanel.add(feeLabel);
        feePanel.add(feeInput);
        inputPanel.add(feePanel);
        jPanel.add(inputPanel);
        /// 입력 완료 버튼
        // finButton.setBounds(50, 600, 300, 50);
        jPanel.add(finButton);

        inputPanel.setBackground(Color.BLUE);
        timeContainer.setBackground(Color.RED);
        feePanel.setBackground(Color.LIGHT_GRAY);
        jPanel.setBackground(Color.BLACK);

        add(jPanel);
        setVisible(true);
    }
}