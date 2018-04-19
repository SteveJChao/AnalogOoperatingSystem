package user_interface;


import A_General.Init;
import A_General.OS;
import ConstantMemory.GetConMemList;
import ConstantMemory.Pieces;
import DiscreteMemory.GetPageArrayList;
import DiscreteMemory.Page;
import OS_Kernel.OS_Kernel;
import ProcessScheduling.CPU_Schedul;
import Jobs.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import Process.*;
import ProcessScheduling.CreatProcess;
import Process.Process;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

/**
 * 任务管理界面
 */
public class TaskWinDiscrete {
    public JFrame frame;
    public JPanel panelLeft;
    public JPanel panelRight;

    //功能选择
    public JPanel panelNorth;
    //进程展示
    public JPanel panelWest;
    //内存展示
    public JPanel panelCenter;
    //磁盘展示
    public JPanel panelSouth;
    public JTextField textField;

    public JPanel panelPcbDetail;

    //
    public JTabbedPane tabbedPane;

    //第一行的按钮
    public JButton butStart;
    public JButton butStop;
    public JButton butRandomCreate;
    public JButton butKernelCreate;
    public JButton butSearch;
    public JButton butRefresh;

    //接受参数输入输入框
    public JTextField numField;
    public JTextField roundsField;
    public JTextField timePieceField;

    public JTextArea textArea;
    public JScrollPane scrollPane;

    public int num = 49;//limit
    public int rounds = 100;

    public JButton butDetail;

    public JComboBox comboBox;

    public Graphics gCenter;
    public Graphics gSouth;


    //三个PCB队列
    public ArrayList<PCB> arrayListRun;
    public ArrayList<PCB> arrayListReady;
    public ArrayList<PCB> arrayListBlock;
    //后备JCB队列
    public ArrayList<JCB> arrayListPrepare;
    //pieces队列
    public ArrayList<Pieces> arrayListBlank;
    public ArrayList<Pieces> arrayListUsed;

    public ArrayList<Page> inMemPageList;
    public ArrayList<Page> outMemPageList;

    public int blankTotalSize = 0;
    public int usedTotalSize = 0;

    //绘制列表的数目
    private final static int LIST = 10;

    private boolean butDown = false;
    private boolean on = true;

    private static final int MEM_WIDTH = 320;
    private static final int MEM_X = 220;
    private static final double TOTAL_SIZE = 64 * 1024;
    private static final String TAB_MEMORY = "memory";
    private static final String TAB_PCB_DETAIL = "detail";

    /**
     *
     */
    public TaskWinDiscrete() {
//        new OS_Kernel();

        initUI();
        CreatProcess.randomCreate(num);
        onButtonClick(butStart);
        onButtonClick(butStop);
        onButtonClick(butRandomCreate);
        onButtonClick(butSearch);
        onButtonClick(butRefresh);
        onButtonClick(butKernelCreate);
    }

