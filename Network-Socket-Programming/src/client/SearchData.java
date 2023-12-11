package client;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class SearchData extends JPanel {
    // 검색 후 테이블 생성
    Object[][] search;

    public SearchData() {
        if (search != null) {
            System.out.println(search);
            System.out.println("SearchData Function");
            // setSize(400, 700);

            String[] colName = {"ID", "Name", "Age"};
            JTable table = new JTable(search, colName);
            JScrollPane scrollPane = new JScrollPane(table);

            // 버튼 UI
            // Add ListSelectionListener to the JTable
            ListSelectionModel selectionModel = table.getSelectionModel();
            selectionModel.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    if (!e.getValueIsAdjusting()) {
                        int selectedRow = table.getSelectedRow();
                        int selectedColumn = table.getSelectedColumn();

                        System.out.println("Selected Row: " + selectedRow);
                        System.out.println("Selected Column: " + selectedColumn);
                        System.out.println("Value at Selected Cell: " + table.getValueAt(selectedRow, selectedColumn));
                    }
                }
            });

            add(scrollPane);
            System.out.println("fin");
            setVisible(true);
        }
                
    }
    public void drawSearchTable(Object[][] data) {
        while (data != null) {
            this.search = data;
            for (int row = 0; row < search.length; row++) {
                for (int col = 0; col < search[row].length; col++) {
                    System.out.print(search[row][col] + "\t");
                }
                System.out.println();
            }
            if (search != null) {
                break;
            }
            System.out.println("drawSearcTable Function");
            new SearchData();
        }
        
    }
    // public Object[][] getData() {
    //     return this.search;
    // }

}
