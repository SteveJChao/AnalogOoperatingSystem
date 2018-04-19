package DiskManagement;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Vector;

public class HandelDisk {

	//从磁盘中读取内容
	public static String readFromDisk(Vector<Integer> disks) {
		
		return DiskSchedualingAlgorithm.diskSchedualingAlgorithm(disks,0,null);
		
	}
	
	//向磁盘中写入内容
	public static String WriteToDisk(Vector<Integer> disks,ArrayList<String> files) {
		
		return DiskSchedualingAlgorithm.diskSchedualingAlgorithm(disks,1,files);
		
	}
	
	//抹除磁盘内容
	public static String ClearDisk(Vector<Integer> disks) {
		
		return DiskSchedualingAlgorithm.diskSchedualingAlgorithm(disks,2,null);
		
	}
	
	
	
	
	
}
