package demo;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * source code from 《java核心技术 卷1 基础知识》 P326
 */
public class JCheckBoxDemo extends JFrame{

    int DEFAULT_WIDTH = 600;
    int DEFAULT_HEIGHT = 400;
    private JLabel label;
    private JCheckBox boldCheckbox;
    private JCheckBox italicCheckbox;
    private static final int FONTSIZE = 12;

    public JCheckBoxDemo(){
        setTitle("JCheckBoxDemo");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        //添加label
        label = new JLabel("The quick brown fox jumps over the lazy dog.");
        label.setFont(new Font("Serif",Font.PLAIN, FONTSIZE));
        add(label, BorderLayout.CENTER);

        //构造一个监听器，响应checkBox事件
        ActionListener actionListener = new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                int mode = 0;
                if(boldCheckbox.isSelected()) mode += Font.BOLD;
                if(italicCheckbox.isSelected()) mode += Font.ITALIC;
                label.setFont(new Font("Serif", mode, FONTSIZE));
            }
        };

        //添加buttonPanel,它包含2个checkBox
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);

        //为buttonPanel添加2个checkBox
        boldCheckbox = new JCheckBox("bold");
        boldCheckbox.addActionListener(actionListener);
        buttonPanel.add(boldCheckbox);

        italicCheckbox = new JCheckBox("italic");
        italicCheckbox.addActionListener(actionListener);
        buttonPanel.add(italicCheckbox);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //创建窗体并指定标题
        JCheckBoxDemo frame = new JCheckBoxDemo();
        //关闭窗体后退出程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //自动适配所有控件大小
        //frame.pack();
        //设置窗体位置在屏幕中央
        frame.setLocationRelativeTo(null);
        //显示窗体
        frame.setVisible(true);
    }

}
