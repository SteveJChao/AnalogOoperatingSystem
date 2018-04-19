package ProcessScheduling;

import java.io.PrintStream;

import A_General.OS;
import ConstantMemory.Memory;
import DiscreteMemory.Page;
import Jobs.JobScheduling;
import MemoryScheduling.ConstantMemoryRelease;
import MemoryScheduling.MemorySchedul;
import MemoryScheduling.VirtualMemorySchedul;
import Process.PCB;

/***
 * 处理机调度执行的其他操作 除了处理机调度算法之外，还需要执行一些其他的处理 比如：
 * 
 * 1.检查就绪队列中是否存在内核进程，且内核进程准备就绪，这时应先把内核进程放入处理机
 * 
 * 2.检查处理机中是否存在已经完成的进程，如果有，那么移除这些进程
 * 
 * 3.查看阻塞队列中是否有进程已被激活，如果有的话，把它们调入就绪队列
 * 
 * 4.将后备队列中的作业调入内存，创建进程
 * 
 * 5.假设CPU执行一个时间片（就绪队列/后备队列 等待时间＋1、运行的进程运行时间＋1、执行相应功能模块）
 * 
 * 
 * 
 * 维持一个队列 利用队列完成各种算法
 */
public class CPU_Schedul {

	// 在调度之前的一些预处理
	public static void CPURun() {
		if (OS.CPUFLAG == 1) {
			CPU_Schedul.removeFinishTask(); // 移除已经结束的进程
			CPU_Schedul.KernelTaskMoving(); // 检查是否有实时进程调入处理机
			CPU_Schedul.block2ready(); // 阻塞队列内被激活的进程调入就绪队列
			JobScheduling.jobScheduling(); // 后备队列作业调入，分配内存创建进程
			CPU_SchedulAlgorithm.CPUSchedul();//处理机调度算法
			Running(); // 模拟处理机运行一个时间片段
			try {
				new Thread().sleep(100 * OS.TIMEPIECE); // 假设处理机一个时间片大小为TIMEPIECE
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	// 当就绪队列中存在内核进程时,需要把内核进程调入处理机
	public static void KernelTaskMoving() {
		// 当就绪队列中存在内核进程时且内核进程active
		if (OS.ready_list.getPCB(0) != null) {
			PCB kernel = OS.ready_list.getPCB(0);
			OS.ready_list.deletePCB(kernel);
			if (OS.running_list.isFull()) {
				// 如果运行队列满了，需要腾出位置给内核进程
				// 删除最先进入的那个进程，放到就绪队列头部
				PCB tem = OS.running_list.deleteHead();
				OS.running_list.addHead(kernel);
				OS.ready_list.addHead(tem);
			} else {// 如果运行队列没满，直接调入
				OS.running_list.addHead(kernel);
			}
			System.out.println("Kernel Task ... successfully moves to CPU");

		}
	}

	// 移除处理机内已经运行完毕的进程
	public static void removeFinishTask() {
		// 检测有没有进程运行完毕
		while (true) {
			PCB pcb = OS.running_list.getFinsihTask();
			if (pcb == null)
				return;
			// 从运行队列删除该进程
			OS.running_list.deletePCB(pcb);
			// 释放该进程占用的内存
			MemorySchedul.MemoryRelease(pcb);
			System.out.println("Process : " + pcb.PID + "--" + pcb.process.Pname + " finished!");

		}

	}

	// 查看阻塞队列是否有符合条件可以调入就绪队列的进程
	public static void block2ready() {
		// 阻塞队列中有进程在等待,并且阻塞状态被激活
		while (!OS.block_list.isEmpty()) {
			PCB pcb = OS.block_list.getActiveTask();
			if (pcb == null)
				break;
			BlockActivate.activate(pcb);

		}
			//阻塞进程中有进程可以被激活
		OS.block_list.block2ready();
	
	}

	// 过了一个时间片
	public static void Running() {
		if (OS.MemoryAllocateFLAG == 1 && !OS.running_list.isEmpty()) {
			//如果定义为离散存储模型，每一次处理机内进程都要选择一个自己的内存页面
			VirtualMemorySchedul.virtualMemorySchedul(randomSelectPages().ID);
		}
		
		OS.running_list.TimeUsedPlus(); // 首先CPU内所有进程运行一个时间片
		OS.ready_list.TimeWaitedPlus();// 然后就绪队列中所有进程等待时间增加一个时间片
		OS.jobsList.TimeWaitedPlus();// 然后后备队列中所有进程等待时间增加一个时间片
	}

	// 离散存储模型下，每次处理机会随机选取一个内存片
	public static Page randomSelectPages() {
		PCB runningProcess = OS.running_list.getHead();
		int maxVlaue = runningProcess.pageTable.size();
		int index = (int) (Math.random() * maxVlaue);// 随机选取一个页面
		Page page = runningProcess.pageTable.get(index);
		return page;
	}
	
	
	//检查是否有被阻塞的进程，如果有的话，放入阻塞队列
	public void checkBlock() {
		OS.running_list.checkblockProcess();
	}

}
