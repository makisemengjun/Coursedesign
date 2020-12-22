package com.company;

import java.sql.ResultSet;
import java.sql.SQLException;

//用以实现播放直播。
public class player {
    //与bili类相同，rmid为房间号,qn为清晰度,way为获取流地址方式
    private final long rmid;
    private final long qn;
    private final String way;

    player(String UpName, long qn, String way) throws CException {
        this.qn = qn;
        this.way = way;
        long rmid;
        //获取直播流
        try {
            String str_sel = ("select rmid from biliroom where name" +
                    " = \"REPUPNAME\"").replaceAll("REPUPNAME", UpName);
            ResultSet resultSet = MariaDB.select(str_sel);
            boolean next = resultSet.next();
            if (next) {
                rmid = Long.parseLong(resultSet.getString("rmid"));
            } else {
                throw new CException("Query error");

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new CException("Something wrong with sql");

        }
        this.rmid = rmid;
    }

    player(long rmid, long qn, String way) {
        this.rmid = rmid;
        this.qn = qn;
        this.way = way;
    }

    public void play() throws CException {
        bili bili_e = new bili(rmid, qn, way);
        String str_url = bili_e.get_real_url();
        //cmd即播放命令
        String cmd;
        System.out.println(str_url);
        //--no-ytdl是mpv的一个选项，使用ytdl有可能会消耗token使得直播源提早失效
        cmd = "mpv " + str_url + " --no-ytdl";
        try {
            //创建进程用以播放获得的视频流
            Process process_mpv = Runtime.getRuntime().exec(cmd);
            /*
            引入线程处理错误流及输出流信息
            java默认的缓冲区很小，若不处理则很快会造成播放器假死
            处理错误信息
            */
            ExecThread errorMpv = new ExecThread(process_mpv.getErrorStream(), "Error");
            //处理普通输出信息
            ExecThread opMpv = new ExecThread(process_mpv.getInputStream(), "output");
            //启动线程
            errorMpv.start();
            opMpv.start();

            //开始播放视频流
            process_mpv.waitFor();

        } catch (Exception e) {
            throw new CException("mpv call failed\n" +
                    "Please make sure you install mpv and " +
                    "check your environment variable");
        }
    }

}
