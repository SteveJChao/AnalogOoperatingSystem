package DiskManagement;

import java.io.Serializable;

import A_General.OS;
import FileSystem.Dentry;

public class SuperBlock implements Serializable{
	public int blockSize = 64;//每个盘块大小
	public int row_num = 20;//位示图大小（长/宽）
	public VectorMap vectorMap;
	
	public SuperBlock() {
		vectorMap = new VectorMap(row_num, blockSize);
	}
	
	//得到磁盘中空闲盘块信息
	public String getFreeDisk() {
		return vectorMap.freeDiskNum+"*"+blockSize+"  Bytes";
	}
	
	// 得到inode结点总数
	public int getTotalFiles()
	{
		return Dentry.getTotalDirNum(OS.ROOT)+Dentry.getTotalFileNum(OS.ROOT);
	}
	
	
	
	
}
