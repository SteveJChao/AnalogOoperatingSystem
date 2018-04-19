package MemoryScheduling;

import A_General.OS;
import DiscreteMemory.Page;
import Process.PCB;
/***
 * 
 * 离散内存回收算法
 * 
 * 当一个进程执行完毕或被某种原因杀死之后
 * 需要回收该进程对应的内存
 * 
 */
public class DiscreteMemoryRelease {

	public static void pageRelease(PCB pcb) {
		int totalPageNum = pcb.pageTable.size();
		for(int i = 0;i<totalPageNum;i++)
		{
			//在页表中，找到这个进程占据的第 i 个物理块
			Page page = pcb.pageTable.get(i);
			
			//在内存或外存中删除这个页面
			OS.outMemoryPages.deletePage(page);
			OS.inMemoryPages.deletePage(page);
			
			//将这个内存区域置为0，即未被使用
			OS.MemoryList[page.ID] = 0;									
		}
		
		//清空页表
		pcb.pageTable.clear();
		
		
		
		
		
	}
	
	

}
