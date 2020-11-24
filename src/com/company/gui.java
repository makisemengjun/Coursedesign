package com.company;

import javax.swing.*;

public class gui {

    public static void print() {
        Window_play win = new Window_play();
        win.setTitle("BiliBili直播");
        win.setLocationRelativeTo(null);
        win.setSize(400, 255);
        win.setVisible(true);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
