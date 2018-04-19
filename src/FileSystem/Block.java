package FileSystem;

import javax.swing.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 盘块类
 */
public class Block {

    //盘块标识，用了数字表示
    private int blockName;
    //盘块文件
    private File blockFile;


    //显示盘详细情况的文件，0-1
    private File blockBitMap;
    //备份文档
    private File recover;

    //
    private FileWriter bitWriter;
    private FileWriter recoverWriter;

    //块中的文件数
    private int fileNum;

    //块大小
    private double space;

    //位图
    public int [][] bitmap = new int[32][32];

    //位图文件集合
    private Map<String, int[][] > filesBit = new HashMap<String, int[][]>();


    private ArrayList<File> files = new ArrayList<File>();

    /**
     * 构造器
     * @param name
     * @param file
     * @param rec
     * @throws IOException
     */
    public Block(int name, File file, boolean rec) throws IOException {
        blockName = name;
        //标识该盘块的文件
        blockFile = file;

        //
        blockBitMap = new File(blockFile.getPath() + File.separator + blockName + "BitMap&&Fat.txt");
        recover = new File(blockFile.getPath() + File.separator + "recover.txt");

        if (!rec) {
            space = 0;
            fileNum = 0;

            //表示盘块的文件夹的实例化
            blockFile.mkdir();

            //盘块的实例化
            blockBitMap.createNewFile();
            bitWriter = new FileWriter(blockBitMap);
            for (int i = 0; i < 32; i++) {
                for (int k = 0; k < 32; k++) {
                    bitmap[i][k] = 0;
                    bitWriter.write("0");
                }
                bitWriter.write("\r\n");
            }
            bitWriter.flush();

            //恢复文件的实例化
            recover.createNewFile();
            recoverWriter = new FileWriter(recover);
            //第一二行分别为已占大小，和文件数
            recoverWriter.write(String.valueOf(space) + "\r\n");
            recoverWriter.write(String.valueOf(fileNum) + "\r\n");

            for (int i = 0; i < 32; i++) {
                for (int k = 0; k < 32; k++) {
                    //与位图保持一致
                    if (bitmap[i][k] == 0) {
                        recoverWriter.write("0\r\n");
                    } else {
                        recoverWriter.write("1\r\n");
                    }
                }
            }
            recoverWriter.flush();
        }else{

            //恢复模式
            try {
                //bitmap数组的值来自于恢复文件
                BufferedReader reader = new BufferedReader(new FileReader(recover));

                space = Double.parseDouble(reader.readLine());
                fileNum = Integer.parseInt(reader.readLine());

                for (int i = 0; i < 32; i++) {
                    for (int k = 0; k < 32; k++) {
                        if (Integer.parseInt(reader.readLine()) == 0) {
                            bitmap[i][k] = 0;
                        } else {
                            bitmap[i][k] = 1;
                        }
                    }
                }

                String temp;
                while ((temp = reader.readLine()) != null) {
                    File myFile = new File(blockFile.getPath() + File.separator + temp);
                    files.add(myFile);
                    int[][] tempBit = new int[32][32];
                    for (int i = 0; i < 32; i++) {
                        for (int k = 0; k < 32; k++) {
                            if (Integer.parseInt(reader.readLine()) == 0) {
                                tempBit[i][k] = 0;
                            } else {
                                tempBit[i][k] = 1;
                            }
                        }
                    }
                    filesBit.put(myFile.getName(), tempBit);
                }
                reader.close();
            }catch (Exception e){
                JOptionPane.showMessageDialog(null, "The files aren't complete. You can choose another place or delete \"myFileSystem\" in this dir and run this again!",
                "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

    public File getBlockFile(){
        return blockFile;
    }

    /**
     * 写入FCB
     * @param file
     * @param capacity
     * @throws IOException
     */
    public void putFCB(File file, double capacity) throws IOException {
        FileWriter newFileWriter = new FileWriter(file);
        newFileWriter.write("File\r\n");
        newFileWriter.write("Size" + String.valueOf(capacity) + "\r\n");
        newFileWriter.write("Name: " + file.getName() + "\r\n");
        newFileWriter.write("Location: " + file.getPath() + "\r\n");
        String ctime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(file.lastModified()));
        newFileWriter.write("Modified: " + ctime + "\r\n");
        newFileWriter.write("--------------------------edit file blew ------------------------------\r\n");
        newFileWriter.close();
    }


    /**
     * 重写盘块文件
     * 在盘块的结尾写明盘块使用情况
     * @throws IOException
     */
    public void rewriteBitMap() throws IOException {
        bitWriter = new FileWriter(blockBitMap);
        bitWriter.write("");
        for (int i = 0; i < 32; i++){
            for (int k = 0; k < 32; k++){
                if (bitmap[i][k] == 0){
                    bitWriter.write("0");
                }else{
                    bitWriter.write("1");
                }
            }
            bitWriter.write("\r\n");
        }
        for (int i = 0; i < files.size(); i++){
            bitWriter.write(files.get(i).getName() + ":");
            for (int k = 0; k < 32; k++){
                for (int j = 0; j < 32; j++){
                    try {
                        if (filesBit.get(files.get(i).getName())[k][j] == 1) {
                            bitWriter.write(String.valueOf(k * 32 + j) + " ");
                        }
                    }catch (Exception e){
                        System.out.println("wrong");
                    }
                }
            }
            bitWriter.write("\r\n");
        }
        bitWriter.flush();
    }


    /**
     * 重写恢复文件
     * @throws IOException
     */
    public void rewriteRecoverWriter() throws IOException{
        recoverWriter = new FileWriter(recover);
        recoverWriter.write("");

        recoverWriter.write(String.valueOf(space) + "\r\n");
        recoverWriter.write(String.valueOf(fileNum) + "\r\n");

        for (int i = 0; i < 32; i++){
            for (int k = 0; k < 32; k++){
                if (bitmap[i][k] == 0){
                    recoverWriter.write("0\r\n");
                }else{
                    recoverWriter.write("1\r\n");
                }
            }
        }
        for (int i = 0; i < files.size(); i++){
            recoverWriter.write(files.get(i).getName() + "\r\n");
            int [][] bitTemp = filesBit.get(files.get(i).getName());
            for (int k = 0; k < 32; k++){
                for (int j = 0; j < 32; j++){
                    if (bitTemp[k][j] == 0){
                        recoverWriter.write("0\r\n");
                    }else {
                        recoverWriter.write("1\r\n");
                    }
                }
            }
        }
        recoverWriter.flush();
    }

    public boolean createFile(File file, double capacity) throws IOException {
        files.add(file);
        file.createNewFile();
        int cap[][] = new int[32][32];
        for (int i = 0; i < 32; i++){
            for (int k = 0; k < 32; k++)
                cap[i][k] = 0;
        }
        BufferedReader in = new BufferedReader(new FileReader(blockBitMap));
        int count = (int) capacity;
        for (int i = 0; i < 32; i++){
            String line  = in.readLine();
            for (int k = 0; k < 32; k++){
                if (count > 0) {
                    if (line.charAt(k) == '0') {
                        count--;
                        cap[i][k] = 1;
                        bitmap[i][k] = 1;
                    }
                }
            }
        }
        if (count > 0){
            JOptionPane.showMessageDialog(null, "Insufficient memory!!", "Fail", JOptionPane.ERROR_MESSAGE);
            file.delete();
            for (int i = 0; i < 32; i++){
                for (int k = 0; k < 32; k++){
                    if (cap[i][k] == 1){
                        bitmap[i][k] = 0;
                    }
                }
            }
            return false;
        }else{
            fileNum++;
            space += capacity;
            filesBit.put(file.getName(), cap);
            rewriteBitMap();
            rewriteRecoverWriter();
            // Put FCB
            putFCB(file, capacity);

            return true;
        }
    }

    /**
     * 删除文件的方法
     * @param file
     * @param capacity
     * @return
     */
    public boolean deleteFile(File file, double capacity){
        //保护文件声明
        if (file.getName().equals("1") || file.getName().equals("2") || file.getName().equals("3") || file.getName().equals("4")
                || file.getName().equals("5") || file.getName().equals("6") || file.getName().equals("7") || file.getName().equals("8")
                || file.getName().equals("9") || file.getName().equals("10") || file.getName().equals("1BitMap&&Fat.txt")
                || file.getName().equals("2BitMap&&Fat.txt") || file.getName().equals("3BitMap&&Fat.txt")
                || file.getName().equals("4BitMap&&Fat.txt") || file.getName().equals("5BitMap&&Fat.txt")
                || file.getName().equals("5BitMap&&Fat.txt") || file.getName().equals("6BitMap&&Fat.txt")
                || file.getName().equals("7BitMap&&Fat.txt") || file.getName().equals("8BitMap&&Fat.txt")
                || file.getName().equals("9BitMap&&Fat.txt") || file.getName().equals("10BitMap&&Fat.txt")
                || file.getName().equals("recover.txt")){
            JOptionPane.showMessageDialog(null, "The dir is protected!!", "Access fail", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try{
            if (file.isFile()){
                try {
                    file.delete();
                }catch (Exception e){
                    e.printStackTrace();
                }
                space -= capacity;
                fileNum--;
                int[][] fileStore = filesBit.get(file.getName());
                for (int i = 0; i < 32; i++){
                    for (int k = 0; k < 32; k++){
                        if (bitmap[i][k] == 1 && fileStore[i][k] == 1){
                            bitmap[i][k] = 0;
                        }
                    }
                }
                filesBit.remove(file.getName());
                for (int i = 0; i < files.size(); i++){
                    if (files.get(i).getName().equals(file.getName())){
                        files.remove(i);
                        break;
                    }
                }
            }else{
                File [] files = file.listFiles();
                for(File myFile : files){
                    deleteFile(myFile, capacity);
                }
                while(file.exists()) {
                    file.delete();
                }
            }
            return true;
        }catch (Exception e){
            System.out.println("fail");
            return false;
        }
    }

    public boolean renameFile(File file, String name, double capacity) throws IOException {
        String oldName = file.getName();
        int[][] tempBit = filesBit.get(oldName);
        String c = file.getParent();
        File mm;
        if(file.isFile()) {
            mm = new File(c + File.separator + name + ".txt");
            if (file.renameTo(mm)){
                file = mm;
                filesBit.remove(oldName);
                filesBit.put(file.getName(), tempBit);
                // Put FCB
                putFCB(file, capacity);
                for (int i = 0; i < files.size(); i++){
                    if (files.get(i).getName().equals(oldName)){
                        files.remove(i);
                        files.add(file);
                        break;
                    }
                }
                rewriteBitMap();
                rewriteRecoverWriter();
                return true;
            }else{
                return false;
            }
        }
        else {
            mm = new File(c + File.separator + name);
            file.renameTo(mm);
            return true;
        }
    }

    public int getFileNum() {
        return fileNum;
    }

    public double getSpace() {
        return space;
    }
}
