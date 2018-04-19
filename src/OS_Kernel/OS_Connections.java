package OS_Kernel;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import A_General.OS;
import Users.Users;

public class OS_Connections implements Runnable,Serializable{
	
	public ServerSocket serverSocket  = null;
	public Socket socket = null;
//	public HashMap<String, OnlineUsers> myList = new HashMap<String, OnlineUsers>();
//	
	public OS_Connections() {
		
//		this.setVisible(false);
//		this.setTitle("Online Users:"+OS.onlineUsers.size());
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.setSize(200,200);
		
		try{
			serverSocket = new ServerSocket(6153);
			System.out.print("ok");
		}catch (Exception e) {
			JOptionPane.showMessageDialog(null, "操作系统连接出错");
			e.printStackTrace();
		}
		
		
		new Thread(this).start();
		
		
	}
	
	
	@Override
	public void run() {
		
		while (true)
		{
			try {
				 socket = serverSocket.accept();
				new OnlineUsers(socket,this);
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "操作系统连接出错");
				try{
					serverSocket.close();
				}catch (Exception ex) {
					// TODO: handle exception
				}
				

			}
		}
		
	}
	
	


}
