package TestModel;

import A_General.Init;
import A_General.OS;
import Process.PCB;
import ProcessCommunication.MessagePassingSystem;
import ProcessScheduling.CPU_Schedul;
import ProcessScheduling.CPU_SchedulAlgorithm;
import ProcessScheduling.CreatProcess;

public class ProcessSchdedulingTest {

	public static void main(String[] args) {

		try {
			Init.loader();
		} catch (Exception e) {
			System.err.println("Init Error!");
			System.exit(0);
		}
		
		CreatProcess.randomCreate(2);
		OS.running_list.printAll();
		OS.ready_list.printAll();
		OS.block_list.printAll();
		System.out.println("********************************************************************************************");
		System.out.println("********************************************************************************************");
		System.out.println("********************************************************************************************");
		
		int i = 0;
		while (true) {
			if(i>15)
				return;
			
			
			if(i == 3)
			System.out.println(MessagePassingSystem.send("kill", OS.running_list.getHead(), "Process_#9", "kill you!"));
			
			if(i == 5)
				System.out.println(MessagePassingSystem.send("pause", OS.running_list.getHead(), "Process_#10", "pause you!"));
			
			if(i == 8)
				System.out.println(MessagePassingSystem.send("activate", OS.running_list.getHead(), "Process_#10", "activate you!"));
			CPU_Schedul.CPURun();
			System.out.println("********************************************************************************************");
			System.out.println("********************************************************************************************");
			System.out.println("********************************************************************************************");

			OS.running_list.printAll();
			OS.ready_list.printAll();
			OS.block_list.printAll();
			i++;
		}
	}
	
	
	
	
}
