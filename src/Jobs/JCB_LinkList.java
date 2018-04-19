package Jobs;

import java.awt.print.Pageable;
import java.util.Vector;

import ConstantMemory.Memory;
import MemoryScheduling.ConstantMemoryAllocate;

/***
 * 用一个链表（作业队列），连接各种作业
 */

public class JCB_LinkList {

	public JCB head;
	public JCB rear;
	public int num;

	public JCB_LinkList() {
		head = null;
		rear = null;
		num = 0;
	}

	/***
	 * 状态判断函数
	 */

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



	// 找到JCB前一项，若队列为空或返回null
	public JCB preJCB(JCB jcb) {
		if (head == null || jcb == head)
			return null;
		JCB p = head;
		while (p != null) {
			if (p.next == jcb)
				return p;
			p = p.next;
		}

		return null;

	}

	// 遍历整个队列，找到预计用时最短的进程
	public JCB getMinTimeUsed() {
		JCB target = null;
		int value = 9999;
		JCB p = head;
		while (p != null) {
			if (p.pcb.Ptime_needed < value) {
				value = p.pcb.Ptime_needed;
				target = p;
			}
			p = p.next;

		}

		return target;

	}

	// 遍历整个队列，找到优先级最高的进程
	public JCB getMaxPrio() {

		JCB target = null;
		int value = -1;
		JCB p = head;
		while (p != null) {
			if (p.pcb.Pprio > value) {// 找到优先级最高的进程
				target = p;
				value = p.pcb.Pprio;
			}
			p = p.next;

		}

		return target;

	}


	// 遍历整个队列，找到响应比最高的: R =(W+T)/T = 1+W/T
	public JCB getHighestResponseRatio() {

		JCB target = null;
		double value = -1;
		JCB p = head;
		while (p != null) {
			double tem = 1 + (p.pcb.process.Ptime_waited + 1) / (p.pcb.Ptime_needed + 1);
			if (tem > value) {// 找到响应比最高的进程
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

	// 添加JCB
	public void addJCB(JCB jcb) {

		if (head == null) {
			head = jcb;
			rear = jcb;
			num++;
		}

		else {
			rear.next = jcb;
			rear = jcb;
			num++;
		}

	}

	// 删除JCB，队列为空或未找到则返回false
	public boolean deleteJCB(JCB jcb) {

		if (num == 0 || head == null) {
			return false;

		}

		if (jcb == head && jcb == rear) {
			head = null;
			rear = null;
			num = 0;
			jcb.next = null;
			return true;
		}

		else if (jcb == head) {
			head = head.next;
			num--;
			jcb.next = null;
			return true;
		} else {
			JCB pre = preJCB(jcb);
			if (pre == null)
				return false;
			if (pre.next == rear) {// 如果删除的是尾巴
				rear = pre;
			}
			pre.next = jcb.next;
			num--;
			jcb.next = null;
			return true;
		}

	}

	// 头删
	public JCB deleteHead() {
		if (head == null || num == 0) {
			return null;
		}

		else if (num == 1) {
			JCB tem = head;
			tem.next = null;
			head = null;
			rear = null;
			num--;
			return tem;
		} else {
			JCB tem = head;
			head = head.next;
			tem.next = null;
			num--;
			return tem;
		}

	}

	// 头插
	public void addHead(JCB jcb) {

		if (num == 0) {
			head = jcb;
			rear = jcb;
			num++;
		} else {
			jcb.next = head;
			head = jcb;
			num++;
		}

	}

	// 队列内所有进程等待时间片＋1
	public void TimeWaitedPlus() {
		JCB p = head;
		while (p != null) {
			p.pcb.process.Ptime_waited++;
			p = p.next;
		}
	}



}
