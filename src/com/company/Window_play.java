package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class Window_play extends JFrame implements ActionListener {
    JLabel JL_name, JL_source, JL_qn;//name即房间名字，source即流格式，qn即清晰度
    JComboBox JCB_source, JCB_qn;
    JButton JBT_play;
    JTextField room_name;
    JTextArea opArea = new JTextArea("Out put Area", 5, 20);
    Map<Integer, Long> qn_map;

    {
        qn_map = new HashMap<Integer, Long>();
        qn_map.put(1, 10000L);
        qn_map.put(2, 400L);
        qn_map.put(3, 250L);
        qn_map.put(4, 150L);
    }

    Window_play() {
        init();
        setBounds(300, 100, 400, 225);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void init() {
        //输入房间信息
        JL_name = new JLabel("直播间名");
        room_name = new JTextField(15);

        //清晰度的选项
        JL_qn = new JLabel("清晰度");
        JCB_qn = new JComboBox();
        JCB_qn.addItem("--请选择--");
        JCB_qn.addItem("原画");//qn=10000
        JCB_qn.addItem("蓝光");//qn=400
        JCB_qn.addItem("超清");//qn=250
        JCB_qn.addItem("高清");//qn=150

        //选择视频流格式
        JL_source = new JLabel("视频流格式");
        JCB_source = new JComboBox();
        JCB_source.addItem("--请选择--");
        JCB_source.addItem("web");
        JCB_source.addItem("flv");

        JBT_play = new JButton("播放");
        JBT_play.addActionListener(this);
        JPanel jp = new JPanel();
        jp.setBackground(Color.white);
        jp.add(JL_name);
        jp.add(room_name);
        jp.add(JL_qn);
        jp.add(JCB_qn);
        jp.add(JL_source);
        jp.add(JCB_source);
        jp.add(JBT_play);
        jp.add(opArea);

        add(jp);
    }

    public void actionPerformed(ActionEvent e) {
        //获得清晰度
        Long qn = qn_map.get(JCB_qn.getSelectedIndex());
        //获得流格式
        String source_way = new String();
        switch (JCB_source.getSelectedIndex()) {
            case 1:
                source_way = "web";
                break;
            case 2:
                source_way = "flv";
        }
        String str_rmid = room_name.getText();
        long long_rmid = -1L;
        boolean rm_is_str = false;
        player p;
        try {
            long_rmid = Long.parseLong(str_rmid);
            rm_is_str = false;
        } catch (NumberFormatException parseE) {
            rm_is_str = true;
        }
        if (rm_is_str) {
            p = new player(str_rmid, qn, source_way);
        } else {
            p = new player(long_rmid, qn, source_way);
        }
        p.play();
    }


}