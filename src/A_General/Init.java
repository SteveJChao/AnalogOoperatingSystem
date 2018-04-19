package A_General;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import DiskManagement.SuperBlock;
import DiskManagement.iNode;
import FileSystem.Dentry;
import Users.Users;

/***
 * 
 * 整个系统的初始化
 *
 */
public class Init {

	public static void initDentry() {
		iNode node_root = new iNode(".", "root", ".", 0, true);
		OS.ROOT = node_root.dentry;
		OS.ROOT.path = ".";
		OS.ROOT.creatFile("tmp", "root", ".", true);
		OS.ROOT.creatFile("home", "root", ".", true);
		OS.ROOT.creatFile("usr", "root", ".", true);
		OS.ROOT.creatFile("dev", "root", ".", true);
		OS.ROOT.creatFile("bin", "root", ".", true);
		OS.ROOT.creatFile("etc", "root", ".", true);
	}

	public static void initUser() {

		OS.userList = new HashMap<String, Users>();
		OS.root = new Users("root", ".");
		new Users("root", ".");
		new Users("apple", "fruits");
		new Users("banana", "fruits");
		new Users("strawberry", "fruits");
		new Users("peer", "fruits");
		new Users("car", "machine");
		new Users("motobike", "machine");
		new Users("airplane", "machine");

	}

	public static void initDisk() {
		PrintStream ps;
		try {
			ps = new PrintStream(new FileOutputStream("MyDisk", false));

			for (int i = 0; i < 400; i++) {
				ps.println("$");
			}
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 保存磁盘信息
	public static void saveDisk() {
		PrintStream ps;
		try {
			ps = new PrintStream(new FileOutputStream("MyDisk", false));

			for (int i = 0; i < 400; i++) {
				ps.println(OS.MyDisk[i]);
			}
			ps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 加载磁盘信息
	public static void loadDisk() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("MyDisk"));
			int lineNum = 0;
			String line = br.readLine();
			while (line != null) {
				OS.MyDisk[lineNum] = line;
				line = br.readLine();

				lineNum++;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void saver() throws Exception {

		File system = new File("System");

		if (!system.exists())
			system.createNewFile();

		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(system));
		oos.writeObject(OS.userList);
		oos.writeObject(OS.ROOT);
		oos.writeObject(OS.superBlock);
		oos.close();
		saveDisk();

	}

	public static void loader() throws Exception {

		File system = new File("System");

		loadDisk();

		if (system.exists()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(system));

			OS.userList = (HashMap<String, Users>) ois.readObject();
			OS.ROOT = (Dentry) ois.readObject();
			OS.superBlock = (SuperBlock) ois.readObject();
			OS.root = OS.userList.get("root");

		} else {
			OS.superBlock = new SuperBlock();
			initDentry();
			initUser();

		}

	}

}
