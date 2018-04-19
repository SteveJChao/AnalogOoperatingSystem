package ProcessCommunication;

import java.util.HashMap;
import java.util.Vector;

import A_General.OS;
import MemoryScheduling.ConstantMemoryRelease;
import MemoryScheduling.MemorySchedul;
import Process.PCB;
import Process.PCB_LinkList;

/***
 * 
 * 						进程之间的通信
 * 
 *   假定进程之间的通信有以下几种情况：
 *   
 *   1.杀死另一个进程：kill＋目标进程号＋发送进程号＋原因
 *   
 *   2.阻塞另一个进程：pause＋目标进程号＋发送进程号＋阻塞原因
 *   
 *   3.激活一个进程：activate＋目标进程号＋发送进程号＋被阻塞原因
 *   
 *   4.给一个进程传送大量字节：distribute＋目标进程号＋发送进程号＋消息
 *
 */
public class MessagePassingSystem {
	
	//从一串格式化的消息中分割出相应信息
	public static HashMap<String, String> divideMessage(String message) {
		
		String[] info = message.split("@#");
		HashMap<String, String> returnMessage = new HashMap<String, String>();
		returnMessage.put("cmd", info[0]);
		returnMessage.put("sender", info[1]);
		returnMessage.put("receiver", info[2]);
		returnMessage.put("message", info[3]);
		
		return returnMessage;
	
	}
	
	//将消息格式化
	public static String rebuildMessage(String cmd,int sender,int receiver,String message) {	
		String returnMessage = cmd+"@#"+sender+"@#"+receiver+"@#"+message;
		return returnMessage;
	
	}
	
	
	//发送者给目标进程发送消息
	public static String send(String cmd,PCB sender,String receiverName,String message) {

		if(OS.PCBNametable.get(receiverName) == null)
		{
		 return "<Error> target process dosen't exist !";
		}
		int receiverID = OS.PCBNametable.get(receiverName);
		String sendMessage = rebuildMessage(cmd, sender.PID, receiverID, message);
		
		PCB targetPCB = OS.PCBtable.get(receiverID);
		
		//加锁
		if(targetPCB.Message != null)
			return "<Error> target process is busy now !";
		
		targetPCB.Message = sendMessage;
		
		
		
		//目标进程直接对该消息做出响应
		return "successfully send message\n\n"+receive(targetPCB);
		
		
	}

	
	//接收者处理这个消息
	public static String receive(PCB pcb) {
		PCB_LinkList targetList = null;
		PCB targetPCB = null;
		if(pcb.Message == null)
		{//如果该进程没有收到任何消息
			 return "<Error> message send , but no response !";
		}
		
		
		//分割收到的消息
		HashMap<String, String> receiveMessage = divideMessage(pcb.Message);
		int sender = Integer.parseInt(receiveMessage.get("sender"));
		int receiver = Integer.parseInt(receiveMessage.get("receiver"));
		String cmd = receiveMessage.get("cmd");
		String info = receiveMessage.get("message");
		
		//将消息具体内容（原因）添加到该进程的内存中
		pcb.info = info;
		
		
		//寻找目标PCB在哪个队列中
		
		targetPCB = OS.running_list.getPCB(receiver);
		if(targetPCB == null) {//目标进程不在运行队列中
			targetPCB = OS.ready_list.getPCB(receiver);
			if(targetPCB == null) {//目标进程不在就绪队列中
				targetPCB = OS.block_list.getPCB(receiver);
				if(targetPCB == null) {//目标进程不在阻塞队列中
					 return "<Error> target process dosen't exist !";
				}else {
					targetList = OS.block_list;
				}
			}else {
				targetList = OS.ready_list;
			}
		}else {
			targetList = OS.running_list;
		}
		
		
		
		return handleMessage(targetList, pcb, cmd, info);
		
		
		
	}
	
	
	
	public static String handleMessage(PCB_LinkList list,PCB target,String cmd,String info) {
		
		if(cmd.equals("kill")) {
			//杀死目标进程指令： 终止目标进程并回收其内存
			target.info = info;
			list.deletePCB(target);
			MemorySchedul.MemoryRelease(target);
			OS.PCBNametable.remove(target.process.Pname);
			OS.PCBtable.remove(target.PID);
			target.Message = null;
			return "successfully  [Kill]  process  "+target.PID+":  "+target.process.Pname;
		}else if (cmd.equals("pause")) {
			//暂停目标进程指令
			target.info = info;
			list.deletePCB(target);
			OS.block_list.addPCB(target);
			target.Pflag = 0;
			target.Message = null;
			return "successfully  [Pause]  process  "+target.PID+":  "+target.process.Pname;
		}else if (cmd.equals("activate")) {
			//激活目标进程指令
			target.info = info;
			target.Pflag = 1;
			target.Message = null;
			return "successfully  [Activate]  process  "+target.PID+":  "+target.process.Pname;
		}else if (cmd.equals("distribute")) {
			//分发消息给目标进程指令
			target.info = info;
			target.Message = null;
			return "successfully  [Distribute Message]  process  "+target.PID+":  "+target.process.Pname;
			
		}else {
			//命令不存在
			target.Message = null;
			return "<Error>  command  "+cmd+"  not found !";
		}
		
		
	}
	
	
	

}
