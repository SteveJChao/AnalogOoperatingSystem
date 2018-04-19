package TestModel;

import A_General.Init;
import A_General.OS;
import MemoryScheduling.ConstantMemoryRelease;
import ProcessScheduling.CPU_Schedul;
import ProcessScheduling.CreatProcess;

public class ConstantMemoryAllocateTest {
public static void main(String[] args) {
	
	try {
		Init.loader();
	} catch (Exception e) {
		System.err.println("Init Error!");
		e.printStackTrace();
		System.exit(0);
	}
	
	CreatProcess.randomCreate(59);
	
	for(int i = 0;i<50000;i++)
	{
		
		
		if (i == 10) {
			CreatProcess.randomCreate(63);
			System.out.println("new process come");
		}
		
		if (i == 30) {
			CreatProcess.randomCreate(42);
			System.out.println("new process come");
		}
		System.out.println("===========未被使用的===========");
		OS.MemoryPage.blankList.printALL();
		System.out.println("===========已被使用的===========");
		OS.MemoryPage.usedList.printALL();
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		CPU_Schedul.CPURun();
		
	}


	
}
}
