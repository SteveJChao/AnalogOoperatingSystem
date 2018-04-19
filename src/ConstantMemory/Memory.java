package ConstantMemory;

/***
	 * 定义一个类，用来描述  ［连续的内存］
	 */

	public class Memory {

		int ID;
		int size;		//页面大小
		int activeProcessNum = 0;		//页面内活跃进程（内存）数量
		int totalProcessNum = 0;			//页面内所包含进程的数量
		int Vstart = 0;	//页面相对开始的位置
		
		public Pieces_LinkList blankList;
		public Pieces_LinkList usedList = new Pieces_LinkList();
		
		
		public Memory(int size) {
			blankList = new Pieces_LinkList(Vstart, size);
			this.size = size;	
		}

		
		
	}

