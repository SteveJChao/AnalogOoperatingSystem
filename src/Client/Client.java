package Client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import A_General.Message;
import A_General.Mtype;
import A_General.OS;

public class Client extends JFrame implements ActionListener {

	private JTextArea jta = new JTextArea();
	private JTextField jtf = new JTextField();
	private JScrollPane scrollPane = new JScrollPane();

	private BufferedReader bf = null;
	ObjectOutputStream oos = null;

	// 0-登陆；1-注册；2-使用OS；3-文本输入模式
	private int client_flag = 0;

	Socket socket = null;

	String currentMessage = "";

	public Client() {
		this.setSize(800, 600);
		// 界面处理
		this.setTitle("shell");
//		this.
//		this.setOpacity(0.7f);
		jtf.setBackground(Color.black);
		jtf.setPreferredSize(new Dimension(800,30));
		jtf.setBorder(new EmptyBorder(0,0,0,0));
		jtf.setForeground(Color.green);
		jtf.setFont(new Font("Consolas",Font.BOLD,15));
        jtf.addActionListener(this);

        jta.setBackground(Color.black);
		jta.setPreferredSize(new Dimension(800,565));
		jta.setBorder(new EmptyBorder(0,0,0,0));
		jta.setForeground(Color.green);
		jta.setFont(new Font("Consolas",Font.BOLD,15));
		jta.setEditable(false);

        scrollPane.setViewportView(jta);



		this.add(scrollPane, BorderLayout.CENTER);
		this.add(jtf, BorderLayout.SOUTH);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		// 客户端链接
		try {
			socket = new Socket("localhost", 6153);
			bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}



		jta.setText("=============Login===============" + "\n");
		jta.append("\n" + "please input your [account] and [password]:" + "\n");
		receiverMessage();

	}

