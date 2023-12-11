package client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class InitUI extends JFrame {
    // 버튼 UI
    JPanel pvBtContainer = new JPanel();
    JButton providerButton = new JButton("주차장 제공하기");
    JPanel rtBtContainer = new JPanel();
    JButton renterButton = new JButton("주차장 대여하기");
    JPanel buttonContainer = new JPanel();

    public static void main(String[] args) {
        new InitUI();
    }

    public InitUI() {
        // Frame 기본 설정
        setTitle("주차장 제공/대여 서비스");
        setSize(400, 700);
        setLayout(null);

        // 구성 UI :: ButtonContainer
        pvBtContainer.add(providerButton);
        rtBtContainer.add(renterButton);
        /// 각 버튼에 패딩넣기
        /// [provider] 버튼 구성
        pvBtContainer.setLayout(new GridLayout(1, 1));
        pvBtContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonContainer.add(pvBtContainer);
        /// [renter] 버튼 구성
        rtBtContainer.setLayout(new GridLayout(1, 1));
        rtBtContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonContainer.add(rtBtContainer);
        /// ButtonContainer
        buttonContainer.setBounds(45, 200, 300, 200);
        buttonContainer.setLayout(new GridLayout(2, 1));
        this.add(buttonContainer);

        // 버튼 이벤트 추가
        providerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Provider provider = new Provider();
                remove(buttonContainer);
                add(provider);
                revalidate();
                repaint();
            }
        });

        renterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Renter renter = new Renter();
                remove(buttonContainer);
                add(renter);
                revalidate();
                repaint();
            }
        });
        setVisible(true);

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
