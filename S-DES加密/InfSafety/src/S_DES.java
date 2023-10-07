import java.awt.EventQueue;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class S_DES extends JFrame {

	private JPanel contentPane;
	private static JTextField MkeyText;
	private static JTextField PlainText;
	private static JTextField CodeText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					S_DES frame = new S_DES();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public S_DES() {
		setTitle("S-DES加密");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 354);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("主密钥：");
		lblNewLabel.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel.setBounds(115, 64, 83, 36);
		contentPane.add(lblNewLabel);
		
		MkeyText = new JTextField();
		MkeyText.setBounds(209, 74, 144, 21);
		contentPane.add(MkeyText);
		MkeyText.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("明文：");
		lblNewLabel_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1.setBounds(115, 120, 83, 36);
		contentPane.add(lblNewLabel_1);
		
		PlainText = new JTextField();
		PlainText.setColumns(10);
		PlainText.setBounds(209, 130, 144, 21);
		contentPane.add(PlainText);
		
		JLabel lblNewLabel_1_1 = new JLabel("密文：");
		lblNewLabel_1_1.setFont(new Font("宋体", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(115, 182, 83, 36);
		contentPane.add(lblNewLabel_1_1);
		
		CodeText = new JTextField();
		CodeText.setEditable(false);
		CodeText.setColumns(10);
		CodeText.setBounds(209, 192, 144, 21);
		contentPane.add(CodeText);
		
		JButton btnNewButton = new JButton("加密");
		btnNewButton.setFocusable(false);
		btnNewButton.setContentAreaFilled(false);
	
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getKey();
				String code=encrypt();
				//转为ascii值进行输出
				code=convertHexToString(bin2hex(code));
				CodeText.setText(code);
			}
		});
		btnNewButton.setFont(new Font("宋体", Font.BOLD, 20));
		btnNewButton.setBounds(331, 238, 106, 36);
		contentPane.add(btnNewButton);
		
		
	}
	//二进制转十六进制
		public static String bin2hex(String input) {
	        StringBuilder sb = new StringBuilder();
	        int len = input.length();
	        for (int i = 0; i < len / 4; i++){
	            //每4个二进制位转换为1个十六进制位
	            String temp = input.substring(i * 4, (i + 1) * 4);
	            int tempInt = Integer.parseInt(temp, 2);
	            String tempHex = Integer.toHexString(tempInt).toUpperCase();
	            sb.append(tempHex);
	        }
	        return sb.toString();
	    }
	//十六进制转ascii
		public static String convertHexToString(String hex){
			  StringBuilder sb = new StringBuilder();
			  StringBuilder temp = new StringBuilder();
			  for( int i=0; i<hex.length()-1; i+=2 ){
			      String output = hex.substring(i, (i + 2));
			      int decimal = Integer.parseInt(output, 16);
			      sb.append((char)decimal);
			      temp.append(decimal);
			  }
			  return sb.toString();
		}
	//置换盒
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
			{"01","00","11","10"},{"11","10","01","00"},{"00","10","01","11"},{"11","01","00","10"}
		};
		public static String[][] S2= new String[][] {
			{"00","01","10","11"},{"10","00","01","11"},{"11","10","01","00"},{"10","01","00","11"}
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
		public static String key1;
		public static String key2;
		public static void getKey() {
			//获得主密钥
			String mKey=MkeyText.getText();
			mKey=Replace(mKey, P10);
			String Lkey=mKey.substring(0, 5);
			String Rkey=mKey.substring(5, 10);
			//子密钥1
			Lkey=LS(Lkey, 1);	
			Rkey=LS(Rkey, 1);
			key1=Lkey+Rkey;
			key1=Replace(key1, P8);
			//子密钥2
			Lkey=LS(Lkey, 2);
			Rkey=LS(Rkey, 2);
			key2=Lkey+Rkey;
			key2=Replace(key2, P8);
		}
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
		//加密主流程
		public static String encrypt() {
			//获得明文
			String plainText=PlainText.getText();
			//length<8 ascii转二进制
			if(plainText.length()<8) {
			plainText=decimalToBinary(plainText);
			}
			//IP置换
			plainText=Replace(plainText, IP);
			String L0=plainText.substring(0, 4);
			String R0=plainText.substring(4, 8);
			//f函数
			String R0_1=Replace(R0,EP);
			String temp=XOR(R0_1, key1);
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
			String temp_1=XOR(R1_1, key2);
			//s盒操作
			String s1_1=temp_1.substring(0, 4);
			s1_1=Replace(s1_1,S4);
			s1_1=S_Selected(s1_1, 1);
			String s2_1=temp_1.substring(4, 8);
			s2_1=Replace(s2_1,S4);
			s2_1=S_Selected(s2_1, 2);
			String ans2=Replace((s1_1+s2_1), P4);
			//异或得到L2
			String L2=XOR(ans2, L1);
			String R2=R1;
			
			String code=Replace((L2+R2), IP_1);
			return code;
		}
}
