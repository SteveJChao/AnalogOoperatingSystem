package OS_Kernel;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.net.Socket;
import A_General.*;
import FileSystem.PackageCommand;
import ProcessScheduling.CreatProcess;
import Users.Users;
import sun.font.CreatedFontTracker;


public class OnlineUsers implements Runnable{

	public Users who = null;
	public Socket socket = null;
	public OS_Connections server = null;
	// BufferedReader bf = null;
	public PrintStream ps = null;
	public ObjectInputStream ois = null;
	// ObjectOutputStream oos = null;
	public boolean flag = true;

	public OnlineUsers(Socket socket, OS_Connections server) throws Exception {
		this.server = server;
		this.socket = socket;
		// bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		ps = new PrintStream(this.socket.getOutputStream());
		// oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(this.socket.getInputStream());

		
		
		new Thread(this).start();
	}
	@Override
	public void run() {
		while(flag) {
			
			try {
				Message message = (Message) ois.readObject();
				String type = message.type;
				if (type.equals(Mtype.LOGIN)) {
					
					Login(message);

				} else if (type.equals(Mtype.REGISTER)) {

					Register(message);
					
				} else if (type.equals(Mtype.COMMAND)){
					
					runProcess(message);

				}
				else{
					ErrorCommand();
				}
			} catch (Exception e) {
				if(this.who!=null)
					Logout();
			}
			
			
			
			
		}
		
		
		
	}

	// 登陆时身份认证
	public void Login(Message message) throws Exception {

		String userName = message.UserName;
		String passwd = message.password;

        CreatProcess.createKernelProcess();

		// 帐号不存在
		if (!OS.userList.containsKey(userName)) {
			ps.println("<Error> Account not found , register first !");

			return;
		}

		// 密码错误
		if (!passwd.equals(OS.userList.get(userName).password)) {
			ps.println("<Error> Wrong Password !");

			return;
		}

		if (OS.onlineUsers.containsKey(userName)) {
			// 登录失败，该账号已在线
			ps.println("<Error> Account is active now !");

			return;
		}

		// 在在线用户队列中加入该用户

//		server.myList.put(userName, this);
		
		//加入当前的用户队列
		OS.onlineUsers.put(userName, ps);

		who = OS.userList.get(userName);

//		server.setTitle("Online Users:" + OS.onlineUsers.size());
		ps.println("~Welcome , "+userName);
		
		
	}

	// 下线时资源回收
	public void Logout() {
		System.out.println("logout");
		OS.onlineUsers.remove(who.userName);
        CreatProcess.createKernelProcess();
//		server.setTitle("Online Users:" + OS.onlineUsers.size());
		try {
			flag = false;
			socket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// 用户注册
	public void Register(Message message) throws Exception {

		String userName = message.UserName;
		String passwd = message.password;
        CreatProcess.createKernelProcess();
		// 用户名是否重复
		if (OS.userList.containsKey(userName)) {

			ps.println("<Error> same account exists !");
			return;
		}

		Users user = new Users(userName, message.group);
		user.password = passwd;
		
		//在用户列表中保留此用户
		OS.userList.put(userName, user);
		
		ps.println("successfully register , please login !");


	}

	// 错误命令
	public void ErrorCommand() throws Exception {
		ps.println("<Error> Command not found !");
		flag = false;
		socket.close();

	}
	
	
	public void runProcess(Message m) throws Exception{
		
		
		String[] message = m.message.split(" ");
		String command = "";		
		String command_type = "";
		for(int i=0;i<message.length;i++)
		{
			if(i==0)
				command_type = message[i].trim();
			else {
				if(i==message.length-1)
					command+=message[i].trim();
				else {
					command+=message[i].trim()+"@#";
				}
				
			}
		}
	if(command_type.equals("who"))
		PackageCommand.Command_who(who);
	else if (command_type.equals("ls")) 
		if(command.equals("-l"))
		PackageCommand.Command_ls(who, false);
		else
			PackageCommand.Command_ls(who, true);
	else if (command_type.equals("chmod")) 
		PackageCommand.Command_chmod(who, command);
	else if (command_type.equals("pwd")) 
		PackageCommand.Command_pwd(who);
	else if (command_type.equals("mkdir")) 
		PackageCommand.Command_mkdir(who, command);
	else if (command_type.equals("read")) 
		PackageCommand.Command_read(who, command);
	else if (command_type.equals("rm")) 
		PackageCommand.Command_rm(who, command);
	else if (command_type.equals("rmdir")) 
		PackageCommand.Command_rmdir(who, command);
	else if (command_type.equals("touch")) 
		PackageCommand.Command_touch(who, command);
	else if (command_type.equals("write")) 
		PackageCommand.Command_write(who, command);
	else if (command_type.equals("cd")) 
		PackageCommand.Command_cd(who, command);
	else if (command_type.equals("mail")) 
		PackageCommand.Command_mail(who, command);
	else if (command_type.equals("time")) 
		PackageCommand.Command_time(who);
	else if (command_type.equals("onlineuser")) 
		PackageCommand.Command_online(who);
	else if (command_type.equals("passwd")) 
		PackageCommand.Command_passwd(who, command);
	else 
		PackageCommand.Command_communicate(who,command_type+"@#"+command);
		
	}

}
