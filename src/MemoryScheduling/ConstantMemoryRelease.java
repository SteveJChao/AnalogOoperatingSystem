package MemoryScheduling;

import A_General.OS;
import ConstantMemory.Pieces;
import Process.PCB;

/***
 * 
 * 连续内存回收算法
 * 
 * 当一个进程执行完毕或被某种原因杀死之后
 * 需要回收该进程对应的内存
 * 并将空闲内存中的碎片“紧缩”
 *
 */
public class ConstantMemoryRelease {
	public static boolean releaseMemory(PCB pcb) {
		Pieces pieces = OS.MemoryPage.usedList.getPieces(pcb.pageStart);
		if(pieces == null) {
			//如果内存中不存在这个进程
			return false;
		}
		OS.MemoryPage.usedList.deletePieces(pieces);
		OS.MemoryPage.blankList.releaseAndAddBack(pieces);
		return true;	
	}
	
	
	
	
	
}
