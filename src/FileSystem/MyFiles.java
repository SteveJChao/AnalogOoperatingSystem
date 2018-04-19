package FileSystem;

import java.io.File;

/**
 * 文件管理类
 */
public class MyFiles {
    //盘块名称
    private int blockName;

    private File myFile;

    private String fileName;
    //文件大小
    double space;

    /**
     * 新建文件时调用的初始化构造方法
     * @param myFile
     * @param blockName
     * @param capacity
     */
    public MyFiles(File myFile, int blockName, double capacity){
        space = capacity;
        this.myFile = myFile;
        this.blockName = blockName;
        //获取传入文件的文件名
        fileName = myFile.getName();
    }

    public String getFileName(){
        return myFile.getName();
    }

    /**
     * 调用库方法返回文件路径
     * @return
     */
    public String getFilePath(){
        return myFile.toString();
    }

    /**
     * 重命名文件方法，
     * @param name
     * @return
     */
    public boolean renameFile(String name){
        //所在文件夹的地址
        String parent = myFile.getParent();

        //
        File newFile = new File(parent + File.separator + name);
        //
        if (myFile.renameTo(newFile)){
            //修改指针
            myFile = newFile;
            fileName = name;
            return true;
        }else{
            return false;
        }
    }

    public File getMyFile(){
        return myFile;
    }

    public int getBlockName() {
        return blockName;
    }

    public double getSpace() {
        return space;
    }

    @Override
    public String toString(){
        return fileName;
    }
}
