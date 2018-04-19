package DiskManagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Vector;

import A_General.OS;

public class DiskSchedualingAlgorithm {

	public static String diskSchedualingAlgorithm(Vector<Integer> disks, int flag, ArrayList<String> files) {
		switch (OS.DiskScheduelFLAG) {
		case 0:
			return FCFS(disks,flag,files);

		case 1:
			return SCAN(disks,flag,files);

		case 2:
			return CSCAN(disks,flag,files);

		default:
			return FCFS(disks,flag,files);
		}
	}

	// 先来先服务算法（FCFS）
	// flag :0-读文件；1-抹除并写文件；2-抹除文件
	public static String FCFS(Vector<Integer> disks, int flag, ArrayList<String> files) {
		if (flag == 0) {
			String info = "";
			for (int diskID : disks) {
				info += OS.MyDisk[diskID].substring(1);
			}
			return info;
		} else if (flag == 1) {
			int i=0;
			System.err.println(disks.size());
			for (int diskID : disks) {
				System.err.println(diskID+"   "+files.get(i));
				OS.MyDisk[diskID] = "$";
				OS.MyDisk[diskID] += files.get(i);
				i++;
			}
			return null;
			
		} else {
			for (int diskID : disks) {
				OS.MyDisk[diskID] = "$";
			}
			return null;
		}

	}

	// 扫描算法（SCAN）
	public static String SCAN(Vector<Integer> disks, int flag, ArrayList<String> files) {
		int current_cursor = -1;//当前的游标
		if (flag == 0) {
			String info = "";
			for (int diskID : disks) {
				info += OS.MyDisk[diskID].substring(1);
			}
			return info;
		} else if (flag == 1) {
			int i=0;
			System.err.println(disks.size());
			for (int diskID : disks) {
				System.err.println(diskID+"   "+files.get(i));
				OS.MyDisk[diskID] = "$";
				OS.MyDisk[diskID] += files.get(i);
				i++;
			}
			return null;
			
		} else {
			for (int diskID : disks) {
				OS.MyDisk[diskID] = "$";
			}
			return null;
		}

	}

	// 循环扫描算法（CSCAN）
	public static String CSCAN(Vector<Integer> disks, int flag, ArrayList<String> files) {
		int current_cursor = -1;//当前的游标
		int rollback = -1;//末尾的游标
		if (flag == 0) {
			String info = "";
			for (int diskID : disks) {
				info += OS.MyDisk[diskID].substring(1);
			}
			return info;
		} else if (flag == 1) {
			int i=0;
			System.err.println(disks.size());
			for (int diskID : disks) {
				System.err.println(diskID+"   "+files.get(i));
				OS.MyDisk[diskID] = "$";
				OS.MyDisk[diskID] += files.get(i);
				i++;
			}
			return null;
			
		} else {
			for (int diskID : disks) {
				OS.MyDisk[diskID] = "$";
			}
			return null;
		}

	}

}
