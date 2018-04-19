package TestModel;

import Process.PCB;

public class outputTest {

	
	public static String printALL(PCB pcb) {
		
		
		String str = "   ID: "+pcb.PID+"\n"+"   Name: "+pcb.process.Pname+"\n"+"\n"+
							"   Priority: "+pcb.Pprio+"\n"+"  Memory_used: "+pcb.Pmemory+"\n"+
							"   isActivate: "+pcb.Pflag+"\n"+"  NO_SLEEP:"+pcb.process.NO_SLEEP+"\n"+
							"   Owner: "+pcb.process.Powner+"\n"+"  Group: "+pcb.process.PownerGroup+"\n"+"\n"+
							
							
							"   Ptime_needed: "+pcb.Ptime_needed+"\n"+"  Ptime_used: "+pcb.process.Ptime_used+"\n"+
							"   Ptime_waited: "+pcb.process.Ptime_waited+"\n"+"  Power_cost: "+pcb.process.Ppower_cost+"\n"+"\n"+
							
							"   Disk_write: "+pcb.process.Disk_write+"\n"+"  Disk_read: "+pcb.process.Disk_read+"\n"+"\n"+"\n"+
							
							"   Message_Info: "+pcb.info;

		System.out.println(str);
		return str;
	}
	
	
	
	
	
	
	
	
	
}
