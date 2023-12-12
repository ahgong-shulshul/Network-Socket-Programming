package client;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Renter extends JPanel {
    // 주차장 위치 입력받기 위한 UI
    JPanel locationPanel = new JPanel();
    JLabel doLabel = new JLabel("광역시/도");
    JTextField doInput = new JTextField(20);
    JLabel siLabel = new JLabel("시/군/구");
    JTextField siInput = new JTextField(20);
    JLabel dongLabel = new JLabel("읍/면/동");
    JTextField dongInput = new JTextField(20);
    JButton searchButton = new JButton("검색하기");
    // 검색된 결과를 보여주는 UI
    String[] colName = {"ID", "Name", "Age"};
    JTable table;
    JScrollPane scrollPane;
    JPanel scrollPanel = new JPanel();
    // 예약하기
    JPanel reservePanel = new JPanel();
    JButton reserveButton = new JButton("예약 완료");


    public static void main(String[] args) {
        new Renter();
    }

    public Renter() {
        // Frame 기본 설정
        this.setSize(400, 700);
        // this.setLayout(null);
        locationPanel.setLayout(new GridLayout(7, 1));
        locationPanel.setBorder(BorderFactory.createEmptyBorder(10 , 20 , 10 , 30));
        scrollPanel.setBorder(BorderFactory.createEmptyBorder(50 , 20 , 10 , 30));
        // reservePanel.setLayout(new GridLayout(1, 1));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // 버튼 UI
        /// 주차장 위치
        locationPanel.add(doLabel);
        locationPanel.add(doInput);
        locationPanel.add(siLabel);
        locationPanel.add(siInput);
        locationPanel.add(dongLabel);
        locationPanel.add(dongInput);
        locationPanel.add(searchButton);
        add(locationPanel);
        setVisible(true);

        // 검색하기 버튼 이벤트
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 입력받은 정보 가져오기
                String doInputValue = doInput.getText();
                String siInputValue = siInput.getText();
                String dongInputValue = dongInput.getText();
                
                // 서버로 전달할 데이터로 가공하기
                String deliverLocation = doInputValue + " " + siInputValue + " " + dongInputValue;

                // 서버로 데이터 전송하기
                //sendToServer("Renter", deliverLocation);
                // getFromServer
                Object[][] data = {
                    {"1", "John Doe", "30"},
                    {"2", "Jane Doe", "25"},
                    {"3", "Bob Smith", "40"},
                    // Add more data as needed
                };

                // 아래 전달받은 결과 추가
                String[] colName = {"ID", "Name", "Age"};
                table = new JTable(data, colName);
                scrollPane = new JScrollPane(table);
                scrollPane.setViewportView(table);
                add(scrollPane);
                scrollPane.setVisible(true);
                revalidate();
                repaint();
            }
            
        });

        

        // 예약완료 버튼 이벤트
        reserveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printSelectedRowAndColumn();
                // 완료 패널로 전환
                FinPanel finPanel = new FinPanel();
                remove(locationPanel);
                remove(scrollPane);
                remove(reservePanel);
                add(finPanel);
                revalidate();
                repaint();
            }
            
        });
        reservePanel.add(reserveButton);
        add(reservePanel);
        

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
    private void printSelectedRowAndColumn() {
        int selectedRow = table.getSelectedRow();
        int selectedColumn = table.getSelectedColumn();

        if (selectedRow == -1 || selectedColumn == -1) {
            System.out.println("No selection");
        } else {
            System.out.println("Selected Row: " + selectedRow);
            System.out.println("Selected Column: " + selectedColumn);
        }
    }

}
