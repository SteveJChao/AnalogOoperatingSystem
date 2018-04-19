package TestModel;

import javax.naming.InitialContext;

import A_General.OS;
import DiscreteMemory.Page;
import MemoryScheduling.VirtualMemorySchedul;

public class VirtualMemorySchedulingTest {

	public static void randomCreatePages() {
		for (int i = 0; i < 10; i++) {
			Page page = new Page(i);
			OS.outMemoryPages.addPage(page);
		}
	}

	public static int[] Init() {
		int[] newarray = new int[20];
		newarray[0] = 7;
		newarray[1] = 0;
		newarray[2] = 1;
		newarray[3] = 2;
		newarray[4] = 0;
		newarray[5] = 3;
		newarray[6] = 0;
		newarray[7] = 4;
		newarray[8] = 2;
		newarray[9] = 3;
		newarray[10] = 0;
		newarray[11] = 3;
		newarray[12] = 2;
		newarray[13] = 1;
		newarray[14] = 2;
		newarray[15] = 0;
		newarray[16] = 1;
		newarray[17] = 7;
		newarray[18] = 0;
		newarray[19] = 1;
		return newarray;

	}

	public static void main(String[] args) {

		randomCreatePages();
		System.out.println("=================内     存====================");
		OS.inMemoryPages.printAll();
		System.out.println("=================外      存 ====================");
		OS.outMemoryPages.printAll();
		for (int i : Init()) {
			System.out.println();
			System.out.println("============            ［   " + i + "   ］              =============");
			System.out.println();
			VirtualMemorySchedul.LRU(i);
			// System.out.println("内存：" +Variable.inMemoryPages.num);
			// System.out.println("外存：" +Variable.outMemoryPages.num);
			// System.out.println(tag);
			System.out.println("=================内     存====================");
			OS.inMemoryPages.printAll();
			System.out.println("=================外      存 ====================");
			OS.outMemoryPages.printAll();
		}

	}
}
