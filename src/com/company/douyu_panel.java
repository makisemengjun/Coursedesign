package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

//继承自JPanel的底层容器，实现了事件监听的接口
public class douyu_panel extends JPanel implements ActionListener {
    //初始化清晰度对应的qn值映射
    public Map<Integer, Long> qn_map;
    //name即房间名字，source即流线路，qn即清晰度
    JLabel JL_name, JL_source, JL_qn;
    //两个下拉选框
    JComboBox<String> JCB_source, JCB_qn;
    //一个播放按钮
    JButton JBT_play;
    //输入房间号的文本框
    JTextField room_name;
    JPanel jp;

    {
        qn_map = new HashMap<>();
        qn_map.put(0, 1L);
        qn_map.put(1, 0L);
        qn_map.put(2, 4L);
        qn_map.put(3, 3L);
        qn_map.put(4, 2L);
        qn_map.put(5, 1L);
    }

    douyu_panel() {
        //将初始化任务分为了几个函数
        init_room();
        init_qn();
        init_source();
        init_button();
        init_jp();
    }

    //房间号选项
    private void init_room() {
        //输入房间信息
        JL_name = new JLabel("直播间名");
        room_name = new JTextField(15);
    }

    //清晰度选项
    private void init_qn() {
        //清晰度的选项
        JL_qn = new JLabel("清晰度");
        JCB_qn = new JComboBox<>();
        //1流畅；2高清；3超清；4蓝光4M；0蓝光8M或10M
        JCB_qn.addItem("--请选择--");
        JCB_qn.addItem("蓝光8M");
        JCB_qn.addItem("蓝光4M");
        JCB_qn.addItem("超清");
        JCB_qn.addItem("高清");
        JCB_qn.addItem("流畅");
    }

    //源格式选项
    private void init_source() {
        //视频流的线路
        JL_source = new JLabel("视频流线路");
        JCB_source = new JComboBox<>();
        JCB_source.addItem("--请选择--");
        JCB_source.addItem("主线路");
        JCB_source.addItem("备用线路");
    }

    //按钮
    private void init_button() {
        //播放按钮
        JBT_play = new JButton("播放");
        //添加事件监听器，按下按钮读取前几个控件的选项并开始播放
        JBT_play.addActionListener(this);
    }

    //将上述组件添加到JPanel上
    private void init_jp() {
        //初始化容器JPanel
        jp = new JPanel();
        jp.setBackground(Color.white);
        //将上面所有的控件添加到JPanel，FlowOut布局
        jp.add(JL_name);
        jp.add(room_name);
        jp.add(JL_qn);
        jp.add(JCB_qn);
        jp.add(JL_source);
        jp.add(JCB_source);
        jp.add(JBT_play);
        //jp.add(opArea);
        //将jp添加到window_play这个容器才能显示出来
        add(jp);
    }

    public void actionPerformed(ActionEvent e) {//实现接口，用以开始播放

        //获得清晰度
        Long qn = qn_map.get(JCB_qn.getSelectedIndex());
        //获得流格式
        String source_way = "ws-h5";
        switch (JCB_source.getSelectedIndex()) {
            case 1:
                break;
            case 2:
                source_way = "tct-h5";
        }

        //str_rmid是主播名
        String str_rmid = room_name.getText();
        //long_rmid是房间号
        long long_rmid = -1L;
        //rm_is_str用以判断输入的是主播名称还是房间号
        boolean rm_is_str = false;
        //调用player类，使用不同的构造函数
        //这里的健壮性可能需要测试
        player p;

        //检测输入的是名称还是数字
        try {
            long_rmid = Long.parseLong(str_rmid);

        } catch (NumberFormatException parseE) {
            rm_is_str = true;
        }

        try {
            if (rm_is_str) {//调用不同的构造函数，应该有更漂亮的写法
                p = new player(str_rmid, qn, source_way, "douyu_room");
            } else {
                p = new player(long_rmid, qn, source_way, "douyu_room");
            }
            //开始播放
            p.play();
        } catch (CException playE) {
            //处理异常，告诉使用者出错信息
            String msg = playE.getCMessage();
            JOptionPane.showMessageDialog
                    (jp, msg, "Error", JOptionPane.INFORMATION_MESSAGE);

        }

    }

}