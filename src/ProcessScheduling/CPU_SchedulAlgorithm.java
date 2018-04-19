package ProcessScheduling;

import java.awt.image.Kernel;

import A_General.OS;
import Jobs.JobScheduling;
import Process.PCB;

/***
 * 处理机调度算法 本次模拟的操作系统允许有多个处理机 
 * 也就是说在某个时刻可允许多个进程并行处理 （FCFS、SPN、HPF、SHPF、HRRN）
 * 
 * 
 * 维持一个队列 利用队列完成各种算法
 */

public class CPU_SchedulAlgorithm {

	
	
	//作业调度
			public static void CPUSchedul() {
				
				switch (OS.CPUSchedulFLAG) {
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
					SHPF();
					break;
				case 4:
					HRRN();
					break;
				case 5:
					RR(OS.TIMEPIECESSIZE);
					break;
					
				default:
					FCFS();
					break;
				}
				
				
			}


	// 先来先服务(First Come First Server)，哪个先到先给哪个服务
	public static void FCFS() {

		// 假设运行队列有空闲，且就绪队列中有进程在等待
		while (!OS.running_list.isFull() && !OS.ready_list.isEmpty()) {
			PCB pcb = OS.ready_list.deleteHead();
			OS.running_list.addPCB(pcb);
			pcb.process.Ptime_waited = 0;
		}

	}

	// 短进程优先(Short Process Next)，用时最短的进程先被服务
	public static void SPN() {

		// 假设运行队列有空闲，且就绪队列中有进程在等待
		while (!OS.running_list.isFull() && !OS.ready_list.isEmpty()) {
			PCB pcb = OS.ready_list.getMinTimeUsed(); // 找到所用时间最短的进程
			if (pcb == null)
				break;
			OS.ready_list.deletePCB(pcb);
			OS.running_list.addPCB(pcb);
			pcb.process.Ptime_waited = 0;
		}
	}

	// 非抢占式－高优先权调度(Highest Priority First)，找到最高优先权的进程
	public static void HPF() {

		// 假设运行队列有空闲，且就绪队列中有进程在等待
		while (!OS.running_list.isFull() && !OS.ready_list.isEmpty()) {
			PCB pcb = OS.ready_list.getMaxPrio(); // 找到优先权最高的进程
			if (pcb == null)
				break;
			OS.ready_list.deletePCB(pcb);
			OS.running_list.addPCB(pcb);
			pcb.process.Ptime_waited = 0;
		}

	}

	// 抢占式－高优先权调度(Seize-Highest Priority First)，找到最高优先权的进程
	public static void SHPF() {

		// 假设就绪队列中有进程在等待
		while (!OS.ready_list.isEmpty()) {

			PCB ready = OS.ready_list.getMaxPrio(); // 找到优先权最高的进程
			if (ready == null)
				break;
			if (OS.running_list.isFull() == false) {// 运行队列有空闲
				OS.ready_list.deletePCB(ready);
				OS.running_list.addPCB(ready);
				ready.process.Ptime_waited = 0;
			} else {// 运行队列无空闲，则找到运行队列中优先级低的进程
				PCB runnning = OS.running_list.getMinPrio();
				if (runnning == null)
					break;
				if (runnning.Pprio < ready.Pprio) {
					OS.running_list.deletePCB(runnning);
					OS.ready_list.deletePCB(ready);
					OS.running_list.addPCB(ready);
					OS.ready_list.addPCB(runnning);
					runnning.Ptime_needed -= runnning.process.Ptime_used; // 总共所需时间减去时间片
					runnning.process.Ptime_used = 0;
					ready.process.Ptime_waited = 0;
				} else {
					break;
				}
			}

		}

	}

	// 最高响应比优先调度(Highest Response Ratio Next)，找到最高响应比的进程
	// 响应比R定义如下： R =(W+T)/T = 1+W/T
	public static void HRRN() {

		// 假设就绪队列中有进程在等待
		while (!OS.ready_list.isEmpty()) {
			PCB ready = OS.ready_list.getHighestResponseRatio(); // 找到响应比最高的进程
			if (ready == null) {
				break;
			}
			if (OS.running_list.isFull() == false) {// 运行队列有空闲
				OS.ready_list.deletePCB(ready);
				OS.running_list.addPCB(ready);
			} else {// 运行队列无空闲，则找到运行队列中响应比最低的进程
				PCB runnning = OS.running_list.getLowestResponseRatio();
				double running_RR = 1 + (runnning.process.Ptime_waited + 1) / (runnning.process.Ptime_used + 1);
				double ready_RR = 1 + (ready.process.Ptime_waited + 1) / (ready.process.Ptime_used + 1);
				if (running_RR < ready_RR) {
					OS.running_list.deletePCB(runnning);
					OS.ready_list.deletePCB(ready);
					OS.running_list.addPCB(ready);
					OS.ready_list.addPCB(runnning);
				} else {
					break;
				}
			}

		}
	}

	// 时间片轮转调度(Round-Robin)，找到时间片为0的进程替换掉
	public static void RR(int pieces) {

		// 假设就绪队列中有进程在等待
		while (!OS.ready_list.isEmpty()) {
			PCB ready = OS.ready_list.getMaxPrio(); // 找到优先权最高的进程
			if (ready == null) {
				break;
			}
			if (OS.running_list.isFull() == false) {// 运行队列有空闲
				OS.ready_list.deletePCB(ready);
				OS.running_list.addPCB(ready);
				ready.process.Ptime_waited = 0;
			} else {// 运行队列无空闲，则找到运行队列中时间片用光的
				PCB runnning = OS.running_list.getTimePieceRunOutOf(pieces);
				if (runnning == null) {
					break;// 如果没有时间片用光的则跳过
				} else {
					OS.running_list.deletePCB(runnning);
					OS.ready_list.deletePCB(ready);
					OS.running_list.addPCB(ready);
					OS.ready_list.addPCB(runnning);
					runnning.Ptime_needed -= runnning.process.Ptime_used; // 总共所需时间减去时间片
					runnning.process.Ptime_used = 0;
					ready.process.Ptime_waited = 0;
				}
			}

		}

	}

}
