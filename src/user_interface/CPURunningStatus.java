package user_interface;

import A_General.OS;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CPURunningStatus {
    public JFrame frame;
    public JPanel panel;
    public JComboBox comboBox;
    public JComboBox comboBox2;

    public CPURunningStatus() {
        initUI();
    }

    public void initUI() {
        frame = new JFrame();
        frame.setSize(new Dimension(600,300));
        panel = new JPanel() {
            Graphics g = this.getGraphics();

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setFont(new Font("Consolas",0,18));
                g2d.setColor(Color.black);
                //提示
                g2d.drawString("Here you can choose the CPU Scheduling algorithm", 40,20);
                g2d.drawString("Here you can choose the Job Scheduling algorithm", 40,100);
            }
        };
        panel.setBackground(Color.white);



        comboBox = new JComboBox();
        comboBox.setBackground(Color.white);
        comboBox.setPreferredSize(new Dimension(500,30));
        comboBox.addItem("RoundRobin");
        comboBox.addItem("ShortProcessNext");
        comboBox.addItem("HighestPriorityFirst");
        comboBox.addItem("SeizeHighestPriorityFirst");
        comboBox.addItem("HighestResponseRatioNext");
        comboBox.addItem("FirstComeFirstServer");
        comboBox.setForeground(new Color(66,133,224));
        comboBox.setFont(new Font("Consolas",Font.BOLD,20));
        panel.add(setNullLabel(800,27));
        onComboBoxClick1();
        panel.add(comboBox);

        comboBox2 = new JComboBox();
        comboBox2.setBackground(Color.white);
        comboBox2.setPreferredSize(new Dimension(500,30));
        comboBox2.addItem("FirstComeFirstServer");
        comboBox2.addItem("ShortProcessNext");
        comboBox2.addItem("HighestPriorityFirst");
        comboBox2.addItem("HighestResponseRatioNext");
        comboBox2.setForeground(new Color(66,133,224));
        comboBox2.setFont(new Font("Consolas",Font.BOLD,20));
        panel.add(setNullLabel(800,30));
        onComboBoxClick2();
        panel.add(comboBox2);

        frame.add(panel);
//        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * comboBox点击事件
     */
    public void onComboBoxClick1() {
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

    public void onComboBoxClick2() {
        comboBox2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    switch ((String)e.getItem()) {
                        case "FirstComeFirstServer":
                            OS.JobSchedulFLAG = 0;
                            break;
                        case "ShortProcessNext":
                            OS.JobSchedulFLAG = 1;
                            break;
                        case "HighestPriorityFirst":
                            OS.JobSchedulFLAG = 2;
                            break;
                        case "HighestResponseRatioNext":
                            OS.JobSchedulFLAG = 3;
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
        new CPURunningStatus();

    }

}
