package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialFrame extends JFrame {

    public InitialFrame() throws HeadlessException {
        setTitle("魔方阵生成器");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(300,100);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("魔方阵的阶数：");
        JTextField jtf = new JTextField("",5);
        JButton jb = new JButton("生成");
        jb.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String num_s = jtf.getText();
                int num = Integer.parseInt(num_s);
                if(num==2){
                    JOptionPane.showMessageDialog(null,"不存在这个阶数的魔方阵","错误",JOptionPane.ERROR_MESSAGE);
                }else{
                    setVisible(false);
                    new MainFrame(num);
                    JOptionPane.showMessageDialog(null,num+"阶魔方阵生成完成","提示",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(label,BorderLayout.CENTER);
        panel.add(jtf,BorderLayout.CENTER);
        panel.add(jb,BorderLayout.CENTER);
        add(panel);

        setResizable (false);
        setLocationRelativeTo(null);//令窗体居中
        setVisible(true);
    }
}
