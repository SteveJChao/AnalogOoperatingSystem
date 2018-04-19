package DiscreteMemory;

import A_General.OS;
import Process.PCB;

/***
 * 定义一个链表（队列），用来连接［离散的页面］
 * 主要是为了表示在实际内存中的页面和挂在外存的虚拟页面
 * 
 * 不同页面在虚拟内存中的起始地址不同
 */


public class Pages_LInkList {

	/***
	 * 虚拟内存－页面调度算法
	 * 采用队列的形式描述在内存中的页面和暂时挂在外村的页面
	 */
	
	Page head = null;
	Page rear = null;
	int size;
	int num = 0;

	public Pages_LInkList(int size) {
		this.size = size;
	}
	
	public Pages_LInkList() {
		this.size = OS.TotalPageNum;
	}

	
	/***
	 *  查询模块
	 */
	
	//判断队列是否已满
		public boolean isFull() {
			if(size<=num) {
				return true;
			}
			else {
				return false;
			}
			
		}
		
	
	//查找指定的Page
	public Page getPage(int ID) {
		if(num == 0 || head == null)
		{
			return null;
		}
		
		Page p = head;
		while (p.next != null) {
			if(p.ID == ID)
			{
				return p;
			}
			  
			p = p.next;
		}
		
		return null;
		
	}
	
	//寻找队列中指定元素的前一个
	public Page prePage(Page page) {
		if(page == null || num == 0 || num == 1) {
			return null;
		}
		
		Page p = head;
		while(p != null) {
			if(p.next == page)
				return p;
			p = p.next;
		}
		
		return null;
		
	}
	
	
	//寻找一个不处于活跃状态的页面
	//即此页面中没有内存
	
	/***
	 *  功能/操作模块
	 */
		
	
	//头插
	public boolean addHead(Page page) {
		if(num>=size)
			return false;
		
		if(head == null || num == 0) {
			head =page;
			rear = page;
			num++;
			return true;
		}else {
			page.next = head;
			head = page;
			num++;
			return true;
		}
		
	}
	
	
	//删除队头元素
	public Page deleteHead() {
		if(head == null || num == 0)
		{
			return null;
		}
		
		if(num == 1 || head == rear)
		{
			Page tem = head;
			tem.next = null;
			head = null;
			rear = null;
			num -- ;
			return tem;		
		}else {
			Page tem = head;
			head = head.next;
			tem.next = null;
			num--;
			return tem;
		}
	}


	
	//在队尾插入
	public boolean addPage(Page page) {
		if(isFull() == true || num>=size) {
			return false;
		}
		
		if(head == null || num==0) {
			head = page;
			rear = page;
			num++;
			return true;
		}else {
			rear.next = page;
		rear = page;
		num++;
		return true;
		}
		
		
		
	}
	
	
	// 遍历整个队列，找到使用次数最少的页面
	public Page getMinUsed() {
		Page target = null;
		int value = 9999;
		Page p = head;
		while (p != null) {
			if (p.times < value) {
				value = p.times;
				target = p;
			}
			p = p.next;

		}

		return target;

	}
	
	
	


	
	
	//删除指定的元素
	public boolean deletePage(Page page) {
		if(num == 0 || head == null) {
			return false;
			
		}
		
		if(num == 1 || head == rear) {
			head = null;
			rear = null;
			page.next = null;
			num --;
			return true;
		}
		
		if(page == head)
		{
			deleteHead();
			return true;
		}
		
		
		Page pre = prePage(page);
		if (pre == null) {

			return false;
		}
		
		
		
		if(page == rear) {
			pre.next = null;
			rear = pre;
			num --;
			return true;
		}

		pre.next = page.next;
		page.next = null;
		num -- ;
		return true;
		
		
		
	}

	
	public void printAll() {
		Page p = head;
		while (p != null) {
			System.out.print(p.ID+"  ");
			p = p.next;
		}
		System.out.println();
	}
	
	

	
	
	
}
