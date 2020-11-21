package com.company;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

//用以实现播放直播。
//最顶层的类，直接间接调用所有其他类。
public class player {
    public static int palyer_main (String[] args){
        long rmid=-1;
        //get gui information
        try{
            ResultSet resultSet;
            resultSet = MariaDB.select("select rmid from biliroom where name = \"roujuan\"");
            resultSet.next();
            rmid = Long.parseLong(resultSet.getString("rmid"));
            System.out.println(rmid);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        if (rmid!= -1) {
            String strurl = bili.get_real_url(rmid, 10000, "web");
            String[] cmd;
            System.out.println(strurl);
            cmd = new String[]{"mpv",strurl,"--no-ytdl"};
            try {
                Process process_mpv = Runtime.getRuntime().exec("mpv "+strurl+" --no-ytdl");
                //引入线程处理错误流及输出流信息，原因见课程设计文档
                ExecThread errorMpv = new ExecThread(process_mpv.getErrorStream(),"Error");
                ExecThread opMpv = new ExecThread(process_mpv.getInputStream(),"output");
                errorMpv.start();
                opMpv.start();
                int exit_val=process_mpv.waitFor();

            }
            catch (Exception e){
                System.out.println("mpv call failed");

            }
        }
        return 0;
    }
}
