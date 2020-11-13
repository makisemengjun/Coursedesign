package com.company;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class gui {//用以图形实现用户交互界面
    public static void paint() {
        JFrame window_f = new JFrame("Stream");
        window_f.setBounds(300, 100, 400, 200);
        JPanel jp = new JPanel();
        JLabel jl = new JLabel("what I want");
        jp.setBackground(Color.white);
        jp.add(jl);
        window_f.add(jp);
        window_f.setVisible(true);
        //try { Class.forName("com.    "};
    }
}