package com.company;

import org.json.JSONArray;
import org.json.JSONObject;
//这是用来获取给定房间号的类

public class bili {

    //获取真实直播房间号，因为在网页中看到的房间号不一定是真实的
    public static long get_roomid(long rmid) {

        //将房间号与url组合
        String strforbili = "https://api.live.bilibili.com/room/v1/Room/room_init" +
                "?id=REProomid";
        String strrid = String.valueOf(rmid);
        strforbili = strforbili.replaceAll("REProomid", strrid);

        StringBuilder builder = new StringBuilder();
        builder = MyHttp.get(strforbili);

        JSONObject json_rid, json_tmp;
        long code, l_status;
        json_rid = new JSONObject(builder.toString());
        code = json_rid.getLong("code");
        //判断get返回值是否正常
        if (code == 0) {
            //先get到临时json文件
            json_tmp = json_rid.getJSONObject("data");
            l_status = json_tmp.getLong("live_status");
            if (l_status == 1) {
                //拿到真实房间号
                rmid = json_tmp.getLong("room_id");
                //System.out.print(rmid);
                return rmid;
            } else {
                //意即未开播
                return -1;
            }
        } else {
            //意即房间不存在
            return -2;
        }

    }

    public static String get_real_url(long rmid, long qn, String way) throws CException {//获取真实直播地址
        //rmid即房间号，qn为清晰度，way是获取flv还是html5源
        String f_url = "https://api.live.bilibili.com/xlive/web-room/v1/playUrl/playUrl";
        long rid = bili.get_roomid(rmid);//获取真实房间号
        if (rmid > 0) {//若房间号不出错

            //构造查询URL，将真实房间号以及清晰度插入进查询字符串
            f_url = MyHttp.SetURLquery(f_url, "cid", String.valueOf(rid));
            f_url = MyHttp.AddURLquery(f_url, "qn", String.valueOf(qn), "platform", way, "https_url_req", "1", "ptype", "16");

            StringBuilder builder = MyHttp.get(f_url);

            //定义一些中间变量
            JSONObject jsonstream;
            //使用的json对象中间变量，将获取的字符串转为json对象
            jsonstream = new JSONObject(builder.toString());
            //状态码，用以反映获取是否成功
            long code;
            //解析需要用到的JsonObject数组
            JSONArray ardurl;
            //结果字符串
            String anstream;
            code = jsonstream.getLong("code");

            if (code == 0) {//若返回结果正确
                //借助json库解析response
                jsonstream = jsonstream.getJSONObject("data");
                ardurl = jsonstream.getJSONArray("durl");
                jsonstream = ardurl.getJSONObject(3);
                anstream = jsonstream.getString("url");

                return anstream;
            } else {
                throw new CException("get stream failed");

            }

        } else if (rmid == -1) {
            throw new CException("主播没有开播");

        } else if (rmid == -2) {
            throw new CException("房间不存在");

        }
        throw new CException("Unknown error");

    }

}
