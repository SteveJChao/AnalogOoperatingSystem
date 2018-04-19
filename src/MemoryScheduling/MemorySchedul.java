package MemoryScheduling;

import A_General.OS;
import Process.PCB;


/***
 * 
 * 通过用户指定的内存分配方式（连续／离散）
 * 决定采用什么方式分配内存
 *
 */

public class MemorySchedul {
	
	public static void MemoryAllocate(PCB pcb) {
		
		switch (OS.MemoryAllocateFLAG) {
		case 0:
			ConstantMemoryAllocate.allocateMemory(pcb);
			break;

		case 1:
			DiscreteMemoryAllocate.pageAllocate(pcb);
			break;
		default:
			ConstantMemoryAllocate.allocateMemory(pcb);
			break;
		}
		
	}
		
		public static void MemoryRelease(PCB pcb) {
			
			switch (OS.MemoryAllocateFLAG) {
			case 0:
				ConstantMemoryRelease.releaseMemory(pcb);
				break;

			case 1:
				DiscreteMemoryRelease.pageRelease(pcb);
				break;
			default:ConstantMemoryRelease.releaseMemory(pcb);
				break;
			}
		
	}
	
	
}
