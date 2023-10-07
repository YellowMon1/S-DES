package zsy;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class dePage extends JFrame {
    private JPanel contentPane;
    JTextField mainKey;
    JTextField smallText;
    JTextField key1;
    JTextField key2;
    JTextField bigText;
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    dePage frame = new dePage();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static int[] IP= new int[] {2,6,3,1,4,8,5,7};
    public static int[] IP_1= new int[] {4,1,3,5,7,2,8,6};
    public static int[] P10= new int[] {3,5,2,7,4,10,1,9,8,6};
    public static int[] P8= new int[] {6,3,7,4,8,5,10,9};
    public static int[] P4= new int[] {2,4,3,1};
    public static int[] EP= new int[] {4,1,2,3,2,3,4,1};

    //置换操作
    public static String Replace(String old,int[] index ) {
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<index.length;i++) {
            sb.append(old.charAt((index[i])-1));
        }
        String s=new String(sb);
        return s;
    }

    //异或
    public static String XOR(String s1,String s2) {
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<s1.length();i++) {
            if(s1.charAt(i)==s2.charAt(i)) {
                sb.append("0");
            }
            else {
                sb.append("1");
            }
        }
        String s=new String(sb);
        return s;
    }
    //LS-1,LS-2
    public static String LS(String s,int n) {
        char[] copy=s.toCharArray();
        StringBuffer sb=new StringBuffer();
        if(n==1) {
            for(int i=0;i<s.length();i++) {
                sb.append(copy[(i+1)%5]);
            }
        }
        if(n==2) {
            for(int i=0;i<s.length();i++) {
                sb.append(copy[(i+2)%5]);
            }
        }
        return new String(sb);
    }
    //S盒
    public static String[][] S1= new String[][] {
            {"01","00","11","10"},{"11","10","01","00"},
            {"00","10","01","11"},{"11","01","00","10"}
    };
    public static String[][] S2= new String[][] {
            {"00","01","10","11"},{"10","00","01","11"},
            {"11","10","01","00"},{"10","01","00","11"}
    };
    //S盒操作前1423
    public static int[] S4=new int[] {1,4,2,3};
    //S盒操作
    public static String S_Selected(String s,int n) {
        //横坐标
        String hori=s.substring(0, 2);
        //纵坐标
        String vert=s.substring(2);
        String ans=new String();
        if(n==1) {
            ans=S1[Integer.parseInt(hori, 2)][Integer.parseInt(vert, 2)];
        }
        if(n==2) {
            ans=S2[Integer.parseInt(hori, 2)][Integer.parseInt(vert, 2)];
        }
        return ans;
    }
    //子密钥
    public static String skey1;
    public static String skey2;
    //输入ASCII值转8bit二进制
    public static String decimalToBinary(String num) {
        StringBuffer sb=new StringBuffer("0");
        String decimal = String.valueOf(Integer.toBinaryString(Integer.parseInt(num)));
        //保证8位bit
        if(decimal.length()<8) {
            sb.append(decimal);
        }
        return new String(sb);
    }
    public dePage() {
        setResizable(false);
        setTitle("S-DES解密(普通)");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 450, 400);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        JPanel panel = new JPanel();
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel main = new JLabel("请输入主密钥：");
        main.setFont(new Font("宋体", Font.PLAIN, 16));
        main.setBounds(45, 0, 208, 29);
        panel.add(main);

        mainKey = new JTextField();
//        mainKey.setEditable(false);
        mainKey.setColumns(10);
        mainKey.setBounds(45, 32, 240, 43);
        panel.add(mainKey);

        JLabel text = new JLabel("请输入待解密密文：");
        text.setFont(new Font("宋体", Font.PLAIN, 16));
        text.setBounds(45, 80, 208, 30);
        panel.add(text);

        smallText = new JTextField();