    /**
     *
     */
    public void initUI() {
        frame = new JFrame();
        frame.setSize(new Dimension(1920,1040));

        panelLeft = new JPanel();
        panelLeft.setPreferredSize(new Dimension(1300,1050));
        panelLeft.setBackground(new Color(211,211,211));
        frame.add(panelLeft, BorderLayout.WEST);

        panelRight = new JPanel();
        panelRight.setBackground(new Color(211,211,211));

        frame.add(panelRight, BorderLayout.CENTER);



        /**
         * panelNorth
         */
        panelNorth = new JPanel() {
            Graphics g = this.getGraphics();
            @Override
            public void paint(Graphics g) {
                super.paint(g);

                g.setColor(Color.white);
                g.setFont(new Font("Consolas",Font.BOLD,25));
//                g.drawString(arrayListRun.get(0).process.Pname, 20,20);
            }

        };
        panelNorth.setBackground(new Color(238,238,238));
        panelNorth.setPreferredSize(new Dimension(1250, 200));


        /**
         * panelWest
         */
        panelWest = new JPanel(){
            Graphics g = this.getGraphics();
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                for (int i = 0; i < LIST; i++) {
                    //running
                    g2d.setColor(new Color(234,67,53));
                    g2d.fillRoundRect(30 + i * 112,20,112,40,40,40);

                    g2d.setColor(Color.white);
                    g2d.setFont(new Font("Consolas",Font.BOLD,15));
                    if (arrayListRun != null ) {
//                        && arrayListRun.size() > 0
                        for (int j = 0; j < arrayListRun.size(); j++) {
                            g2d.drawString(arrayListRun.get(j).process.Pname, 42 + j * 112,45);
                        }
                    }



                    //ready
                    g2d.setColor(new Color(52,168,83));
                    g2d.fillRoundRect(30 + i * 112,70,112,42,40,40);
                    g2d.fillRoundRect(30 + i * 112,115,112,42,40,40);
//                    g2d.fillRoundRect(30 + i * 112,160,112,42,40,40);
                    g2d.setColor(Color.white);
                    g2d.setFont(new Font("Consolas",Font.BOLD,15));
                    if (arrayListReady != null) {
                        int n = arrayListReady.size();
                        if (0 < n && n < 10) {
                            for (int j = 0; j < n; j++) {
                                g2d.drawString(arrayListReady.get(j).process.Pname, 42 + j * 112,95);
                            }
                        }
                        if (10 <= n && n < 20) {
                            for (int j = 0; j < 10; j++) {
                                g2d.drawString(arrayListReady.get(j).process.Pname, 42 + j * 112,95);
                            }
                            for (int j = 10; j < n; j++) {
                                g2d.drawString(arrayListReady.get(j).process.Pname, 42 + (j - 10) * 112,140);
                            }
                        }
                        if (20 <= n && n < 30) {
                            for (int j = 0; j < 10; j++) {
                                g2d.drawString(arrayListReady.get(j).process.Pname, 42 + j * 112,95);
                            }
                            for (int j = 10; j < 20; j++) {
                                g2d.drawString(arrayListReady.get(j).process.Pname, 42 + (j - 10) * 112,140);
                            }
                            for (int j = 20; j < n; j++) {
                                g2d.drawString(arrayListReady.get(j).process.Pname, 42 + (j - 20) * 112,185);
                            }
                        }
                    }

                    //block
                    g2d.setColor(new Color(120,120,120));
                    g2d.fillRoundRect(30 + i * 112,165,112,40,40,40);
                    g2d.fillRoundRect(30 + i * 112,208,112,40,40,40);

                    //prepare
                    g2d.setColor(new Color(85,85,85));
                    g2d.fillRoundRect(30 + i * 112,260,112,40,40,40);
                    g2d.fillRoundRect(30 + i * 112,302,112,40,40,40);
                    g2d.fillRoundRect(30 + i * 112,346,112,40,40,40);


                    /**
                     * 绘制后备队列信息
                     */
                    g2d.setColor(new Color(255,255,255));
                    if (arrayListPrepare != null) {
                        int n = arrayListPrepare.size();
                        if (n > 0  && n < 10) {
                            for (int j = 0; j < n; j++) {
                                g2d.drawString(arrayListPrepare.get(j).pcb.process.Pname, 42 + j * 112,283);
                            }
                        }
                        if (n >= 10 && n < 20) {
                            for (int j = 0; j < 10; j++) {
                                g2d.drawString(arrayListPrepare.get(j).pcb.process.Pname, 42 + j * 112,283);
                            }
                            for (int j = 10; j < n; j++) {
                                g2d.drawString(arrayListPrepare.get(j).pcb.process.Pname, 42 + (j - 10) * 112,325);
                            }
                        }

                        if (n >= 20 && n < 30) {
                            for (int j = 0; j < 10; j++) {
                                g2d.drawString(arrayListPrepare.get(j).pcb.process.Pname, 42 + j * 112,283);
                            }
                            for (int j = 10; j < 20; j++) {
                                g2d.drawString(arrayListPrepare.get(j).pcb.process.Pname, 42 + (j - 10) * 112,325);
                            }
                            for (int j = 20; j < n; j++) {
                                g2d.drawString(arrayListPrepare.get(j).pcb.process.Pname, 42 + (j - 20) * 112,370);
                            }
                        }
                    }

                }
            }
        };
        panelWest.setBackground(new Color(238,238,238));
        panelWest.setPreferredSize(new Dimension(1250, 409));

