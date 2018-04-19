package user_interface;

import A_General.OS;
import DiskManagement.VectorMap;
import DiskManagement.iNode;
import FileSystem.Dentry;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class DiskAndFileWin {
    public JFrame frame;
    public JPanel panelWest;
    public JPanel panelEast;
    public JPanel panelE;
    public JPanel panelW;

    public JTextField textField;
    public JButton buttonSearch;
    public JButton butRefresh;

    public Graphics gWest;



    int total_num;
    JLabel[] labels_pool;
    static int cursor = 1;
    int x_current = 50;
    static int y_current = 20;

    public DiskAndFileWin() {
        initUI();
        initPanelEast();
        onButtClick(buttonSearch);
        onButtClick(butRefresh);
    }

    public void initUI() {
        frame = new JFrame();
        frame.setSize(new Dimension(1440,900));

        panelE = new JPanel();
        panelE.setBackground(new Color(210,210,210));
        panelE.setPreferredSize(new Dimension(540,900));
        frame.add(panelE, BorderLayout.CENTER);

        panelW = new JPanel();
        panelW.setBackground(new Color(210,210,210));
        panelW.setPreferredSize(new Dimension(900,900));
        frame.add(panelW, BorderLayout.WEST);

        panelWest = new JPanel() {
            Graphics g = this.getGraphics();

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                //画20*20的阵列
                g.setColor(new Color(217,217,217));
                for(int i = 0; i < 20; i++) {
                    for (int j = 1; j < 20; j++) {
                        g.fillRect(20 + i * (42), 80 + (j * 30), 37, 25);
                    }
                }
                //System文件
                g.setColor(new Color (52,168,83));
                for (int j = 0; j < 20; j++) {
                    g.fillRect(20 + j * (42), 80, 37, 25);
                }

                //绘制实际使用情况
                g.setColor(new Color (100,100,100));
                VectorMap vectorMap = OS.superBlock.vectorMap;
                boolean[][] map = vectorMap.map;
                for (int i = 1; i < map.length; i++) {
                    for (int j = 0; j < map[0].length; j++) {
                        if (map[i][j]) {
                            g.fillRect(20 + j * 42, 80 + i * 30, 37, 25);
                        }
                    }
                }
            }
        };
        panelWest.setPreferredSize(new Dimension(880,830));
        panelWest.setBackground(new Color(238,238,238));
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(406,30));
        textField.setBackground(new Color(211,211,211));
        textField.setBorder(new EmptyBorder(0,0,0,0));
        textField.setForeground(new Color(66,133,224));
        textField.setFont(new Font("Consolas",Font.BOLD,22));
        panelWest.add(setNullLabel(880, 10));
        panelWest.add(textField);

        buttonSearch = new JButton("Search");
        buttonSearch.setPreferredSize(new Dimension(210,30));
        buttonSearch.setBackground(new Color(66,133,244));
        buttonSearch.setForeground(Color.white);
        buttonSearch.setFont(new Font("Consolas",Font.BOLD,25));
        buttonSearch.setFocusPainted(false);
        buttonSearch.setBorder(new EmptyBorder(0,0,0,0));
        panelWest.add(buttonSearch);

        butRefresh = new JButton("Refresh");
        butRefresh.setPreferredSize(new Dimension(210,30));
        butRefresh.setBackground(new Color(66,133,244));
        butRefresh.setForeground(Color.white);
        butRefresh.setFont(new Font("Consolas",Font.BOLD,25));
        butRefresh.setFocusPainted(false);
        butRefresh.setBorder(new EmptyBorder(0,0,0,0));
        panelWest.add(butRefresh);


        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(880, 5));
        panelW.add(label);
        panelW.add(panelWest);

        panelEast = new JPanel();
        panelEast.setBackground(new Color(238,238,238));
        panelEast.setPreferredSize(new Dimension(494,830));
        JLabel label1 = new JLabel();
        label1.setPreferredSize(new Dimension(540, 5));
        panelE.add(label1);
        panelE.add(panelEast);




        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        gWest = panelWest.getGraphics();
    }

    public void printNode(Dentry root,int x_current) {

        y_current += 20;
        labels_pool[cursor] = new JLabel(root.dirName);
        labels_pool[cursor].setSize(200,20);
        labels_pool[cursor].setLocation(x_current, y_current);
        cursor++;

        if(root.dirContains.size() <= 0)
            return;


        for(String fileName:root.dirContains.keySet())
        {
            printNode(root.dirContains.get(fileName), x_current+20);
        }
    }

    public void initPanelEast() {
        cursor = 1;
         x_current = 50;
          y_current = 20;
        total_num = Dentry.getTotalDirNum(OS.ROOT)+Dentry.getTotalFileNum(OS.ROOT);

        labels_pool = new JLabel[total_num];

        labels_pool[0] = new JLabel(OS.ROOT.dirName);
        labels_pool[0].setSize(200,20);
        labels_pool[0].setLocation(x_current, y_current);
        panelEast.setLayout(null);


        for(String fileName:OS.ROOT.dirContains.keySet())
        {
            printNode(OS.ROOT.dirContains.get(fileName), x_current+20);

        }





        for(int i=0;i<total_num;i++)
        {
            labels_pool[i].setPreferredSize(new Dimension(200,30));
            labels_pool[i].setForeground(new Color(66,133,244));
            labels_pool[i].setFont(new Font("Consolas",0,20));
            panelEast.add(labels_pool[i]);
        }

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

    /**
     * 按钮点击事件
     * @param button
     */
    public void onButtClick(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals("Search")) {
                    try {
                        String path = getPath();
                        System.out.println(path);
                        Dentry dentry = Dentry.searchFile(path);
                        if (dentry != null) {
                            if (dentry.isDir) {
                                JOptionPane.showMessageDialog(null, "What you search is a folder.", "Folder", JOptionPane.DEFAULT_OPTION);
                            } else {
                                iNode iNode = dentry.inode;
                                Vector<Integer> vector = iNode.diskTable;
                                int size = OS.superBlock.vectorMap.size;
                                gWest.setColor(new Color(254, 153, 6));
                                for (int n = 0; n < vector.size(); n++) {
                                    int num = vector.get(n);
                                    int i = num / size;
                                    int j = num % size;
                                    gWest.fillRect(20 + j * 42, 80 + i * 30, 37, 25);
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Please input a correct path.", "Wrong path", JOptionPane.DEFAULT_OPTION);
                        }

                    } catch (Exception ex) {
                        System.out.println("please input an Integer");
                    }
                } else if (e.getActionCommand().equals("Refresh")) {
                    panelWest.repaint();
                }
            }
        });
    }

    /**
     * 获取PID
     * @return
     */
    public String getPath() throws Exception {
        if(textField == null) return "";
        String path = textField.getText();
        return path;
    }


    public static void main(String[] args) {
        new DiskAndFileWin();
    }




}
