package A_General;

import java.io.PrintStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Vector;

import ConstantMemory.Memory;
import ConstantMemory.Pieces;
import DiscreteMemory.Page;
import DiscreteMemory.Pages_LInkList;
import DiskManagement.SuperBlock;
import FileSystem.Dentry;
import Jobs.JCB_LinkList;
import OS_Kernel.OS_Connections;
import Process.PCB;
import Process.PCB_LinkList;
import Users.Users;

/***
 * 全局变量的定义
 */

public class OS implements Serializable{

	
	//在线的用户
	public static OS_Connections server;
	public static HashMap<String, PrintStream> onlineUsers = new HashMap<String, PrintStream>();
	public static HashMap<String, Users> userList;
	
	// 下一个新创建进程的ID
	public static int NextProcessID = 11;

	// 时间片大小(*100ms)
	public static int TIMEPIECE = 5;

	// 轮转时间片大小
	public static int TIMEPIECESSIZE = 4;

	// 处理机的数量
	public static int CPU_num = 3;

	// 内存中允许得最多进程数目
	public static int TotalProcessNum = 20;

	// 内存总大小(Bytes)
	public static int MaxMemory = 64 * 1024;

	// 离散存储模型中，每一页内存大小(Bytes)
	public static int PageSIZE = 128;

	// 离散存储模型中，页面总数
	public static int TotalPageNum = (MaxMemory / PageSIZE);

	// 离散存储模型中，记录内存使用情况的数组
	public static int[] MemoryList = new int[TotalPageNum];
	
	//磁盘超级块
	public static SuperBlock superBlock;
	
	//磁盘超级块
	public static String[] MyDisk = new String[20 * 20];

	/***
	 * ======================== 标志位======================================
	 */

	// 处理机状态位
	// 0－暂停；1－运行；
	public static int CPUFLAG = 1;

	// 处理机调度算法：
	// 0－先来现服务；1－短进程优先；2－高优先级；
	// 3－抢占式高优先级；4－高响应比；5－时间片轮转
	public static int CPUSchedulFLAG = 4;

	// 作业调度算法：
	// 0－先来现服务；1－短作业优先；2－高优先级；
	// 3－高响应比；
	public static int JobSchedulFLAG = 0;

	// 内存分配算法：
	// 0－连续分配算法；1－离散内存分配算法（分页）；
	public static int MemoryAllocateFLAG = 0;

	// 连续内存分配算法：
	// 0－首次分配算法；1－循环首次分配算法；2－最佳匹配算法；
	// 3－最坏匹配算法；
	public static int ConstantMemoryAllocateFLAG = 2;
	
	//虚拟页面置换算法：
	//0-LRU；1-LFU
	public static int VirtualMemoryScheduelFLAG = 0;
	
	//磁盘算法：
	//0-FCFS；1-SCAN；2-CSCAN
	public static int DiskScheduelFLAG = 1;

	/***
	 * ======================== 全局状态队列======================================
	 */

	// 操作系统内核进程
	// public static PCB kernel = null;

	// 进程名字和ID所对应的哈希表
	public static HashMap<Integer, PCB> PCBtable = new HashMap<Integer, PCB>();// ID--Object
	public static HashMap<String, Integer> PCBNametable = new HashMap<String, Integer>();// Name--ID
	// 进程队列：运行队列－－就绪队列－－阻塞队列
	public static PCB_LinkList running_list = new PCB_LinkList(CPU_num);
	public static PCB_LinkList ready_list = new PCB_LinkList();
	public static PCB_LinkList block_list = new PCB_LinkList();

	// 后备队列
	public static JCB_LinkList jobsList = new JCB_LinkList();

	// 离散分配时，页表的定义
//	private static Vector<Page> PageTable = new Vector<Page>();

	// 离散分配时，内存中实际存在的页面
	public static Pages_LInkList inMemoryPages = new Pages_LInkList(CPU_num * 3);
	public static Pages_LInkList outMemoryPages = new Pages_LInkList();

	// 连续分配时，内存为一整个内存页面
	public static Memory MemoryPage = new Memory(MaxMemory);
	public static Pieces point = null;
	
	
	//初始化的文件系统
	public static Dentry ROOT ;
	
	//系统用户
	public static Users root ;
}
