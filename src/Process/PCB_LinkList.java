package Process;

import java.awt.print.Pageable;
import java.io.PrintStream;
import java.util.Vector;

import A_General.OS;
import ConstantMemory.Memory;
import MemoryScheduling.ConstantMemoryAllocate;
import ProcessCommunication.MessagePassingSystem;
import ProcessScheduling.BlockActivate;

/***
 * 用一个链表（进程队列），连接各种进程
 */

public class PCB_LinkList {

	PCB head;
	PCB rear;
	int num;
	int size;

	public PCB getHead() {
		if (head == null || num == 0)
			return null;
		return head;
	}

	// 不限制队列长度
	public PCB_LinkList() {
		head = null;
		rear = null;
		num = 0;
		size = 9999999;
	}

	// 限制队列长度
	public PCB_LinkList(int size) {
		head = null;
		rear = null;
		num = 0;
		this.size = size;
	}

	/***
	 * 状态判断函数
	 */
	public boolean isFull() {
		if (num >= size)
			return true;
		else {
			return false;
		}
	}

	public boolean isEmpty() {
		if (num <= 0) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 * 查询函数
	 */
	// 获得队列长度
	public int getNum() {
		return num;
	}

	// 寻找active状态的进程，找不到返回NULL
	public PCB getActiveTask() {
		if (head == null)
			return null;
		PCB p = head;
		while (p != null) {
			if (p.Pflag != 0) {
				return p;
			}
			p = p.next;
		}
		return null;
	}

	// 得到处理器中运行完毕的进程
	public PCB getFinsihTask() {
		if (head == null)
			return null;
		PCB p = head;
		while (p != null) {
			if (p.Ptime_needed <= p.process.Ptime_used) {
				return p;
			}
			p = p.next;
		}

		return null;

	}

	// 找到指定PID的PCB，若队列为空，返回null
	public PCB getPCB(int id) {
		if (head == null)
			return null;
		PCB p = head;
		while (p != null) {
			if (p.PID == id)
				return p;
			p = p.next;

		}
		return null;

	}

	// 找到PCB前一项，若队列为空或返回null
	public PCB prePCB(PCB pcb) {
		if (head == null || pcb == head)
			return null;
		PCB p = head;
		while (p != null) {
			if (p.next == pcb)
				return p;
			p = p.next;
		}

		return null;

	}

	// 遍历整个队列，找到预计用时最短的进程
	public PCB getMinTimeUsed() {
		PCB target = null;
		int value = 9999;
		PCB p = head;
		while (p != null) {
			if (p.Ptime_needed < value) {
				value = p.Ptime_needed;
				target = p;
			}
			p = p.next;

		}

		return target;

	}

	// 遍历整个队列，找到优先级最高的进程
	public PCB getMaxPrio() {

		PCB target = null;
		int value = -1;
		PCB p = head;
		while (p != null) {
			if (p.Pprio > value) {// 找到优先级最高的进程
				target = p;
				value = p.Pprio;
			}
			p = p.next;

		}

		return target;

	}

	// 遍历整个队列，找到优先级最低的进程
	public PCB getMinPrio() {

		PCB target = null;
		int value = 99999;
		PCB p = head;
		while (p != null) {
			// 找到优先级最低的进程，且此进程不是内核进程
			if (p.PID == 0) {
				p = p.next;
				continue;
			}

			if (p.Pprio < value) {
				target = p;
				value = p.Pprio;
			}
			p = p.next;

		}

		return target;

	}

	// 遍历整个队列，找到时间片为0的进程
	public PCB getTimePieceRunOutOf(int piece) {

		PCB p = head;
		while (p != null) {
			if (p.PID == 0) {
				p = p.next;
				continue;
			}
			if (p.process.NO_SLEEP == false && p.process.Ptime_used >= piece) {// 此进程可以被休眠
				return p;
			}
			p = p.next;

		}

		return p;

	}

	// 遍历整个队列，找到响应比最高的: R =(W+T)/T = 1+W/T
	public PCB getHighestResponseRatio() {

		PCB target = null;
		double value = -1;
		PCB p = head;
		while (p != null) {
			double tem = 1 + (p.process.Ptime_waited + 1) / (p.process.Ptime_used + 1);
			if (tem > value) {// 找到响应比最高的进程
				value = tem;
				target = p;
			}
			p = p.next;

		}

		return target;

	}

	// 遍历整个队列，找到响应比最低的: R =(W+T)/T = 1+W/T
	public PCB getLowestResponseRatio() {

		PCB target = null;
		double value = 9999999;
		PCB p = head;
		while (p != null) {
			if (p.PID == 0) {
				p = p.next;
				continue;
			}
			double tem = 1 + (p.process.Ptime_waited + 1) / (p.process.Ptime_used + 1);
			if (p.PID != 0 && tem < value) {// 找到响应比最低的进程，且跳过内核进程
				value = tem;
				target = p;
			}
			p = p.next;

		}

		return target;

	}

	/***
	 * 功能函数
	 **/

	// 添加PCB
	public void addPCB(PCB pcb) {

		if (head == null) {
			head = pcb;
			rear = pcb;
			num++;
		}

		else {
			rear.next = pcb;
			rear = pcb;
			num++;
		}

	}

	// 删除PCB，队列为空或未找到则返回false
	public boolean deletePCB(PCB pcb) {

		if (num == 0 || head == null) {
			return false;

		}

		if (pcb == head && pcb == rear) {
			head = null;
			rear = null;
			num = 0;
			pcb.next = null;
			return true;
		}

		else if (pcb == head) {
			head = head.next;
			num--;
			pcb.next = null;
			return true;
		} else {
			PCB pre = prePCB(pcb);
			if (pre == null)
				return false;
			if (pre.next == rear) {// 如果删除的是尾巴
				rear = pre;
			}
			pre.next = pcb.next;
			num--;
			pcb.next = null;
			return true;
		}

	}

	// 头删
	public PCB deleteHead() {
		if (head == null || num == 0) {
			return null;
		}

		else if (num == 1) {
			PCB tem = head;
			tem.next = null;
			head = null;
			rear = null;
			num--;
			return tem;
		} else {
			PCB tem = head;
			head = head.next;
			tem.next = null;
			num--;
			return tem;
		}

	}

	// 头插
	public void addHead(PCB pcb) {

		if (num == 0) {
			head = pcb;
			rear = pcb;
			num++;
		} else {
			pcb.next = head;
			head = pcb;
			num++;
		}

	}

	// 队列内所有进程等待时间片＋1
	public void TimeWaitedPlus() {
		PCB p = head;
		while (p != null) {
			p.process.Ptime_waited++;
			p = p.next;
		}
	}

	// 队列内所有进程运行时间片＋1
	public void TimeUsedPlus() {
		PCB p = head;
		while (p != null) {
			p.process.run();
			p.process.Ptime_used++;
			p = p.next;
		}
	}
	
	//检查是否有进程被阻塞
	public void checkblockProcess() {
		PCB p = head;
		while (p != null) {
			if(p.Pflag == 0)
				BlockActivate.block(p);
			p = p.next;
		}
	}
	
	

	// 处理进程通信的信息
	public void sendMessage() {
		PCB p = head;
		while (p != null) {
			if (p.send != null) {
				String[] message = p.send.split("@#");
				MessagePassingSystem.send(message[0], p, message[1], message[2]);
				p.send = null;
			}
			p = p.next;
		}
	}

	
	//从阻塞队列中查找是否有可以被激活的进程
	public void block2ready() {
		PCB p = head;
		while (p != null) {
			if(p.blockType == 1 && p.process.owner.current.dirContains.containsKey(p.info) &&  p.process.owner.current.dirContains.get(p.info).inode.write_locked == false && p.process.owner.current.dirContains.get(p.info).inode.read_locked <=0)
				BlockActivate.activate(p);
			p = p.next;
		}
	}
	
	//统计所有进程占用的内存
	public int stasticMemory() {
		int totalMemory = 0;
		PCB p = head;
		while (p != null) {
			totalMemory += p.Pmemory;
			p = p.next;
		}
		return totalMemory;
	}
	
	
	
	public void printAll() {
		PCB p = head;
		System.out.println("   ");
		if (num != 0)
			System.out.println(num + "    " + head.PID + "  " + rear.PID);
		System.out.println(" ID" + "                " + "    进程名称  " + "     " + "进程类型" + "      " + "进程状态" + "   "
				+ "进程优先级" + "   " + "进程所需时间" + "     " + "进程已用时间" + "     " + "进程等待时间");
		System.out.println(
				"===================================================================================================");
		while (p != null) {
			System.out.println(p.PID + "              " + p.process.Pname + "           " + p.Ptype
					+ "                    " + p.Pflag + "                  " + p.Pprio + "                      "
					+ p.Ptime_needed + "                         " + p.process.Ptime_used
					+ "                             " + p.process.Ptime_waited);
			p = p.next;
		}
	}

}
