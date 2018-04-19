package ProcessScheduling;


import A_General.OS;
import Process.PCB;

public class BlockActivate {

	//阻塞某个进程，并且输入这个进程阻塞的理由
	public static void block(PCB pcb) {
		OS.running_list.deletePCB(pcb);
		OS.block_list.addHead(pcb);
		pcb.Pflag = 0;
	}
	
	
	public static void activate(PCB pcb) {
		
		OS.block_list.deletePCB(pcb);
		OS.ready_list.addPCB(pcb);
		pcb.Pflag = 1;
		pcb.blockType = -1;
		pcb.Message = null;
		pcb.info = null;		
		
		
	}
}