        /**
         * panelCenter
         */
        panelCenter = new JPanel() {
            Graphics g = this.getGraphics();

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(210,210,210));

                //16 * 32的网格
                for (int i = 0; i < 16; i++) {
                    for (int j = 0; j < 30; j++) {
                        g2d.fillRect(10 + i * 35, 10 + j * 25 ,30,20);
                    }
                }
                //kernel
                g2d.setColor(new Color(52,168,83));
                for (int i = 0; i < 16; i++) {
                    for (int j = 30; j < 32; j++) {
                        g2d.fillRect(10 + i * 35, 10 + j * 25 ,30,20);
                    }
                }
                //inMemory
                if (inMemPageList != null) {
                    g2d.setColor(new Color (234,67,53));
                    for (int i = 0; i < inMemPageList.size(); i++) {
                        int line = getLine(inMemPageList.get(i).ID);
                        int column = getColumn(inMemPageList.get(i).ID);
                        g2d.fillRect(10 + column * 35, 10 + line * 25, 30, 20);
                    }
                }
                //outMemory
                if (outMemPageList != null) {
                    g2d.setColor(new Color (160,160,160));
                    for (int i = 0; i < outMemPageList.size(); i++) {
                        int line = getLine(outMemPageList.get(i).ID);
                        int column = getColumn(outMemPageList.get(i).ID);
                        g2d.fillRect(10 + column * 35, 10 + line * 25, 30, 20);
                    }
                }

                //提示信息
                g2d.setColor(new Color (234,67,53));
                g2d.setFont(new Font("Consolas",Font.BOLD,17));
                g2d.drawString("pages inside memory", 10,835);
                g2d.fillRect(220,820,30,20);

                g2d.setColor(new Color (140,140,140));
                g2d.setFont(new Font("Consolas",Font.BOLD,17));
                g2d.drawString("pages outside memory", 10,865);
                g2d.fillRect(220,850,30,20);

                g2d.setColor(new Color (52,168,83));
                g2d.setFont(new Font("Consolas",Font.BOLD,17));
                g2d.drawString("memory for kernel", 10,895);
                g2d.fillRect(220,880,30,20);
            }
        };
        panelCenter.setBackground(new Color(238,238,238));
        panelCenter.setPreferredSize(new Dimension(575, 950));


        /**
         * panelSouth
         */
        panelSouth = new JPanel(){
            Graphics g = this.getGraphics();
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setFont(new Font("Consolas",0,20));
                //画运行队列详细信息
                g2d.setColor(Color.black);
                g2d.drawString("ID", 40,30);
                g2d.drawString("Name", 100,30);
                g2d.drawString("Type", 250,30);
                g2d.drawString("Owner", 360,30);
                g2d.drawString("OwnerGroup", 480,30);
                g2d.drawString("prior", 630,30);
                g2d.drawString("timeNeeded", 760,30);
                g2d.drawString("timeUsed", 900,30);
                if (arrayListRun != null) {
                    g2d.setColor(new Color(234,67,53));
                    for (int i = 0; i < arrayListRun.size(); i++) {
                        PCB pcb = arrayListRun.get(i);
                        g2d.drawString("" + pcb.process.PID,40,60 + (i * 25));
                        g2d.drawString("" + pcb.process.Pname, 100,60 + (i * 25));
                        g2d.drawString("" + pcb.process.Ptype,250,60 + (i * 25));
                        g2d.drawString("" + pcb.process.Powner, 360,60 + (i * 25));
                        g2d.drawString("" + pcb.process.PownerGroup,480,60 + (i * 25));
                        g2d.drawString("" + pcb.Pprio, 630,60 + (i * 25));
                        g2d.drawString("" + pcb.Ptime_needed, 760,60 + (i * 25));
                        g2d.drawString("" + pcb.process.Ptime_used, 900,60 + (i * 25));

                    }
                }
            }
        };
        panelSouth.setBackground(new Color(238,238,238));
        panelSouth.setPreferredSize(new Dimension(1250, 300));

        panelSouth.add(nullLabel(1200,15));
        textArea = new JTextArea();
        setTextArea(textArea);

        panelLeft.add(nullLabel(1200,20));
        panelLeft.add(panelNorth, BorderLayout.NORTH);
        panelLeft.add(nullLabel(1200,10));

        panelLeft.add(panelWest, BorderLayout.WEST);
        panelLeft.add(nullLabel(1200,10));

        panelLeft.add(panelSouth, BorderLayout.SOUTH);

        panelRight.add(nullLabel(600,20));
        panelRight.add(panelCenter);

