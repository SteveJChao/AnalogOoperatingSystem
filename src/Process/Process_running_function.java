package Process;

import A_General.OS;
import FileSystem.Dentry;
import ProcessCommunication.MessagePassingSystem;

public class Process_running_function {

	//进程通信操作
	public static String sendMessage(PCB sender,String command) {
		
		
		String cmd = command.split("@#")[0];
		String receiverName = command.split("@#")[1];
		String message = command.split("@#")[2];
		
		if(cmd.equals("kill")||cmd.equals("pause")||cmd.equals("activate")||cmd.equals("distribute"))
		{
			return MessagePassingSystem.send(cmd, sender, receiverName, message);
		}
		
		else return "<Error> command  "+cmd+"  not  found !";
		
	}
	
	
	
	
	
	
	
	
}
