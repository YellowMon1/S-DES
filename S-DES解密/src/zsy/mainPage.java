package zsy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainPage extends JFrame {
    private JPanel contentPane;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    mainPage frame = new mainPage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public mainPage(){
        setResizable(false);
        setTitle("S-DES解密");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 350);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel main = new JLabel("S-DES解密系统");
        main.setFont(new Font("宋体", Font.BOLD,25));
        main.setBounds(130, 60, 210, 40);
        panel.add(main);

        JButton normal = new JButton("普通解密");
        normal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new dePage().setVisible(true);
            }
        });
        normal.setFont(new Font("宋体", Font.PLAIN, 18));
        normal.setBounds(80, 170, 130, 38);
        panel.add(normal);

        JButton force = new JButton("暴力解密");
        force.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new Force().setVisible(true);
            }
        });
        force.setFont(new Font("宋体", Font.PLAIN, 18));
        force.setBounds(230, 170, 130, 38);
        panel.add(force);
    }
}
