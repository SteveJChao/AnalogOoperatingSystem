package Jobs;

import Process.PCB;


/***
 * 
 * 作业控制块
 *
 */

public class JCB {

	public int JID;
	public int JType;		//作业类型：0为CPU繁忙型、1为I/O繁忙型、2为终端型
	public PCB pcb;
	public JCB next = null;
	
	public JCB(PCB pcb,int type) {
		this.pcb = pcb;
		JID = pcb.PID;
		JType = type;
	}
	
	
	
	
	
	
}
