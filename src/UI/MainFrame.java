package UI;

import method.Solution;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private int num;
    private int[][] magic;
    private Thread t;
    private remakeFrame remakeFrame;
    private reSizeFrame resizeFrame;
    private boolean suspend = true;
    private JPanel panel = new JPanel ( );
    private JMenuBar jmb = new JMenuBar ( );
    private Font font = new Font (Font.SERIF, Font.PLAIN, 30);

    public MainFrame(int num) {
        this.num = num;//魔方阵的阶数
        setBounds (100, 100, 400, 400);
        setLocationRelativeTo (null);//默认位于中心
        setExtendedState (MAXIMIZED_BOTH);//窗口最大化
        setDefaultCloseOperation (EXIT_ON_CLOSE);

        JMenu jm = new JMenu ("选项");
        JMenuItem remake = new JMenuItem ("重新生成");
        JMenuItem reSize = new JMenuItem ("设置字体大小");
        remake.addActionListener (new ActionListener ( ) {
            @Override
            public void actionPerformed(ActionEvent e) {
                   if(remakeFrame == null) remakeFrame =  new remakeFrame ();
                   else{
                       remakeFrame.setVisible(true);
                   }
            }
        });
        reSize.addActionListener (new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                   if(resizeFrame == null) resizeFrame = new reSizeFrame ();
                   else{
                       resizeFrame.setVisible(true);
                   }
            }
        });
        jm.add (remake);
        jm.add(reSize);
        jmb.add(jm);
        this.setJMenuBar (jmb);

        t = new PanelThread();
        t.start();
        setContentPane (panel);
        setVisible (true);
    }


    class remakeFrame extends JFrame {
        public remakeFrame() throws HeadlessException {
            setTitle ("重新设置魔方阵的阶数");
            setSize(300,100);
            setLayout(new FlowLayout (  ));
            setResizable (false);
            setLocationRelativeTo (null);

            JLabel label = new JLabel ("魔方阵的阶数：");
            JTextField jtf = new JTextField ( String.valueOf (num),5);
            JButton jb = new JButton("确认");
            jb.addActionListener (new ActionListener ( ) {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s= jtf.getText ();
                    if(!isNum(s)){
                        JOptionPane.showMessageDialog (null,"请输入数字","错误",JOptionPane.ERROR_MESSAGE);
                    }else{
                        if(Integer.parseInt (s)<=2){
                            JOptionPane.showMessageDialog (null,"请输入一个大于2的数字","错误",JOptionPane.ERROR_MESSAGE);
                        }else{
                            num = Integer.parseInt (s);
                            synchronized(t){
                                t.notify();
                            }
                            suspend = false;
                            setVisible (false);
                        }
                    }
                }
            });
            add(label);
            add(jtf);
            add(jb);
            setVisible (true);

        }
    }

    class reSizeFrame extends JFrame {
        public reSizeFrame() throws HeadlessException {
            setTitle("修改字体大小");
            setSize(300,100);
            setLayout(new FlowLayout (  ));
            setResizable (false);
            setLocationRelativeTo (null);
            JLabel label = new JLabel ("字体大小：");
            JTextField jtf = new JTextField ( String.valueOf(font.getSize()),5);
            JButton jb = new JButton("确认");
            jb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int size = Integer.parseInt(jtf.getText());
                    if(size<0){
                        JOptionPane.showMessageDialog(null,"请输入一个正整数","错误",JOptionPane.ERROR_MESSAGE);
                    }else{
                        font = new Font(Font.SERIF, Font.PLAIN,size);
                        synchronized(t){
                            t.notify();
                        }
                        suspend = false;
                        setVisible(false);
                    }
                }
            });
            add(label);
            add(jtf);
            add(jb);
            setVisible(true);
        }
    }


    public boolean isNum(String s){
        for(int i=0;i<s.length ();i++){
            if(!Character.isDigit (s.charAt (i))){
                return false;
            }
        }
        return true;
    }


    class PanelThread extends Thread{
        @Override
        public void run() {
            try {
                while(true){
                    MainFrame.this.setTitle (num + "阶魔方阵");
                    magic = new int[num][num];
                    Solution.solution (num, magic);
                    panel.removeAll();
                    panel.setLayout (new GridLayout (num, num));
                    for (int i = 0; i < num; i++)
                        for (int j = 0; j < num; j++) {
                            JLabel label = new JLabel (String.valueOf (magic[i][j]), JLabel.CENTER);
                            label.setOpaque (true);
                            label.setBackground (Color.WHITE);
                            label.setBorder (BorderFactory.createLineBorder (Color.BLACK));
                            label.setFont (font);
                            panel.add (label);
                        }
                    synchronized(t){
                        setContentPane (panel);
                        if(suspend) t.wait();
                    }
                    suspend = true;

                }
            } catch (Exception e) {
                e.printStackTrace ( );
            }
        }
    }
}
