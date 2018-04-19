package FileSystem;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import A_General.Init;
import A_General.OS;
import FileSystem.Dentry;

public class DentryTest extends JFrame{

	int total_num;
	JLabel[] labels_pool;
	static int cursor = 1;
	int x_current = 50;
	static int y_current = 20;
	
	public void printNode(Dentry root,int x_current) {
		
		y_current += 20;
		labels_pool[cursor] = new JLabel(root.dirName);
		labels_pool[cursor].setSize(70,20);
		labels_pool[cursor].setLocation(x_current, y_current);
		this.add(labels_pool[cursor]);
		cursor++;
		
		if(root.dirContains.size() <= 0)
			return;
		
		
		for(String fileName:root.dirContains.keySet())
		{
			printNode(root.dirContains.get(fileName), x_current+20);
		}
		
		
		
		
	}
	
	
	
	public DentryTest() {
		// TODO Auto-generated constructor stub
		try {
			Init.loader();
		} catch (Exception e) {
			System.err.println("Init Error!");
			e.printStackTrace();
			System.exit(0);
		}
		
		

		
		
		total_num = Dentry.getTotalDirNum(OS.ROOT)+Dentry.getTotalFileNum(OS.ROOT);
		
		labels_pool = new JLabel[total_num];
		
		
		this.setSize(600,700);
	

		
		labels_pool[0] = new JLabel(OS.ROOT.dirName);
		labels_pool[0].setSize(20,20);
		labels_pool[0].setLocation(x_current, y_current);
		this.add(labels_pool[0]);
		
		
		for(String fileName:OS.ROOT.dirContains.keySet())
		{

			printNode(OS.ROOT.dirContains.get(fileName), x_current+20);
			
		}
		
		
		


		
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		
	}
	public static void main(String[] args) {
		new DentryTest();
	}
	
	
	
	
	
	
	
	
	
	
}
