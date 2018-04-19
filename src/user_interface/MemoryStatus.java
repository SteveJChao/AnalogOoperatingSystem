package user_interface;

import A_General.OS;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MemoryStatus {
    public JFrame frame;
    public JPanel panelConstant;
    public JPanel panelDiscrete;
    public JPanel panelNorth;
    public JPanel panel;
    public JTabbedPane tabbedPane;
    public JCheckBox boxConstant;
    public JCheckBox boxDiscrete;

    public JComboBox comboBox0;
    public JComboBox comboBoxDisk;

    public JComboBox comboBoxCon;
    public JComboBox comboBoxDis;

    private static final String CONSTANT_ALLOCATE = "Constant MemoryAllocate";
    private static final String DISCRETE_ALLOCATE = "Discrete MemoryAllocate";


    public MemoryStatus() {
        initUI();
    }

    public void initUI() {
        frame = new JFrame();
        frame.setSize(new Dimension(600,360));


        panelNorth = new JPanel() {
            Graphics g = this.getGraphics();
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(Color.black);
                g2d.setFont(new Font("Consolas",0,18));

                g2d.drawString("Memory allocate algorithm", 10, 20);
                g2d.drawString("Disk allocate algorithm", 343, 20);
            }
        };




        comboBox0 = new JComboBox();
        comboBox0.setBackground(Color.white);
        comboBox0.setPreferredSize(new Dimension(330,30));
        comboBox0.addItem("ConstantMemoryAllocate");
        comboBox0.addItem("DiscreteMemoryAllocate");
        comboBox0.setForeground(new Color(66,133,224));
        comboBox0.setFont(new Font("Consolas",Font.BOLD,20));
        panelNorth.add(setNullLabel(600,20));
        panelNorth.add(comboBox0);
//        panelNorth.add(setNullLabel(225,20));
        comboBox0.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch ((String)e.getItem()) {
                        case "ConstantMemoryAllocate":
                            OS.MemoryAllocateFLAG = 0;
                            break;
                        case "DiscreteMemoryAllocate":
                            OS.MemoryAllocateFLAG = 1;
                            break;
                    }
                }
            }
        });


        comboBoxDisk = new JComboBox();
        comboBoxDisk.setBackground(Color.white);
        comboBoxDisk.setPreferredSize(new Dimension(230,30));
        comboBoxDisk.addItem("FCFS");
        comboBoxDisk.addItem("SCAN");
        comboBoxDisk.addItem("CSCAN");
        comboBoxDisk.setForeground(new Color(66,133,224));
        comboBoxDisk.setFont(new Font("Consolas",Font.BOLD,20));
//        comboBoxDisk.add(setNullLabel(600,20));
        comboBox0.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch ((String)e.getItem()) {
                        case "FCFS":
                            OS.DiskScheduelFLAG = 0;
                            break;
                        case "SCAN":
                            OS.MemoryAllocateFLAG = 1;
                            break;
                        case "CSCAN":
                            OS.MemoryAllocateFLAG = 2;
                            break;
                    }
                }
            }
        });

        panelNorth.add(comboBoxDisk);


        panelNorth.setPreferredSize(new Dimension(600,90));
        frame.add(panelNorth, BorderLayout.NORTH);

        //连续分配界面
        panelConstant = new JPanel();
        panelConstant.setBackground(new Color(240,240,240));

        comboBoxCon = new JComboBox();
        comboBoxCon.setBackground(Color.white);
        comboBoxCon.setPreferredSize(new Dimension(400,30));
        comboBoxCon.addItem("FirstFit");
        comboBoxCon.addItem("NextFit");
        comboBoxCon.addItem("BestFit");
        comboBoxCon.addItem("WorstFit");
        comboBoxCon.setForeground(new Color(66,133,224));
        comboBoxCon.setFont(new Font("Consolas",Font.BOLD,20));

        comboBoxCon.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch ((String) e.getItem()) {
                        case "FirstFit":
                            OS.ConstantMemoryAllocateFLAG = 0;
                            break;
                        case "NextFit":
                            OS.ConstantMemoryAllocateFLAG = 1;
                            break;
                        case "BestFit":
                            OS.ConstantMemoryAllocateFLAG = 2;
                            break;
                        case "WorstFit":
                            OS.ConstantMemoryAllocateFLAG = 3;
                            break;
                    }
                }
            }
        });
        panelConstant.add(setNullLabel(600,20));
        panelConstant.add(comboBoxCon);


        //离散分配界面
        panelDiscrete = new JPanel();
        panelDiscrete.setBackground(new Color(240,240,240));

        comboBoxDis = new JComboBox();
        comboBoxDis.setBackground(Color.white);
        comboBoxDis.setPreferredSize(new Dimension(400,30));
        comboBoxDis.addItem("LRU");
        comboBoxDis.addItem("LFU");
        comboBoxDis.setForeground(new Color(66,133,224));
        comboBoxDis.setFont(new Font("Consolas",Font.BOLD,20));

        comboBoxDis.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch ((String) e.getItem()) {
                        case "LRU":
                            OS.VirtualMemoryScheduelFLAG = 0;
                            break;
                        case "LFU":
                            OS.VirtualMemoryScheduelFLAG = 1;
                            break;
                    }
                }
            }
        });
        panelDiscrete.add(setNullLabel(600,20));
        panelDiscrete.add(comboBoxDis);




        tabbedPane = new JTabbedPane();
        tabbedPane.add(CONSTANT_ALLOCATE, panelConstant);
        tabbedPane.add(DISCRETE_ALLOCATE, panelDiscrete);
        frame.add(tabbedPane);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

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
                        case "FirstComeFirstServer":
                            OS.CPUSchedulFLAG = 0;
                            break;
                        case "ShortProcessNext":
                            OS.CPUSchedulFLAG = 1;
                            break;
                        case "HighestPriorityFirst":
                            OS.CPUSchedulFLAG = 2;
                            break;
                        case "SeizeHighestPriorityFirst":
                            OS.CPUSchedulFLAG = 3;
                            break;
                        case "HighestResponseRatioNext":
                            OS.CPUSchedulFLAG = 4;
                            break;
                        case "RoundRobin":
                            OS.CPUSchedulFLAG = 5;
                            break;
                    }
                }
            }
        });
    }

    /**
     * 占位
     * @param width
     * @param height
     * @return
     */
    public JLabel setNullLabel(int width,int height) {
        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(width, height));
        return label;
    }

    public static void main(String[] args) {
        new MemoryStatus();
    }
}
