package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicButtonListener;

public class gui {

    public static void print() {
        JFrame jFrame = new JFrame();
        JTabbedPane jTabbedPane = new JTabbedPane();

        bili_panel win_bili = new bili_panel();
        win_bili.setSize(400, 255);
        win_bili.setVisible(true);

        douyu_panel win_douyu = new douyu_panel();
        win_douyu.setSize(400, 255);
        win_douyu.setVisible(true);

        jTabbedPane.add("bilibili", win_bili);
        jTabbedPane.add("douyu", win_douyu);
        jTabbedPane.addChangeListener(new ChangeListener() {
                                          @Override
                                          public void stateChanged(ChangeEvent changeEvent) {
                                              int tmp = jTabbedPane.getSelectedIndex();
                                              jTabbedPane.getSelectedIndex();
                                              switch (tmp) {
                                                  case 0:
                                                      jFrame.setTitle("bilibili");
                                                      break;
                                                  case 1:
                                                      jFrame.setTitle("douyu");
                                                      break;
                                              }
                                          }
                                      }

        );
        jFrame.add(jTabbedPane);

        jFrame.setTitle("bilibili");
        jFrame.setLocationRelativeTo(null);
        jFrame.setSize(600, 150);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
