package TestModel;

import A_General.Init;
import A_General.OS;
import FileSystem.FileSystemCommand;
import FileSystem.PackageCommand;
import ProcessScheduling.CPU_Schedul;

public class FIleTest {
	public static void main(String[] args) {
		try {
			Init.loader();
		} catch (Exception e) {
			System.err.println("Init Error!");
			e.printStackTrace();
			System.exit(0);
		}

		System.out.println(OS.superBlock.getFreeDisk());
		System.out.println(OS.superBlock.getTotalFiles());
		
		
//		packageCommand.Command_who(OS.root);
//		packageCommand.Command_pwd(OS.root);
//		PackageCommand.Command_cd(OS.root, "./home");
//		packageCommand.Command_pwd(OS.root);
		PackageCommand.Command_ls(OS.root,true);
//		PackageCommand.Command_rm(OS.root, "car");
//		PackageCommand.Command_ls(OS.root,true);
//		PackageCommand.Command_touch(OS.root, "car.txt");
//		PackageCommand.Command_write(OS.root, "car.txt@#666666666666666");
//		PackageCommand.Command_ls(OS.root,false);
//		PackageCommand.Command_read(OS.root, "car.txt");
//		PackageCommand.Command_cd(OS.root, ".");
////		packageCommand.Command_pwd(OS.root);
//		PackageCommand.Command_ls(OS.root,true);
		int i=100;
		while (i>0) {
			System.out.println(i);
			CPU_Schedul.CPURun();
			i--;
		}
		
		try {
			Init.saver();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
