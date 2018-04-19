package FileSystem;import java.util.HashMap;

import A_General.OS;
import Process.PCB;
import ProcessScheduling.CreatProcess;
import Users.Users;
/***
 * 
 *  包装一些用户的命令：

 *  	0.mkdir 【目录名】
 *  	1.read 【文件名】
 *  	2.write 【文件名】【内容】
 *  	3.touch 【文件名】【大小】
 *  	4.rm 【文件名】
 *  	5.ls
 *  	6.chmod 【权限】【文件名】
 *   	7.cd 【目标路径】
 *   	8.进程通信
 *   	9.rmdir 【目录名】
 *   	10.pwd
 *   	11.who
 *   	12.mail 【用户名】【信息】
 *   	13.time
 *   	14.onlineuser
 *   	15.passwd 【新密码】
 *  	
 *
 */
public class PackageCommand {
	//mkdir
	public static void Command_mkdir(Users users,String command) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(300));
		parameters.put("Pname", "mkdir_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 0;
		pcb.process.command = command;
		
		
	}
	
	
	//read
	public static void Command_read(Users users,String command) {
		
		int m = 500;
		if(users.current.dirContains.containsKey(command) == true)
			m += users.current.dirContains.get(command).inode.size;
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(m));
		parameters.put("Pname", "read_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(3));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 1;
		pcb.process.command = command;
		
		
	}
	
	
	//write
	public static void Command_write(Users users,String command) {
		
		int m = 500;
		if(users.current.dirContains.containsKey(command) == true)
			m += users.current.dirContains.get(command).inode.size+command.length();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(m));
		parameters.put("Pname", "write_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(3));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 2;
		pcb.process.command = command;
		
		
	}
	
	
	//touch
	public static void Command_touch(Users users,String command) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(800));
		parameters.put("Pname", "touch_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(2));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 3;
		pcb.process.command = command;
		
		
	}
	
	
	//rm
	public static void Command_rm(Users users,String command) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(18));
		parameters.put("Pmemory", String.valueOf(500));
		parameters.put("Pname", "rm_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 4;
		pcb.process.command = command;
		
		
	}
	
	//ls
	public static void Command_ls(Users users,boolean isSimple) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(15));
		parameters.put("Pmemory", String.valueOf(100));
		parameters.put("Pname", "ls_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 5;
		pcb.process.command = String.valueOf(isSimple);
		
		
	}
	
	//chmod
	public static void Command_chmod(Users users,String command) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(20));
		parameters.put("Pmemory", String.valueOf(200));
		parameters.put("Pname", "chmod_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 6;
		pcb.process.command = command;
		
		
	}
	
	
	//cd
	//输入的string是：  目标directory
	public static void Command_cd(Users users,String command) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(12));
		parameters.put("Pmemory", String.valueOf(80));
		parameters.put("Pname", "cd_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 7;
		pcb.process.command = command;
		
		
	}
	
	//进程通信：cmd@#receiverName@#message
	public static void Command_communicate(Users users,String command) {
		
		PCB pcb = CreatProcess.createCurrentProcess(users,"communicate_"+users.userName+"_"+OS.NextProcessID);

		pcb.process.command_type = 8;
		pcb.process.command = command;
		
		
	}
	
	
	//rmdir
	public static void Command_rmdir(Users users,String command) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(12));
		parameters.put("Pmemory", String.valueOf(200));
		parameters.put("Pname", "rmdir_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 9;
		pcb.process.command = command;
		
		
	}
	
	
	//pwd
	public static void Command_pwd(Users users) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(20));
		parameters.put("Pmemory", String.valueOf(100));
		parameters.put("Pname", "pwd_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 10;
		pcb.process.command = "pwd";

		
		
	}
	
	//who
	public static void Command_who(Users users) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(100));
		parameters.put("Pname", "who_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 11;
		pcb.process.command = "who";
	
	
	
}
	
	
	//mail
	public static void Command_mail(Users users,String command) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(command.length()));
		parameters.put("Pname", "mail_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 12;
		pcb.process.command = command;
	
	
	
}
	
	//time
	public static void Command_time(Users users) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(100));
		parameters.put("Pname", "time_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 13;
		pcb.process.command = "time";
	
	
	
}
	
	//onlineuser
	public static void Command_online(Users users) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(150));
		parameters.put("Pname", "online_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 14;
		pcb.process.command = "onlineuser";
	
	
	
}
	
	//passwd
	public static void Command_passwd(Users users,String command) {
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("PID", String.valueOf(OS.NextProcessID));
		parameters.put("Pprio",String.valueOf(10));
		parameters.put("Pmemory", String.valueOf(50));
		parameters.put("Pname", "passwd_"+users.userName+"_"+OS.NextProcessID);
		parameters.put("Ptime_needed", String.valueOf(1));
		PCB pcb = CreatProcess.createUserProcess(users, parameters, 0);
		pcb.process.command_type = 15;
		pcb.process.command =command;
	
	
	
}
	
	
}
