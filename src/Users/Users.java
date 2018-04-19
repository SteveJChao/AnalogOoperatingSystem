package Users;

import java.io.PrintStream;
import java.io.Serializable;

import A_General.OS;
import FileSystem.Dentry;

public class Users implements Serializable{

	public String userName;
	public String groupName;
	public String password = "123456";
	public Dentry current = OS.ROOT;
	
	
	public Users(String userName,String groupName) {
		this.userName = userName;
		this.groupName = groupName;
		 current.dirContains.get("home").creatFile(userName,userName,groupName, true);
		 current = current.cd("./home/"+userName);
		 OS.userList.put(userName, this);
	}
	

	
	
}