//        panelPcbDetail = new JPanel() {
//            Graphics g = this.getGraphics();
//
//            @Override
//            public void paint(Graphics g) {
//                super.paint(g);
//                Graphics2D g2d = (Graphics2D) g;
//                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//                g2d.setFont(new Font("Consolas",0,20));
//
//                g2d.setColor(Color.black);
//                g2d.drawString("ID", 40,30);
//                g2d.drawString("Name", 100,30);
//                g2d.drawString("Type", 220,30);
//                g2d.drawString("Thread", 360,30);
//                g2d.drawString("Owner", 500,30);
//                g2d.drawString("OwnerGroup", 650,30);
//                g2d.drawString("Port", 820,30);
//
//                while (arrayListReady != null) {
//                    g2d.setColor(new Color(66,133,224));
//                    //TODO
//                    for (int i = 0; i < arrayListReady.size(); i++){
//                        //TODO
//                    }
//                    //TODO
//                    for (int i = 0; i < arrayListBlock.size(); i++){
//                        //TODO
//                    }
//                    //TODO
//                    for (int i = 0; i < arrayListPrepare.size(); i++){
//                        //TODO
//                    }
//
//                }
//            }
//        };
//        panelPcbDetail.setPreferredSize(new Dimension(500,900));
//        panelPcbDetail.setBackground(new Color(238,238,238));




//        UIManager.put("TabbedPane.borderHightlightColor", Color.gray);
//        UIManager.put("TabbedPane.darkShadow", Color.gray);
//        UIManager.put("TabbedPane.light", Color.gray);
//        UIManager.put("TabbedPane.selectHighlight", Color.gray);
//        UIManager.put("TabbedPane.darkShadow", Color.gray);
//        UIManager.put("TabbedPane.focus", Color.gray);
//        tabbedPane = new JTabbedPane() {};
//
////        tabbedPane.setBorder(new EmptyBorder(0,0,0,0));
//        tabbedPane.add(TAB_MEMORY, panelCenter);
//        tabbedPane.add(TAB_PCB_DETAIL, panelPcbDetail);
//        //tabbedPane.setFocusable(false);


//        panelRight.add(tabbedPane);


//        panelNorth.add(setJLabel(200, 30, "NUMBER", Color.white));
//        numField = new JTextField();
//        setTextField(numField, 200,30);
//        panelNorth.add(nullLabel(900,30));
//        panelNorth.add(setJLabel(200, 30, "ROUNDS", Color.white));
//        roundsField = new JTextField();
//        setTextField(roundsField, 200,30);
//        panelNorth.add(nullLabel(900,30));
        panelNorth.add(nullLabel(1200,10));
        panelNorth.add(setJLabel(140, 30, "TIMEPIECE", new Color(66,133,224)));
        timePieceField = new JTextField();
        setTextField(timePieceField, 200,28);
        panelNorth.add(nullLabel(800,30));


//        panelNorth.add(setJLabel(200, 30, "Algorithm", Color.white));
        comboBox = new JComboBox();
        comboBox.addItem("时间片轮转");
        comboBox.addItem("短进程优先");
        comboBox.addItem("非抢占高优先权");
        comboBox.addItem("抢占式高优先权");
        comboBox.addItem("高响应比优先");
        comboBox.addItem("先来先服务");
        comboBox.setForeground(new Color(60,91,134));
        comboBox.setFont(new Font("Consolas",Font.BOLD,25));

        comboBox.setBackground(Color.white);
        comboBox.setPreferredSize(new Dimension(200,33));
        onComboBoxClick(comboBox);
