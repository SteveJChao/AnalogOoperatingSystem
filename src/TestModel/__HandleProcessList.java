package TestModel;

import A_General.OS;
import Process.PCB;


/***
 * 这个类暂时没用到，但留着，或许可以用在外存管理
 */


public class __HandleProcessList {

	
	/**
	 * 处理进程队列的函数
	 * 如果没有找到对应的进程PCB或者删除进程失败
	 * 就返回false
	 */
	// 阻塞-------->就绪
	public boolean block2ready(int PID) {
		PCB pcb = OS.block_list.getPCB(PID);
		if (pcb == null)
			return false;
		if (OS.block_list.deletePCB(pcb) == false)
			return false;
		OS.ready_list.addPCB(pcb);
			return true;
	}

	// 就绪------->运行
	public boolean ready2running(int PID) {
		PCB pcb = OS.ready_list.getPCB(PID);
		if (pcb == null)
			return false;
		if (OS.ready_list.deletePCB(pcb) == false)
			return false;
		OS.running_list.addPCB(pcb);
		return true;
	}

	// 运行-------->就绪
	public boolean running2ready(int PID) {
		PCB pcb = OS.running_list.getPCB(PID);
		if (pcb == null)
			return false;
		if (OS.running_list.deletePCB(pcb) == false)
			return false;
		OS.ready_list.addPCB(pcb);
		return true;
	}

	// 运行-------->阻塞
	public boolean running2block(int PID) {
		PCB pcb = OS.running_list.getPCB(PID);
		if (pcb == null)
			return false;
		if (OS.running_list.deletePCB(pcb) == false)
			return false;
		OS.block_list.addPCB(pcb);
		return true;
	}

	// 就绪-------->阻塞
	public boolean ready2block(int PID) {
		PCB pcb = OS.ready_list.getPCB(PID);
		if (pcb == null)
			return false;
		if (OS.ready_list.deletePCB(pcb) == false)
			return false;
		OS.block_list.addPCB(pcb);
		return true;
	}

	public boolean deletePCB(int PID) {
		PCB pcb = OS.block_list.getPCB(PID);
		boolean flag = OS.block_list.deletePCB(pcb);
		return flag;
	}

}
