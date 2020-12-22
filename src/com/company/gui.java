package com.company;

import javax.swing.*;

public class gui {

    public static void print() {
        JFrame jFrame = new JFrame();
        Window_play win = new Window_play();
        win.setSize(400, 255);
        win.setVisible(true);
        jFrame.add(win);
        jFrame.setTitle("java play live");
        jFrame.setLocationRelativeTo(null);
        jFrame.setSize(600, 150);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
