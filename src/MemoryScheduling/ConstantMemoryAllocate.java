package MemoryScheduling;

import java.awt.Point;

import A_General.OS;
import ConstantMemory.Memory;
import ConstantMemory.Pieces;
import DiscreteMemory.Page;
import Process.PCB;


/***
 * 
 * 连续内存分配算法
 * 
 * 包括首次适应算法、循环首次适应算法、最佳适配算法、最坏适配算法
 * 
 * 为创建的进程在内存空间中，分配连续的内存
 *
 */


public class ConstantMemoryAllocate {

	//连续内存分配算法
	public static void allocateMemory(PCB pcb) {
		switch (OS.ConstantMemoryAllocateFLAG) {
		case 0:
			FirstFit(pcb);
			break;
		case 1:
			NextFit(pcb);
			break;
		case 2:
			BestFit(pcb);
			break;
		case 3:
			WorstFit(pcb);
			break;
			
		default:
			FirstFit(pcb);
			break;
		}
		
	}
	
	
	
	
	
	
	// 首次适应算法
	// 从内存区内找到一个合适的块，为进程分配内存
	// 输入当前申请内存的PCB
	public static boolean FirstFit(PCB pcb) {

		int size = pcb.Pmemory;

		// 寻找满足要求的内存块
		Pieces pieces = OS.MemoryPage.blankList.getFirstBlock(null, size);
		if (pieces == null) {
			// 没用足够的内存
			return false;
		}
		Pieces target = OS.MemoryPage.blankList.cutPieces(pieces, size);
		OS.MemoryPage.usedList.addPieces(target);

		// 将该内存区域与PCB绑定
		pcb.pageStart = target.start;
		return true;

	}

	// 循环首次适应算法
	// 有一个全局指针记录上一次访问过的内存位置，下一次直接向下移动
	public static boolean NextFit(PCB pcb) {

		int size = pcb.Pmemory;

		// 找到页面之后，需要寻找内存块
		Pieces pieces = OS.MemoryPage.blankList.getFirstBlock(OS.point, size);
		if (pieces == null) {
			// 没用足够的内存,则从头开始搜寻
			if (FirstFit(pcb) == false)
				return false;
			else {
				return true;
			}
		} else {
			Pieces target = OS.MemoryPage.blankList.cutPieces(pieces, size);
			OS.point = pieces; // 指向当前的位置
			OS.MemoryPage.usedList.addPieces(target);

			// 将该内存区域与PCB绑定
			pcb.pageStart = target.start;
			return true;
		}

	}

	// 最优适应算法
	// 首先选择符合条件的、内存片最小的那个内存块分配内存
	public static boolean BestFit(PCB pcb) {

		int size = pcb.Pmemory;
		Pieces pieces = OS.MemoryPage.blankList.getMinBlock(size);
		if (pieces == null) {
			// 没有空闲的内存页
			return false;
		}

		Pieces target = OS.MemoryPage.blankList.cutPieces(pieces, size);
		OS.MemoryPage.usedList.addPieces(target);

		// 将该内存区域与PCB绑定
		pcb.pageStart = target.start;

		return true;

	}

	// 最坏适应算法
	// 首先选择符合条件的、内存片最大的那个内存块分配内存
	public static boolean WorstFit(PCB pcb) {

		int size = pcb.Pmemory;
		Pieces pieces = OS.MemoryPage.blankList.getMaxBlock(size);
		if (pieces == null) {
			// 没有空闲的内存页
			return false;
		}

		Pieces target = OS.MemoryPage.blankList.cutPieces(pieces, size);
		OS.MemoryPage.usedList.addPieces(target);

		// 将该内存区域与PCB绑定
		pcb.pageStart = target.start;

		return true;

	}

}
