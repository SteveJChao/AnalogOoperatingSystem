package ConstantMemory;

import A_General.OS;

/***
 * 
 * 定义一个类，用来表示连续内存中的内存碎片 包括已经分配的和空闲分区
 *
 */

public class Pieces_LinkList {

	Pieces head = null;
	Pieces rear = null;
	Pieces point = null;// 用来指示当前的位置
	int num = 0;

	public Pieces_LinkList() {

	}

	public Pieces_LinkList(int start, int size) {
		Pieces onePiece = new Pieces(start, size);
		addPieces(onePiece);
	}

	public void addPieces(Pieces p) {
		if (num == 0 || head == null) {
			head = p;
			rear = p;
			num++;
			return;
		}
		rear.next = p;
		p.prior = rear;
		rear = p;
		num++;
		return;
	}

	// 在某个块后面添加
	public void addBehind(Pieces oldPiece, Pieces newPiece) {
		if (oldPiece == rear) {
			rear = newPiece;
			oldPiece.next = newPiece;
			newPiece.prior = oldPiece;
			num++;
			return;
		}
		newPiece.next = oldPiece.next;
		oldPiece.next.prior = newPiece;
		oldPiece.next = newPiece;
		newPiece.prior = oldPiece;
		num++;

	}

	// // 寻找队列中指定元素的前一个
	// public Pieces prePieces(Pieces page) {
	// if (page == null || num == 0 || num == 1) {
	// return null;
	// }
	//
	// Pieces p = head;
	// while (p != null) {
	// if (p.next == page)
	// return p;
	// p = p.next;
	// }
	//
	// return null;
	//
	// }

	// 找到指定开始地址的内存碎片
	public Pieces getPieces(int start) {
		if (num == 0 || head == null) {
			return null;
		}

		Pieces p = head;
		while (p != null) {
			if (p.start == start) {
				return p;
			}
			p = p.next;
		}

		return null;

	}

	// 删除指定的模块
	public boolean deletePieces(Pieces pieces) {
		if (num == 0 || head == null) {
			return false;

		}

		if (num == 1 || head == rear) {
			head = null;
			rear = null;
			pieces.next = null;
			pieces.prior = null;
			num--;
			return true;
		}

		if (pieces == head) {
			head.next.prior = null;
			head = head.next;
			pieces.next = null;
			num--;
			return true;
		}

		if (pieces == rear) {
			rear = pieces.prior;
			pieces.prior.next = null;
			pieces.prior = null;
			pieces.next = null;

			num--;
			return true;
		}

		pieces.prior.next = pieces.next;
		pieces.next.prior = pieces.prior;
		pieces.prior = null;
		pieces.next = null;
		num--;
		return true;

	}

	// 得到内存碎片区最大的块
	public Pieces getMaxBlock(int min) {

		if (num == 0 || head == null) {
			return null;
		}

		Pieces target = null;
		int value = -1;

		Pieces p = head;
		while (p != null) {
			if ((p.size == min || p.size >= min + 50) && p.size > value) {
				target = p;
				value = p.size;
			}
			p = p.next;
		}
		return target;

	}

	// 得到内存碎片区最小的块，但这个块必须大于所需要分配的内存大小
	public Pieces getMinBlock(int min) {

		if (num == 0 || head == null) {
			return null;
		}

		Pieces target = null;
		int value = OS.MaxMemory + 100;

		Pieces p = head;
		while (p != null) {
			if ((p.size == min || p.size >= min + 50) && p.size < value) {
				target = p;
				value = p.size;
			}
			p = p.next;
		}
		return target;

	}

	// 从point位置开始，得到第一个符合要求的内存区域
	public Pieces getFirstBlock(Pieces point, int min) {
		if (num == 0 || head == null) {
			return null;
		}
		if (point == null) {
			point = head;// 如果没有指定中继指针的话，就从头开始搜索
		}
		Pieces target = point;

		while (target != null) {
			if (target.size == min || target.size >= min + 50) {
				return target;
			}
			target = target.next;
		}
		return null;

	}

	// 截断某个块，返回截取的部分，剩余部分放回队列
	public Pieces cutPieces(Pieces p, int min) {

		// 把块的需求部分截取下来
		Pieces newPiece = new Pieces(p.start, min);
		// 剩余的块
		p.start = p.start + min;
		p.size = p.size - min;
		if (p.start >= p.end || p.size <= 0) {
			// 如果剩余的块没了，删掉
			deletePieces(p);
		}

		return newPiece;

	}

	// 如果两个块连接在一起，把他们合并
	public boolean mergePieces_onetimes() {
		boolean flag = false;
		if (num == 0 || head == null) {
			return false;
		}

		Pieces pieces = head;
		while (pieces != null && pieces.next != null) {
			if (pieces.end + 1 >= pieces.next.start) {
				pieces.end = pieces.next.end;
				pieces.size = pieces.size + pieces.next.size;
				deletePieces(pieces.next);
				flag = true;

			}

			pieces = pieces.next;

		}
		return flag;

	}
	
	
	public void mergePieces() {
		while (mergePieces_onetimes()) {
				
		}

		}
		

	// 当一个块用完之后，需要按顺序插回队列
	public boolean releaseAndAddBack(Pieces pieces) {

		Pieces p = head;

		// 如果整个队列都是空的
		if (num == 0 || head == null) {
			head = pieces;
			rear = pieces;
			num++;
			return true;

		}

		// 如果这个块应该放在末尾，直接插入末尾
		if (pieces.start > rear.end) {
			addBehind(rear, pieces);
			mergePieces();
			return true;
		}
		
		// 如果这个块应该放在头部
		if (pieces.start < head.start) {
			pieces.next = head;
			head.prior = pieces;
			head = pieces;
			num++;
			mergePieces();
			return true;
		}
		while (p != null) {
			if (p.end < pieces.start && pieces.end < p.next.start)// 找到合适的位置插入该块
			{
				addBehind(p, pieces);
				mergePieces();
				return true;
			}

			p = p.next;
		}
		return false;

	}

	public void printALL() {
		Pieces p = head;
		int i = 0;
		System.out.println();
		System.out.println();
		while (p != null) {
			System.out.println("第" + i + "个内存碎片:  起始－" + p.start + "    终止－" + p.end + "    总大小－" + p.size);
			p = p.next;
			i++;
		}

	}

}
