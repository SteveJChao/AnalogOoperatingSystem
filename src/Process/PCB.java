package Process;

import java.util.Vector;

import A_General.OS;
import DiscreteMemory.Page;
import FileSystem.Dentry;

/***
 * 定义一个类，用来描述进程控制块，每个PCB指向进程实体
 */



public class PCB {
	public int PID;	//进程ID
	public int Ptype;	//进程类型,0代表内核进程、1代表系统进程
	public int Pflag=1;	//进程状态，0代表因某些原因被阻塞，1代表active
	public int Pprio=10;	//进程初始优先级，默认为10
	public String send = null;	//进程发送的信息
	public String Message = null;	//进程接收到的通信信息
	public String info = null;	//进程通信信息--备注部分
	public int blockType = 0;
	public int Pmemory;	//进程初始化所需RAM
	public int Ptime_needed=0;	//进程需要CPU时间
	public Process process;	//PCB对应的进程
	
	
	
	public int pageStart = -1;//该进程在内存页中对应的起始地址（连续存储模型）
	public  Vector<Page> pageTable = new Vector<Page>();	//页表（离散存储模型）
	
	PCB next; //下一个PCB
	
	//一个进程新建立时做出的基本信息初始化
	public PCB(int PID,int Ptype,int Pprio,int Pmemory,int Ptime_needed) {
		 this.PID = PID;	
		 this.Ptype=Ptype;	
		 this.Pprio = Pprio;	
		 this.Pmemory = Pmemory;
		 this.Ptime_needed = Ptime_needed;
		 
		 this.next = null;
	}
	
	
	
}