//        smallText.setEditable(false);
        smallText.setColumns(10);
        smallText.setBounds(45, 113, 240, 43);
        panel.add(smallText);

        JLabel k1 = new JLabel("子密钥1：");
        k1.setFont(new Font("宋体", Font.PLAIN, 16));
        k1.setBounds(60, 170, 100, 30);
        panel.add(k1);

        key1 = new JTextField();
        key1.setEditable(false);
        key1.setColumns(10);
        key1.setBounds(165, 165, 240, 41);
        panel.add(key1);

        JLabel k2 = new JLabel("子密钥2：");
        k2.setFont(new Font("宋体", Font.PLAIN, 16));
        k2.setBounds(60, 215, 100, 30);
        panel.add(k2);

        key2 = new JTextField();
        key2.setEditable(false);
        key2.setColumns(10);
        key2.setBounds(165, 210, 240, 41);
        panel.add(key2);

        JLabel text1 = new JLabel("解密后明文：");
        text1.setFont(new Font("宋体", Font.PLAIN, 16));
        text1.setBounds(60, 260, 100, 30);
        panel.add(text1);

        bigText = new JTextField();
        bigText.setEditable(false);
        bigText.setColumns(10);
        bigText.setBounds(165, 255, 240, 41);
        panel.add(bigText);

        JButton kb = new JButton("生成子密钥");
        kb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String mKey = mainKey.getText();
                mKey=Replace(mKey, P10);
                String Lkey=mKey.substring(0, 5);
                String Rkey=mKey.substring(5, 10);
                //子密钥1
                Lkey=LS(Lkey, 1);
                Rkey=LS(Rkey, 1);
                skey1=Lkey+Rkey;
                skey1=Replace(skey1, P8);
                //子密钥2
                Lkey=LS(Lkey, 2);
                Rkey=LS(Rkey, 2);
                skey2=Lkey+Rkey;
                skey2=Replace(skey2, P8);

                if(skey1 != null) {
                    key1.setText(skey1);
                    key2.setText(skey2);
                } else {
                    JOptionPane.showMessageDialog(dePage.this, "子密钥生成失败!");
                }

            }
        });
        kb.setFont(new Font("宋体", Font.PLAIN, 16));
        kb.setBounds(290, 34, 130, 38);
        panel.add(kb);

        JButton decode = new JButton("解密");
        decode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String st = smallText.getText();

                //length<8 ascii转二进制
                if(st.length()<8) {
                    st=decimalToBinary(st);
                }
                //IP置换
                st=Replace(st, IP);
                String L0=st.substring(0, 4);
                String R0=st.substring(4, 8);
                //f函数
                String R0_1=Replace(R0,EP);
                String temp=XOR(R0_1, skey2);
                //s盒操作
                String s1=temp.substring(0, 4);
                s1=Replace(s1,S4);
                s1=S_Selected(s1, 1);
                String s2=temp.substring(4, 8);
                s2=Replace(s2,S4);
                s2=S_Selected(s2, 2);
                String ans1=Replace((s1+s2), P4);
                //异或得到R1
                String R1=XOR(ans1, L0);
                String L1=R0;

                //第二次f函数操作
                String R1_1=Replace(R1,EP);
                String temp_1=XOR(R1_1, skey1);
                //s盒操作
                String s1_1=temp_1.substring(0, 4);
                s1_1=Replace(s1_1,S4);
                s1_1=S_Selected(s1_1, 1);
                String s2_1=temp_1.substring(4, 8);
                s2_1=Replace(s2_1,S4);
                s2_1=S_Selected(s2_1, 2);
                String ans2=Replace((s1_1+s2_1), P4);
                //异或得到R1
                String L2=XOR(ans2, L1);
                String R2=R1;

                String code=Replace((L2+R2), IP_1);
                if(code != null) {
                    bigText.setText(code);
                } else {
                    JOptionPane.showMessageDialog(dePage.this, "解密失败!");
                }

            }
        });
        decode.setFont(new Font("宋体", Font.PLAIN, 16));
        decode.setBounds(290, 115, 100, 37);
        panel.add(decode);

        JButton back = new JButton("返回");
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
                new mainPage().setVisible(true);
            }
        });
        back.setFont(new Font("宋体", Font.PLAIN, 18));
        back.setBounds(170, 310, 130, 38);
        panel.add(back);
    }

}
