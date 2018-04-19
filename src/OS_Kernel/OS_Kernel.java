package OS_Kernel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import A_General.Init;
import A_General.OS;
import FileSystem.PackageCommand;
import ProcessScheduling.CPU_Schedul;

public class OS_Kernel {

	public OS_Kernel() {
		
			try {
				Init.loader();
			} catch (Exception e) {
				System.err.println("Init Error!");
				e.printStackTrace();
				System.exit(0);
			}
			
			
			OS.server = new OS_Connections();


//				while(true)
//			CPU_Schedul.CPURun();

			
			
			

	}


	public static void main(String[] args) throws Exception{
		
		new OS_Kernel();
		
	}
}
