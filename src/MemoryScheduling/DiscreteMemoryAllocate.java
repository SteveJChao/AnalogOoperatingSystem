package MemoryScheduling;

import A_General.OS;
import DiscreteMemory.Page;
import Process.PCB;
/***
 * 
 * 离散内存分配算法
 * 
 * 为每一个新创建的进程分配对应的内存
 * 该内存表现形式为离散的物理内存块
 * 页表中对应着每个进程相对块对应的实际物理块
 *
 */
public class DiscreteMemoryAllocate {

	// 为一个进程分配内存，按页（块）分配
	public static boolean pageAllocate(PCB pcb) {
		// 需要分配的内存块总数
		int totalPageNum = (pcb.Pmemory / OS.PageSIZE) + 1;
		for (int i = 0; i < OS.TotalPageNum; i++) {

			if (OS.MemoryList[i] == 0) {// 如果该物理块空闲，则可以分配内存
				Page page = new Page(i);
				OS.outMemoryPages.addPage(page); // 在虚拟存储器中加入该页面
				pcb.pageTable.add(page); // 在该进程对应的页表中加入该页面
				OS.MemoryList[i] = 1; // 标志该物理块已经被使用
				totalPageNum--;
			}
			if (totalPageNum <= 0)
				break; // 所有的内存都已经被分配

		}
		
		
		if(totalPageNum > 0) {
			//如果没有足够的内存物理块给进程分配，撤销分配的操作
			DiscreteMemoryRelease.pageRelease(pcb);
			return false;
		}
		return true;
	}

}