	public void receiverMessage() {
		try {
			while (true) {
				// 收到系统消息并给以反馈
				String str = bf.readLine();
				jta.append("  " + str + "\n");

				// 登陆失败时重新登录
				if (str.equals("<Error> Account not found , register first !") || str.equals("<Error> Wrong Password !")
						|| str.equals("<Error> Account is active now !")) {
					new Thread().sleep(3000);
					jta.setText("=============Login===============" + "\n" + "\n");
					jta.append("\n" + "\n" + " ! " + "please input your [account] and [password]:" + "\n");
					client_flag = 0;
					this.setTitle("Login");
				}

				// 注册失败时重新注册
				if (str.equals("<Error> same account exists !")) {
					new Thread().sleep(3000);
					jta.setText("=============Register===============" + "\n" + "\n");
					jta.append("\n" + "\n" + " ! " + "please input your [account]  [password] [group]:" + "\n");
					client_flag = 1;
					this.setTitle("Register");
				}

				// 注册成功时登陆
				if (str.equals("successfully register , please login !")) {
					new Thread().sleep(3000);
					jta.setText("=============Login===============" + "\n" + "\n");
					jta.append("\n" + "\n" + " ! " + "please input your [account] and [password]:" + "\n");
					client_flag = 0;
					this.setTitle("Login");
				}

				// 登陆成功
				if (str.startsWith("~Welcome")) {
					new Thread().sleep(3000);
					jta.setText("=============Welcome===============" + "\n" + "\n");
					this.setTitle(str.split(",")[1]);
					client_flag = 2;
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (client_flag == 3)
			jta.append("\n"+">>" + jtf.getText() + "\n");
		else
			jta.append("\n"+"$" + jtf.getText() + "\n");

		if (client_flag == 0) {

			// 登陆状态下转为注册
			if (jtf.getText().trim().equals("register")) {
				jta.setText("=============Register===============" + "\n" + "\n");
				jta.append("\n" + "\n" + " ! " + "please input your [account]  [password] [group]:" + "\n");
				client_flag = 1;
				jtf.setText("");
				this.setTitle("Register");
				return;
			}

			if (!jtf.getText().contains(" ") || jtf.getText().split(" ").length != 2) {
				jta.append(" <Error>  Incorrect Pattern" + "\n");
				jtf.setText("");
				return;
			}

			Message message = new Message();
			message.type = Mtype.LOGIN;

			message.UserName = jtf.getText().split(" ")[0];
			message.password = jtf.getText().split(" ")[1];
			try {
				oos.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			jtf.setText("");

		} else if (client_flag == 1) {

			// 注册状态下转为登陆
			if (jtf.getText().trim().equals("login")) {
				jta.setText("=============Login===============" + "\n" + "\n");
				jta.append("\n" + "\n" + " ! " + "please input your [account] and [password]:" + "\n");
				jtf.setText("");
				client_flag = 0;
				this.setTitle("Login");

				return;
			}

			if (!jtf.getText().contains(" ") || jtf.getText().split(" ").length != 3) {
				jta.append(" <Error>  Incorrect Pattern" + "\n");
				jtf.setText("");
				return;
			}

			Message message = new Message();
			message.type = Mtype.REGISTER;

			message.UserName = jtf.getText().split(" ")[0];
			message.password = jtf.getText().split(" ")[1];
			message.group = jtf.getText().split(" ")[2];
			try {
				oos.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			jtf.setText("");

		} else if (client_flag == 2) {
			String str = jtf.getText().trim();
			
			if(str.equals("clear"))
			{
				jta.setText("");
				jtf.setText("");
				return;
			}

			// 写文件特殊操作
			if (str.split(" ")[0].equals("write") && str.split(" ").length == 2) {
				jta.append(">>" + "\n");
				jtf.setText("");
				client_flag = 3;
				currentMessage = str;
				return;
			}

			// 用户通信特殊操作
			if (str.split(" ")[0].equals("mail") && str.split(" ").length == 2) {
				jta.append(">>" + "\n");
				jtf.setText("");
				client_flag = 3;
				currentMessage = str;
				return;
			}

			if (checkCommand(str) == false) {
				jta.append(" <Error>  Incorrect Pattern" + "\n");
				jtf.setText("");
				return;
			}

			Message message = new Message();
			message.type = Mtype.COMMAND;
			message.message = str;

			try {
				oos.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			jtf.setText("");
		} else {

			String str = currentMessage + " " + jtf.getText();
			Message message = new Message();
			message.type = Mtype.COMMAND;
			message.message = str;
			client_flag = 2;
			try {
				oos.writeObject(message);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			jtf.setText("");

		}

	}

	// 检查命令格式是否正确
	public boolean checkCommand(String str) {

		if (!str.contains(" ")) {
			if (str.equals("pwd") || str.equals("who") || str.equals("ls")|| str.equals("time")|| str.equals("onlineuser"))
				return true;
		}

		if (str.equals("ls -l"))
			return true;

		if (str.split(" ")[0].equals("chmod") && str.split(" ").length == 3)
			return true;

		if (str.split(" ")[0].equals("mkdir") && str.split(" ").length == 2)
			return true;

		if (str.split(" ")[0].equals("read") && str.split(" ").length == 2)
			return true;

		if (str.split(" ")[0].equals("rm") && str.split(" ").length == 2)
			return true;

		if (str.split(" ")[0].equals("rmdir") && str.split(" ").length == 2)
			return true;

		if (str.split(" ")[0].equals("touch") && str.split(" ").length == 2)
			return true;

		if (str.split(" ")[0].equals("cd") && str.split(" ").length == 2)
			return true;

		if (str.split(" ")[0].equals("kill") && str.split(" ").length == 3)
			return true;

		if (str.split(" ")[0].equals("pause") && str.split(" ").length == 3)
			return true;

		if (str.split(" ")[0].equals("activate") && str.split(" ").length == 3)
			return true;

		if (str.split(" ")[0].equals("distribute") && str.split(" ").length == 3)
			return true;
		
		if (str.split(" ")[0].equals("passwd") && str.split(" ").length == 2)
			return true;

		return false;

	}

	public static void main(String[] args) throws Exception {
		new Client();
	}

}
