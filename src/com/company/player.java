package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;

//用以实现播放直播。
public class player {
    //与bili类相同，rmid为房间号,qn为清晰度,way为获取流地址方式
    private long rmid;
    private long qn;
    private String way;

    player(String UpName, long qn, String way) {
        this.qn = qn;
        this.way = way;
        long rmid = -1;
        //获取直播流
        try {
            ResultSet resultSet;
            String str_sel = "select rmid from biliroom where name = \"REPUPNAME\"".replaceAll("REPUPNAME", UpName);
            resultSet = MariaDB.select(str_sel);
            boolean next = resultSet.next();
            if (next) {
                rmid = Long.parseLong(resultSet.getString("rmid"));
            } else {
                System.out.println("查询出错");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Something wrong with sql");
        }
        this.rmid = rmid;
    }

    player(long rmid, long qn, String way) {
        this.rmid = rmid;
        this.qn = qn;
        this.way = way;
    }

    public void play() {
        if (rmid != -1) {
            String str_url = bili.get_real_url(rmid, qn, way);
            //cmd即播放命令
            String cmd;
            System.out.println(str_url);
            //--no-ytdl是mpv的一个选项，使用ytdl有可能会消耗token使得直播源提早失效
            cmd = "mpv " + str_url + " --no-ytdl";
            try {
                //创建进程用以播放获得的视频流
                Process process_mpv = Runtime.getRuntime().exec(cmd);
                //引入线程处理错误流及输出流信息，原因见课程设计文档
                //一个处理错误信息
                ExecThread errorMpv = new ExecThread(process_mpv.getErrorStream(), "Error");
                //处理普通输出信息
                ExecThread opMpv = new ExecThread(process_mpv.getInputStream(), "output");
                //启动线程
                errorMpv.start();
                opMpv.start();
                //开始播放视频流
                process_mpv.waitFor();

            } catch (Exception e) {
                System.out.println("mpv call failed");
            }
        }
    }

}
