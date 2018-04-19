package DiskManagement;

import java.io.File;
import java.io.Serializable;

/***
 * 
 * 利用位示图描述磁盘使用情况
 * 
 * 并处理：请求分配磁盘块和回收磁盘块
 *
 */
public class VectorMap implements Serializable {
	// 假设磁盘总共有400个盘块，每个盘块空间为4096Bytes
	public int size;
	public int memoryPreDisk;
	public int freeDiskNum;
	public boolean[][] map;

	public VectorMap(int size, int memoryPreDisk) {

		this.size = size;
		this.memoryPreDisk = memoryPreDisk;
		freeDiskNum = size * size;
		map = new boolean[size][size];

		init();
	}

	// 磁盘一些初始化操作
	public void init() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {
				map[i][j] = false;
			}

		for (int i = 0; i < 1; i++)
			for (int j = 0; j < 20; j++) {// 随机为系统资源分配一些盘区
				map[i][j] = true;
				freeDiskNum--;
			}
	}

	// 为文件分配磁盘空间
	public boolean allocateDisk(iNode file) {

		if (file.size == 0)
			return true;
		

		int totalDisk = file.size / memoryPreDisk + 1;// 总共需要几个盘区
		
		// 磁盘空间不足
		if (totalDisk > freeDiskNum)
			return false;

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {// 遍历整个位示图
				if (map[i][j] == false) {// 找到空闲的盘区
					int disk_id = i * size + j;// 转换成盘块的id
					file.diskTable.add(disk_id);// 在文件的索引表中加入这个盘块id
					map[i][j] = true;
					freeDiskNum--;
					totalDisk--;
				}
				if (totalDisk <= 0)
					return true;
			}

		// 如果分配失败，返还已分配的磁盘块
		releaseAllDisk(file);
		return false;

	}

	// 回收文件对应的磁盘空间
	public void releaseAllDisk(iNode file) {
		if (file.size == 0)
			return;
		for (int disk_id : file.diskTable) {
			int i = disk_id / size;
			int j = disk_id % size;
			map[i][j] = false;
			freeDiskNum++;
		}
		file.diskTable.clear();

	}

	// 为文件追加磁盘块
	public boolean addDisk(iNode file, int memorySize) {

		int totalDisk = memorySize / memoryPreDisk + 1;
		if (totalDisk > freeDiskNum)
			return false;

		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++) {// 遍历整个位示图
				if (map[i][j] = false) {// 找到空闲的盘区
					int disk_id = i * size + j;// 转换成盘块的id
					file.diskTable.add(disk_id);// 在文件的索引表中加入这个盘块id
					map[i][j] = true;
					freeDiskNum--;
					totalDisk--;
				}
				if (totalDisk <= 0) {

					file.size += memorySize;
					return true;
				}

			}

		// 如果分配失败，返还已分配的磁盘块
		releaseAllDisk(file);
		return false;

	}

	// 回收文件部分磁盘空间
	public void freeDisk(iNode file, int memorySize) {
		// 总共需要释放的磁盘块数目（向下取整）
		int totalDisk = memorySize / memoryPreDisk;
		for (int tag = 0; tag < totalDisk; tag++) {
			int disk_id = file.diskTable.get(file.diskTable.size() - 1);
			int i = disk_id / size;
			int j = disk_id % size;
			map[i][j] = false;
			freeDiskNum++;
			file.diskTable.remove(file.diskTable.size() - 1);
		}

	}

	public void printAll() {

		System.out.println(
				"===================================================================================================");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(map[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println(
				"===================================================================================================");
	}

}
