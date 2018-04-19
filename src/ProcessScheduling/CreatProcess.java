package ProcessScheduling;

import java.util.HashMap;

import A_General.OS;
import Jobs.JCB;
import MemoryScheduling.MemorySchedul;
import Process.PCB;
import Process.Process;
import Users.Users;


/***
 * 定义一个类，可用来创建三类进程：内核进程、系统进程（测试用）、用户进程
 */


public class CreatProcess {

	//TODO
	public static PCB createKernelProcess() {
		// PCB变量初始化
		int PID = 0;
		int Ptype = 0;
		int Pprio = 100;
		int Ptime_needed = 10;
		int Pmemory = 75;
		boolean NO_SLEEP = true;

		// 进程变量初始化
		int Pthread = 122;
		int Pport = 0;
		String Pname = "Kernel Task";

		// 调度信息初始化
		double Ppower_cost = 23.6;

		// 磁盘信息初始化
		String Disk_write = "7.68M";
		String Disk_read = "2.73M";

		// 初始化,内核进程类型为0，优先级最高，为100
		// 初始状态默认为阻塞状态
		PCB pcb = new PCB(PID, Ptype, Pprio, Pmemory, Ptime_needed);
		pcb.process = new Process(PID, Pname, OS.root,Ptype, Pthread, Pport);
		// 内核进程优先级最高，为100；且不可以被休眠
		pcb.process.init_process_scheduling(Ppower_cost, NO_SLEEP);
		pcb.process.init_process_disk(Disk_write, Disk_read);

		 OS.PCBtable.put(PID, pcb);
		// 在进程就绪队列中加入该进程的信息
		OS.ready_list.addPCB(pcb);
		MemorySchedul.MemoryAllocate(pcb);

		return pcb;
	}
	
	
	
	//实时进程
	public static PCB createCurrentProcess(Users users,String name) {
		// PCB变量初始化
		int PID = 0;
		int Ptype = 0;
		int Pprio = 100;
		int Ptime_needed = 1;
		int Pmemory = 575;
		boolean NO_SLEEP = true;

		// 进程变量初始化
		int Pthread = 2;
		int Pport = 0;
		String Pname = name;

		// 调度信息初始化
		double Ppower_cost = 23.6;

		// 磁盘信息初始化
		String Disk_write = "7.68M";
		String Disk_read = "2.73M";

		// 初始化,内核进程类型为0，优先级最高，为100
		// 初始状态默认为阻塞状态
		PCB pcb = new PCB(PID, Ptype, Pprio, Pmemory, Ptime_needed);
		pcb.process = new Process(PID, Pname, users,Ptype, Pthread, Pport);
		// 内核进程优先级最高，为100；且不可以被休眠
		pcb.process.init_process_scheduling(Ppower_cost, NO_SLEEP);
		pcb.process.init_process_disk(Disk_write, Disk_read);

		 OS.PCBtable.put(PID, pcb);
		// 在进程就绪队列中加入该进程的信息
		OS.ready_list.addPCB(pcb);
		MemorySchedul.MemoryAllocate(pcb);

		return pcb;
	}
	
	
	
	

	public static PCB createSystemProcess() {
		// PCB变量初始化
		int PID = 1;
		int Ptype = 1;
		int Pprio = 5;
		int Ptime_needed = 200;
		int Pmemory = 256;
		boolean NO_SLEEP = true;

		// 进程变量初始化
		int Pthread = 3;
		int Pport = 1;
		String Pname = "  System Test Task";

		// 调度信息初始化
		double Ppower_cost = 0.6;

		// 磁盘信息初始化
		String Disk_write = "29K";
		String Disk_read = "18K";

		// 初始化,内核进程类型为2
		// 初始状态默认为阻塞状态
		PCB pcb = new PCB(PID, Ptype, Pprio, Pmemory, Ptime_needed);
		pcb.process = new Process(PID, Pname,OS.root, Ptype, Pthread, Pport);
		// 系统进程优先级次高，为5；且不可以被休眠
		pcb.process.init_process_scheduling(Ppower_cost, NO_SLEEP);
		pcb.process.init_process_disk(Disk_write, Disk_read);

		 OS.PCBtable.put(PID, pcb);
		// 在进程就绪队列中加入该进程的信息
		OS.ready_list.addPCB(pcb);
		MemorySchedul.MemoryAllocate(pcb);
		
		return pcb;

	}

	public static PCB createUserProcess(Users users,HashMap<String, String> parameters,int type) {
		// PCB变量初始化
		int PID = Integer.parseInt(parameters.get("PID"));
		int Pprio = Integer.parseInt(parameters.get("Pprio"));
		int Pmemory = Integer.parseInt(parameters.get("Pmemory"));
		int Ptype = 2;

		// 进程变量初始化
		int Pthread = (int)(10+100*Math.random());
		int Pport = (int)(10+100*Math.random());
		String Pname = parameters.get("Pname");
		boolean NO_SLEEP = false;

		// 调度信息初始化
		int Ppower_cost = (int)(20 + 10*Math.random());
		int Ptime_needed = Integer.parseInt(parameters.get("Ptime_needed"));

		// 磁盘信息初始化
		String Disk_write = String.valueOf((int)(23+100*Math.random()))+"M";
		String Disk_read = String.valueOf((int)(100*Math.random()))+"M";

		// 初始化,用户进程类型为2，初始状态默认为阻塞状态
		PCB pcb = new PCB(PID, Ptype, Pprio, Pmemory, Ptime_needed);
		pcb.process = new Process(PID, Pname, users,Ptype, Pthread, Pport);
		// 用户进程优先级默认为0；可以被休眠
		pcb.process.init_process_scheduling(Ppower_cost, NO_SLEEP);
		pcb.process.init_process_disk(Disk_write, Disk_read);

		
		
		JCB jcb = new JCB(pcb, type);
		 OS.PCBtable.put(PID, pcb);
		// 在后备队列中加入该进程的信息
		OS.jobsList.addJCB(jcb);
		OS.NextProcessID++;

		return pcb;
	}

	public static void randomCreate(int total) {
//		createKernelProcess();
//		createSystemProcess();
		for (int i = 0; i < total; i++) {
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("PID", String.valueOf( OS.NextProcessID ));
			parameters.put("Pprio",String.valueOf((int)(10*Math.random())));
			parameters.put("Pmemory", String.valueOf((int)(1000* (Math.random() + 0.8))));
			parameters.put("Pname", "Process_"+OS.NextProcessID);
			parameters.put("Ptime_needed", String.valueOf((int)(3+10*Math.random())));
			createUserProcess(OS.root,parameters,0);
		}

	}

}
