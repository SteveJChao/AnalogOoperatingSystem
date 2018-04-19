package user_interface;

import Client.Client;
import OS_Kernel.OS_Kernel;
import com.sun.awt.AWTUtilities;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class MainWin implements MouseListener,MouseMotionListener {

    public JFrame frame;

    public JPanel panel;
    public Graphics g;

    private int x;
    private int y;


    public MainWin () {
        new OS_Kernel();
        initUI();
    }

    /**
     * 初始化界面
     */
    public void initUI () {
        frame = new JFrame();
        frame.setSize(new Dimension(630,425));
        frame.setUndecorated(true);
        frame.setOpacity(0.985f);
        panel = new JPanel() {
//            Graphics g = this.getGraphics();

            @Override
            public void paint(Graphics g) {
                super.paint(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(3, 131, 135));
                g2d.fillRect(10,10,405,200);
                try{
                    Image image1 = Toolkit.getDefaultToolkit().getImage(getClass().getResource("task_64.png"));
                    g.drawImage(image1,185,83,64,64,this);
                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }
                g2d.setColor(Color.white);
                g2d.setFont(new Font("Consolas",Font.BOLD,18));
                g2d.drawString("Operating status", 20,190);

                g2d.setColor(new Color(30, 215, 96));
                g2d.fillRect(420,10,200,200);
                try{
                    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("CPU_64.png"));
                    g.drawImage(image,488,83,64,64,this);
                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }
                g2d.setColor(Color.white);
                g2d.drawString("CPU scheduling", 425,190);


                g2d.setColor(new Color(3, 131, 135));
                g2d.fillRect(10,215,200,200);
                try{
                    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("terminal_64.png"));
                    g.drawImage(image,78,283,64,64,this);
                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }
                g2d.setColor(Color.white);
                g2d.drawString("Terminal", 20,395);

                g2d.setColor(new Color(3, 131, 135));
                g2d.fillRect(215,215,200,200);
                try{
                    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("disk_64.png"));
                    g.drawImage(image,283,283,64,64,this);
                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }
                g2d.setColor(Color.white);
                g2d.drawString("Disk System", 225,395);

                g2d.setColor(new Color(196,64,19));
                g2d.fillRect(420,215,200,200);
                try{
                    Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("memory_64.png"));
                    g.drawImage(image,488,283,64,64,this);
                }catch (NullPointerException npe){
                    npe.printStackTrace();
                }
                g2d.setColor(Color.white);
                g2d.drawString("Memory Allocate", 430,395);
            }
        };
        panel.setBackground(new Color(50,50,50));
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
        frame.add(panel);


        frame.setDefaultCloseOperation(3);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        g = panel.getGraphics();
    }


    public static void main(String[] args) {
        new MainWin();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (x >10 && x < 415 && y > 10 && y < 210) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new TaskWinCheck();
                }
            }).start();
        } else if (x >420 && x < 620 && y > 10 && y < 210) {
            new CPURunningStatus();
        } else if ( x >10 && x < 210 && y > 215 && y < 415) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    new Client();
                }
            }).start();
        } else if (x >215 && x < 415 && y > 215 && y < 415) {
            new DiskAndFileWin();
        } else if (x >420 && x < 620 && y > 215 && y < 415) {
            new MemoryStatus();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }
}
