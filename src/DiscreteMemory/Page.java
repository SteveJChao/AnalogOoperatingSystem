package DiscreteMemory;

import A_General.OS;
import ConstantMemory.Pieces_LinkList;

/***
 * 定义一个类，用来描述  ［离散的内存页面］
 */

public class Page {

	public int ID;
	public int size;		//页面大小
	public int pageStart;	//页面实际开始地址
	public int Vstart = 0;	//页面相对开始的位置
	public int times = 0;
	Page next = null;
	
	public Pieces_LinkList blankList = new Pieces_LinkList(Vstart, size);
	public Pieces_LinkList usedList = new Pieces_LinkList();
	
	
	public Page(int ID) {
		this.size = OS.PageSIZE;
		this.ID = ID;
		this.pageStart = ID * size;
		
	}
	
	
}