//        panelNorth.add(comboBox);

        panelNorth.add(nullLabel(50,30));

        butStart = new JButton("Power on");
        butStart.setPreferredSize(new Dimension(170,30));
        butStart.setBackground(new Color(66,133,244));
        butStart.setForeground(Color.white);
        butStart.setFont(new Font("Consolas",Font.BOLD,25));
        butStart.setFocusPainted(false);
        butStart.setBorder(new EmptyBorder(0,0,0,0));
        panelNorth.add(butStart);
        butStop = new JButton("Sleep");
        butStop.setPreferredSize(new Dimension(170,30));
        butStop.setBackground(new Color(66,133,244));
        butStop.setForeground(Color.white);
        butStop.setFont(new Font("Consolas",Font.BOLD,25));
        butStop.setFocusPainted(false);
        butStop.setBorder(new EmptyBorder(0,0,0,0));
        panelNorth.add(butStop);

        panelNorth.add(nullLabel(856,30));

        butRandomCreate = new JButton("Random Create");
        butRandomCreate.setPreferredSize(new Dimension(345,30));
        butRandomCreate.setBackground(new Color(66,133,244));
        butRandomCreate.setForeground(Color.white);
        butRandomCreate.setFont(new Font("Consolas",Font.BOLD,25));
        butRandomCreate.setFocusPainted(false);
        butRandomCreate.setBorder(new EmptyBorder(0,0,0,0));
        panelNorth.add(butRandomCreate);

        butKernelCreate = new JButton("Create Kernel process");
        butKernelCreate.setPreferredSize(new Dimension(345,30));
        butKernelCreate.setBackground(new Color(66,133,244));
        butKernelCreate.setForeground(Color.white);
        butKernelCreate.setFont(new Font("Consolas",Font.BOLD,25));
        butKernelCreate.setFocusPainted(false);
        butKernelCreate.setBorder(new EmptyBorder(0,0,0,0));
        panelNorth.add(butKernelCreate);


        panelNorth.add(nullLabel(505,30));

        textField = new JTextField();
        textField.setText("");
        setTextField(textField, 345, 30);
        butSearch = new JButton("Search");
        butSearch.setPreferredSize(new Dimension(170,30));
        butSearch.setBackground(new Color(66,133,244));
        butSearch.setForeground(Color.white);
        butSearch.setFont(new Font("Consolas",Font.BOLD,25));
        butSearch.setFocusPainted(false);
        butSearch.setBorder(new EmptyBorder(0,0,0,0));
        panelNorth.add(butSearch);

        butRefresh = new JButton("Refresh");
        butRefresh.setPreferredSize(new Dimension(170,30));
        butRefresh.setBackground(new Color(66,133,244));
        butRefresh.setForeground(Color.white);
        butRefresh.setFont(new Font("Consolas",Font.BOLD,25));
        butRefresh.setFocusPainted(false);
        butRefresh.setBorder(new EmptyBorder(0,0,0,0));
        panelNorth.add(butRefresh);
        panelNorth.add(nullLabel(505,30));



        frame.setLocationRelativeTo(null);
        frame.setDefaultLookAndFeelDecorated(true);
        frame.setVisible(true);
        gCenter = panelCenter.getGraphics();
        gSouth = panelSouth.getGraphics();
    }

    /**
     * 获取第一部分的属性输入值
     * @return
     */
    public int[] getProperties() {
        int[] array = new int[3];


        return array;
    }

    /**
     * 获取一个piece的height
     * @param usedPiece
     * @return
     */
    public int getHeight(Pieces usedPiece) {
        int size = usedPiece.size;
        int height = (int)(size/TOTAL_SIZE * 900);
        return height;
    }

    /**
     * 通过计算之前的y的值计算当前的y
     * @param index
     * @return
     */
    public int getY(int index) {
        int y = 20;
        y += (int)(((double)arrayListUsed.get(index).start / TOTAL_SIZE) * 900);
        return y;
    }

    public int getHeight(int index) {
        int height = 0;
        height += (int)(((double)arrayListUsed.get(index).size / TOTAL_SIZE) * 900);
        return height;
    }

    /**
     * 位于第line行
     * @param pageID
     * @return
     */
    public int getLine(int pageID) {
        int line = pageID / 16;
        return line;
    }

    /**
     * 位于第column列
     * @param pageID
     * @return
     */
    public int getColumn(int pageID) {
        int column = pageID % 16;
        return column;
    }

    /**
     * button点击事件
     * @param button
     */
    public void onButtonClick(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Power on")) {
                    //输出当前的调度算法
                    changePowerOn();
                    getListData().start();
                    OS.TIMEPIECE = getTimePiece();
                    System.out.println(OS.TIMEPIECE);
                } else if (e.getActionCommand().equals("Sleep")) {
                    changePowerCLose();
                } else if (e.getActionCommand().equals("Random Create")) {
                    CreatProcess.randomCreate(5);
                } else if (e.getActionCommand().equals("Search")) {
                    try {
                        int PID = getPID();
                        if (checkPIDInUse(PID)) {
                            gCenter.setColor(new Color(254, 153, 6));
                            PCB pcb = OS.PCBtable.get(PID);
                            Vector<Page> vector = pcb.pageTable;
                            for (int i = 0; i < vector.size(); i++) {
                                int line = getLine(vector.get(i).ID);
                                int column = getColumn(vector.get(i).ID);
                                gCenter.fillRect(10 + column * 35, 10 + line * 25, 30, 20);
                            }
                            //在panelSouth绘制选中的详细信息
                            gSouth.setFont(new Font("Consolas", 0, 20));
                            gSouth.setColor(new Color(254, 153, 6));
                            gSouth.drawString("" + pcb.process.PID, 40, 170);
                            gSouth.drawString("" + pcb.process.Pname, 100, 170);
                            gSouth.drawString("" + pcb.process.Ptype, 250, 170);
                            gSouth.drawString("" + pcb.process.Powner, 360, 170);
                            gSouth.drawString("" + pcb.process.PownerGroup, 480, 170);
                            gSouth.drawString("" + pcb.Pprio, 630, 170);
                            gSouth.drawString("" + pcb.Ptime_needed, 760, 170);
                            gSouth.drawString("" + pcb.process.Ptime_used, 900, 170);

                            TestModel.outputTest.printALL(pcb);
//                            System.out.println("yes");
                        } else {
                            JOptionPane.showMessageDialog(null, "No PCB in this ID or the PCB is out of memory!", "Not Found", JOptionPane.DEFAULT_OPTION);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Please input an Integer!", "Error", JOptionPane.DEFAULT_OPTION);
                    }
                } else if (e.getActionCommand().equals("Refresh")) {
                    panelCenter.repaint();
                    panelSouth.repaint();
                } else if (e.getActionCommand().equals("Create Kernel process")) {
                    CreatProcess.createKernelProcess();
                }

            }
        });
    }

    /**
     * comboBox点击事件
     * @param comboBox
     */
    public void onComboBoxClick(JComboBox comboBox) {
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch ((String)e.getItem()) {
                        case "先来先服务":
                            OS.CPUSchedulFLAG = 5;
                            break;
                        case "短进程优先":
                            OS.CPUSchedulFLAG = 4;
                            break;
                        case "非抢占高优先权":
                            OS.CPUSchedulFLAG = 3;
                            break;
                        case "抢占式高优先权":
                            OS.CPUSchedulFLAG = 2;
                            break;
                        case "高响应比优先":
                            OS.CPUSchedulFLAG = 1;
                            break;
                        case "时间片轮转":
                            OS.CPUSchedulFLAG = 0;
                            break;
                    }
                }
            }
        });
    }


    /**
     * 子线程启动进程调度
     * 获取队列数据
     * @return
     */
    public Thread getListData() {
//        Init.init();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (on) {
                    CPU_Schedul.CPURun();

                    GetPcbList gpl = new GetPcbList();
                    arrayListRun = gpl.getPcbArrayList(OS.running_list);
                    arrayListReady = gpl.getPcbArrayList(OS.ready_list);
                    arrayListBlock = gpl.getPcbArrayList(OS.block_list);
                    arrayListPrepare = new GetJcbList().getJcbArrayList(OS.jobsList);
                    GetConMemList gcml1 = new GetConMemList();
                    GetConMemList gcml2 = new GetConMemList();

                    arrayListBlank = gcml1.getPiecesArrayList(OS.MemoryPage.blankList);
                    arrayListUsed = gcml2.getPiecesArrayList(OS.MemoryPage.usedList);

                    blankTotalSize = gcml1.getTotalSize(arrayListBlank);
                    usedTotalSize = gcml2.getTotalSize(arrayListUsed);

                    GetPageArrayList gpal = new GetPageArrayList();
                    inMemPageList = new GetPageArrayList().getPageArrayList(OS.inMemoryPages);
                    outMemPageList = new GetPageArrayList().getPageArrayList(OS.outMemoryPages);



                    panelWest.repaint();
                    panelCenter.repaint();
                    panelSouth.repaint();
                    panelNorth.repaint();
                }
            }
        });
        return thread;
    }

    public void changePowerOn() {
        on = true;
    }

    public void changePowerCLose() {
        on = false;

    }

    /**
     * 获取PID
     * @return
     */
    public int getPID() throws Exception {
        String PID_String = "";
        if (textField == null) return -1000;
        PID_String = textField.getText();
        if (PID_String.equals("input the process ID here.")) return -1000;
        int PID = Integer.parseInt(PID_String);
        return PID;
    }

    /**
     * 获取输入的时间片 默认5
     * @return
     */
    public int getTimePiece() {
        String timePieceString = timePieceField.getText();
        if(timePieceString.equals("")) return 5;
        int timePiece = Integer.parseInt(timePieceString);
        return timePiece;
    }


    /**
     * 判断
     * @param PID
     * @return
     */
    public boolean checkPIDInUse(int PID) {
        return OS.PCBtable.containsKey(PID);
    }


    /**
     * 占位
     * @param width
     * @param height
     * @return
     */
    public JLabel nullLabel(int width, int height) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    /**
     * 设置textField
     * @param textField
     */
    public void setTextField(JTextField textField, int width, int height){
        textField.setPreferredSize(new Dimension(width, height));
        textField.setBackground(new Color(211,211,211));
        textField.setBorder(new EmptyBorder(0,0,0,0));
        textField.setForeground(new Color(66,133,224));
        textField.setFont(new Font("Consolas",Font.BOLD,22));
        panelNorth.add(textField);
    }

    /**
     * 设置label
     * @param width
     * @param height
     * @param lab
     * @param color
     * @return
     */
    public JLabel setJLabel(int width, int height, String lab, Color color) {
        JLabel label = new JLabel(lab);
        label.setPreferredSize(new Dimension(width, height));
        label.setFont(new Font("Consolas",Font.BOLD,25));
        label.setForeground(color);
        return label;
    }

    public void setTextArea(JTextArea textArea) {
        textArea.setPreferredSize(new Dimension(1200,250));
        textArea.setBackground(new Color(220,220,220));
        textArea.setFont(new Font("Consolas",Font.BOLD,40));
        textArea.setText("You can find the entire code for \nthis program in TextDemo.java . The fol\nlowing code creates and initial\nizes the text area: textArea = ne\nw JTextArea(5, 20); JScrollPane scrollPane = \nnew JScrollPane(textArea); textArea.setEditable(false);. The two arguments to the JTextArea constr\nuctor are hints as ,You can find th\ne entire code for this program in TextDemo.java . The following code creates and initializes t\nhe text area: textArea = new JTextArea(5, 2\n0); JScrollPane scrollPane = \nnew JScrollPane(textArea); textArea.se\ntEditable(false);. Th\ne two arguments to the J\nTextArea constructor a\nre hints as ,You can find th\ne entire code for this p\nrogram in TextvDem\no.java . The following code crv\neates and initi\nalizes the text area: textArea = \nnew JTextArea(5, 20); JScrollPanv\ne scrollPane = new J\nScrollPane(textArea); tex\ntArea.setEdi\ntable(false);. The two ar\nguments to the JTextArea constru\nctor are hints as ");
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(1200,250));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setViewportView(textArea);
//        panelSouth.add(scrollPane);
    }


    public static void main(String[] args) {
        new TaskWinDiscrete();

    }
}
