package TestModel;

import A_General.OS;
import DiscreteMemory.Page;
import ProcessScheduling.CPU_Schedul;
import ProcessScheduling.CreatProcess;

public class DiscreteMemoryAllocateTest {
	public static void main(String[] args) {
		CreatProcess.randomCreate(3);
		
		for(int i = 0;i<50;i++)
		{
			
			CPU_Schedul.CPURun();
			OS.running_list.printAll();
			OS.ready_list.printAll();
			if (i == 10) {
				CreatProcess.randomCreate(3);
				System.out.println("new process come");
			}
			
			if (i == 30) {
				CreatProcess.randomCreate(2);
				System.out.println("new process come");
			}
			System.out.println("===========内存内的===========");
			OS.inMemoryPages.printAll();
			System.out.println();
			System.out.println();
			System.out.println("===========进程所拥有的===========");
			for(Page page:OS.running_list.getHead().pageTable)
				System.out.print(page.ID+"  ");
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("===========内存外的===========");
			OS.outMemoryPages.printAll();
			System.out.println();
			
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();

			
		}


		
	}
}
