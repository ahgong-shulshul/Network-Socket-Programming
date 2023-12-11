package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

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
    // 이용 가능 YYYY-MM-DD    
    JTextField ymd = new JTextField();
    JLabel ymdLabel = new JLabel("대여 요일(YYYY-MM-DD)");
    JPanel ymdPanel = new JPanel();
    //  STHH:MM EDHH:MM
    // 이용 가능 시간을 입력받기 위한 UI
    JPanel timeContainer = new JPanel();
    JPanel timePanel = new JPanel();
    JPanel timeLabelContainter = new JPanel();
    JLabel startTime = new JLabel("대여 시작시간");
    JLabel endTime = new JLabel("대여 종료시간");
    JLabel to = new JLabel("~");
    JList startTimeHour;
    JList startTimeMin;
    JList endTimeHour;
    JList endTimeMin;
    String[] hour = { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" };
    String[] min = {"00", "10", "20", "30", "40", "50"};
    // 시간 당 요금 입력받기 위한 UI
    JPanel feePanel = new JPanel();
    JLabel feeLabel = new JLabel("시간 당 희망요금");
    JTextField feeInput = new JTextField(20);
    // 입력 완료 버튼
    JButton finButton = new JButton("입력 완료");
    JPanel finBtPanel = new JPanel();

    public static void main(String[] args) throws Exception {
        new Provider();
    }

    public Provider() {
        this.setSize(400, 700);
        this.setLayout(new GridLayout(6, 1));
        locationPanel.setLayout(new GridLayout(6, 1));
        ymdPanel.setLayout(new GridLayout(2, 1));
        timeLabelContainter.setLayout(new GridLayout(1, 2));
        timePanel.setLayout(new GridLayout(1, 5));
        timeContainer.setLayout(new GridLayout(2, 1));
        feePanel.setLayout(new GridLayout(2, 1));

        finBtPanel.setLayout(new GridLayout(1, 1));
        locationPanel.setBorder(BorderFactory.createEmptyBorder(10 , 20 , 10 , 30));
        ymdPanel.setBorder(BorderFactory.createEmptyBorder(10 , 20 , 10 , 30));
        timeContainer.setBorder(BorderFactory.createEmptyBorder(10 , 20 , 10 , 30));
        feePanel.setBorder(BorderFactory.createEmptyBorder(10 , 20 , 10 , 30));
        finBtPanel.setBorder(BorderFactory.createEmptyBorder(10 , 20 , 10 , 30));

        // 버튼 UI
        /// 주차장 위치
        locationPanel.add(doLabel);
        locationPanel.add(doInput);
        locationPanel.add(siLabel);
        locationPanel.add(siInput);
        locationPanel.add(dongLabel);
        locationPanel.add(dongInput);

        /// 대여 요일
        ymdPanel.add(ymdLabel);
        ymdPanel.add(ymd);

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
        endTimeHour = new JList(hour);
        timePanel.add(new JScrollPane(endTimeHour));
        endTimeMin = new JList(min);
        timePanel.add(new JScrollPane(endTimeMin));
        timeContainer.add(timePanel);
        /// 시간 당 요금
        feePanel.add(feeLabel);
        feePanel.add(feeInput);
        finBtPanel.add(finButton);

        add(locationPanel);
        add(ymdPanel);
        add(timeContainer);
        add(feePanel);
        add(finBtPanel);
        setVisible(true);

        finButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 입력받은 정보 가져오기
                String doInputValue = doInput.getText();
                String siInputValue = siInput.getText();
                String dongInputValue = dongInput.getText();
                String ymdInputValue = ymd.getText();
                String startHourInputValue = String.valueOf(startTimeHour.getSelectedValue());
                String startMinInputValue = String.valueOf(startTimeMin.getSelectedValue());
                String endHourInputValue = String.valueOf(endTimeHour.getSelectedValue());
                String endMinInputValue = String.valueOf(endTimeMin.getSelectedValue());
                String feeInputValue = feeInput.getText();
                
                // 서버로 전달할 데이터로 가공하기
                String deliverLocation = doInputValue + " " + siInputValue + " " + dongInputValue;
                String deliverStart = ymdInputValue + " " + startHourInputValue + ":" + startMinInputValue + ":00";
                String deliverEnd = ymdInputValue + " " + endHourInputValue + ":" + endMinInputValue + ":00";
                String deliverFee = feeInputValue;

                System.out.println(deliverLocation + "\n" + deliverStart + "\n" + deliverEnd + "\n" + deliverFee);
                ArrayList<String> data = new ArrayList<>();
                data.add(deliverLocation);
                data.add(deliverStart);
                data.add(deliverEnd);
                data.add(deliverFee);


                // 서버로 데이터 전송하기
                //sendToServer("Provider", data);

                // 완료 패널로 전환
                FinPanel finPanel = new FinPanel();
                locationPanel.setVisible(false); // 창 안보이게 하기
                ymdPanel.setVisible(false);
                timeContainer.setVisible(false);
                feePanel.setVisible(false);
                finBtPanel.setVisible(false);
                add(finPanel);
                finPanel.setVisible(true);
            }
        });
    }
    private static void sendToServer(String eventClass, ArrayList<String> data) {
        try (Socket socket = new Socket("127.20.6.21", 8890);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                out.writeObject(data);
                System.out.println("Event sent to the Server: " + data);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
}