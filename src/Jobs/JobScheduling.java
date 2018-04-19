package Jobs;

import A_General.OS;
import ConstantMemory.Memory;
import MemoryScheduling.ConstantMemoryAllocate;
import MemoryScheduling.MemorySchedul;
import Process.PCB;

/***
 * 作业调度算法 在后备队列中调取相应的作业到内存，创建进程 （FCFS、SPN、HPF、HRRN）
 * 
 * 
 * 维持一个队列 利用队列完成各种算法
 */

public class JobScheduling {

	// 作业调度
	public static void jobScheduling() {

		switch (OS.JobSchedulFLAG) {
		case 0:
			FCFS();
			break;
		case 1:
			SPN();
			break;
		case 2:
			HPF();
			break;
		case 3:
			HRRN();
			break;

		default:
			FCFS();
			break;
		}

	}

	// 判断内存中进程数目是否超标 或 内存已满
	public static boolean isMemoryFull() {

		if (OS.TotalProcessNum <= OS.running_list.getNum() + OS.ready_list.getNum() + OS.block_list.getNum())
			return true;
		if (OS.running_list.stasticMemory() + OS.ready_list.stasticMemory() + OS.block_list.stasticMemory()
				+ 1024 >= OS.MaxMemory)
			return true;
		return false;

	}

	// 先来先服务(First Come First Server)，哪个先到先给哪个服务
	public static void FCFS() {
		// 假设运行队列有空闲，且就绪队列中有进程在等待
		while (!isMemoryFull() && !OS.jobsList.isEmpty()) {
			PCB pcb = OS.jobsList.deleteHead().pcb;
			OS.ready_list.addPCB(pcb);
			MemorySchedul.MemoryAllocate(pcb);
			pcb.process.Ptime_waited = 0;
		}

	}

	// 短进程优先(Short Process Next)，用时最短的进程先被服务
	public static void SPN() {
		// 假设运行队列有空闲，且就绪队列中有进程在等待
		while (!isMemoryFull() && !OS.jobsList.isEmpty()) {
			JCB job = OS.jobsList.getMinTimeUsed(); // 找到所用时间最短的进程
			if (job == null)
				break;
			OS.jobsList.deleteJCB(job);
			OS.ready_list.addPCB(job.pcb);
			MemorySchedul.MemoryAllocate(job.pcb);
			job.pcb.process.Ptime_waited = 0;
		}
	}

	// 高优先权调度(Highest Priority First)，找到最高优先权的进程
	public static void HPF() {
		// 假设运行队列有空闲，且就绪队列中有进程在等待
		while (!isMemoryFull() && !OS.jobsList.isEmpty()) {
			JCB job = OS.jobsList.getMaxPrio(); // 找到优先级最高的进程
			if (job == null)
				break;
			OS.jobsList.deleteJCB(job);
			OS.ready_list.addPCB(job.pcb);
			MemorySchedul.MemoryAllocate(job.pcb);
			job.pcb.process.Ptime_waited = 0;
		}
	}

	// 最高响应比优先调度(Highest Response Ratio Next)，找到最高响应比的进程
	// 响应比R定义如下： R =(W+T)/T = 1+W/T
	public static void HRRN() {
		// 假设就绪队列中有进程在等待
		while (!isMemoryFull() && !OS.jobsList.isEmpty()) {
			JCB job = OS.jobsList.getHighestResponseRatio(); // 找到响应比最高的进程
			if (job == null)
				break;
			OS.jobsList.deleteJCB(job);
			OS.ready_list.addPCB(job.pcb);
			MemorySchedul.MemoryAllocate(job.pcb);
			job.pcb.process.Ptime_waited = 0;
		}
	}

}
