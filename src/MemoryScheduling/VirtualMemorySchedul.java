package MemoryScheduling;

import A_General.OS;
import DiscreteMemory.Page;

/***
 * 虚拟内存替换算法 由于本次模拟多处理机多任务，因此页面替换算法使用LRU （FIFO和Clock可能会出现死锁现象）
 * 
 * 
 * 维持一个队列 每当把进程放入到处理机中,就把这个页面对应的内存页面放到队尾
 */
public class VirtualMemorySchedul {

	public static void virtualMemorySchedul(int pageID) {

		switch (OS.VirtualMemoryScheduelFLAG) {
		case 0:
			LRU(pageID);
			break;

		case 1:
			LFU(pageID);
			break;

		default:
			LRU(pageID);
			break;
		}

	}

	public static boolean LRU(int pageID) {
		// Least Recently Used
		// 维持一个队列，当进程放入处理机时，就把这个进程所对应的内存页面从插入队头（active）
		// 这样，处于队尾的就是最近最久没有被使用过的内存页面

		// 1.检查该页面是否已在内存中,如果在内存中，需要调整内存中页面的队列顺序
		Page innerPage = OS.inMemoryPages.getPage(pageID);
		if (innerPage != null) {
			OS.inMemoryPages.deletePage(innerPage);
			OS.inMemoryPages.addPage(innerPage);
			return true;
		}

		// 2.如果不在内存中，需要从外存调入，先检查是否在外存中
		Page outterPage = OS.outMemoryPages.getPage(pageID);
		if (outterPage == null) {
			return false;
		}

		// 3.当前内存中还有位置可以放下新的页面，也就是说内存没满,直接放到队尾
		if (OS.inMemoryPages.isFull() == false) {
			OS.outMemoryPages.deletePage(outterPage);
			OS.inMemoryPages.addPage(outterPage);
			return true;
		}

		// 4.当前内存已满,替换掉最久没有使用的那个页面（头部）
		else {
			Page tem = OS.inMemoryPages.deleteHead();
			OS.outMemoryPages.deletePage(outterPage);
			OS.inMemoryPages.addPage(outterPage);
			OS.outMemoryPages.addHead(tem);
			return true;

		}

	}

	public static boolean LFU(int pageID) {
		// Least Recently Used
		// 维持一个队列，当进程放入处理机时，就把这个进程所对应的内存页面从插入队头（active）
		// 这样，处于队尾的就是最近最久没有被使用过的内存页面

		// 1.检查该页面是否已在内存中,如果在内存中，需要调整内存中页面的队列顺序
		Page innerPage = OS.inMemoryPages.getPage(pageID);
		if (innerPage != null) {
			OS.inMemoryPages.deletePage(innerPage);
			OS.inMemoryPages.addPage(innerPage);
			innerPage.times++;
			return true;
		}

		// 2.如果不在内存中，需要从外存调入，先检查是否在外存中
		Page outterPage = OS.outMemoryPages.getPage(pageID);
		if (outterPage == null) {
			return false;
		}

		// 3.当前内存中还有位置可以放下新的页面，也就是说内存没满,直接放到队尾
		if (OS.inMemoryPages.isFull() == false) {
			OS.outMemoryPages.deletePage(outterPage);
			OS.inMemoryPages.addPage(outterPage);
			innerPage.times++;
			return true;
		}

		// 4.当前内存已满,替换掉最久没有使用的那个页面（头部）
		else {
			Page tem = OS.inMemoryPages.getMinUsed();
			OS.inMemoryPages.deletePage(tem);
			OS.outMemoryPages.deletePage(outterPage);
			OS.inMemoryPages.addPage(outterPage);
			OS.outMemoryPages.addHead(tem);
			return true;

		}

	}

}
