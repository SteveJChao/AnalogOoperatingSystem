package DiskManagement;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;
import java.util.Vector;

import FileSystem.Dentry;

public class iNode implements Serializable{
	/***
	 * 文件的基本信息
	 */
	public String name;//文件,目录名字
	public String location = "Disk";//文件,目录所在的物理设备名（默认为磁盘）
	public int physicStruct = 3;//文件,目录物理结构，本系统使用的是索引文件格式
	public int logitsStruct = 01;//文件的逻辑结构
	//第一位标志文件是流式文件－0，还是记录型文件－1（默认为流式）
	//第二位标志文件是定长记录－0，还是变长记录－1（默认为变长记录）
	public int size;//文件,目录大小
	public boolean isDir;//是否是目录

	/***
	 * 用户权限信息
	 */
	public String owner;//文件所有者
	public String group;//文件所有组	
	public String authority = "744";//权限，分别是ugo，777代表最高权限，默认是744


	/***
	 * 文件使用信息
	 */
	public String lastModifyData;//文件上一次修改时间
	public int read_locked = 0;//文件是否被读
	public boolean write_locked = false;//文件是否被写
	public boolean modifyTag = false;//文件是否被修改
	
	
	public String FileStream = null;	//	读入内存时的文件字符流
	

	public Vector<Integer> diskTable = new Vector<Integer>();//占据的磁盘区域索引
	
	public Dentry dentry ;//创建索引节点


	 public iNode(String name,String owner,String group,int size,boolean isDir) {
		this.name = name;
		this.owner = owner;	
		this.group = group;
		this.size = size;
		this.isDir = isDir;
		dentry = new Dentry(name,isDir,this);
		
		
		/***
		 * 初始化创建时间
		 */
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		this.lastModifyData = df.format(new Date());// new Date()为获取当前系统时间
	}

	 
	
	 
	 
	 
	 
	 
	 


}
