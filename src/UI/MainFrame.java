package UI;

import method.Solution;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private int num;
    private int[][] magic;

    public MainFrame(int num) {
        this.num = num;//魔方阵的阶数
        magic = new int[num][num];
        Solution.solution (num,magic);
        setBounds (100,100,400,400);
        setLocationRelativeTo(null);//默认位于中心
        setExtendedState (MAXIMIZED_BOTH);//窗口最大化
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setTitle(num+"阶魔方阵");
        setLayout(new GridLayout (num,num));
        for(int i  = 0;i<num;i++)
            for(int j= 0;j<num;j++){
            JLabel label = new JLabel(String.valueOf(magic[i][j]));
            label.setFont(new Font(Font.SERIF,Font.PLAIN,30));
            add(label);
        }
        setVisible(true);
    }
    
}
