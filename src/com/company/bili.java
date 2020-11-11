package com.company;

import org.json.JSONArray;
import org.json.JSONObject;

public class bili {

    public static long get_roomid(long rmid) {//获取真实直播房间号，因为有时在网页中看到的房间号不一定是真实的

        //将房间号与url组合
        String strforbili = new String("https://api.live.bilibili.com/room/v1/Room/room_init?id=REProomid");
        String strrid = new String().valueOf(rmid);
        strforbili = strforbili.replaceAll("REProomid", strrid);

        StringBuilder builder = new StringBuilder();
        builder = MyHttp.get(strforbili);

        JSONObject jsonrid, jsontmp;
        long code, l_status;
        jsonrid = new JSONObject(builder.toString());
        code = jsonrid.getLong("code");
        //判断get返回值是否正常
        if (code == 0) {
            //先get到临时json文件
            jsontmp = jsonrid.getJSONObject("data");
            l_status = jsontmp.getLong("live_status");
            if (l_status == 1) {
                //拿到真实房间号
                rmid = jsontmp.getLong("room_id");
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

    }//end of get roomid.

    public static String get_real_url(long rmid, long qn, String way) {//获取真实直播地址
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
            jsonstream = new JSONObject(builder.toString());//使用的json对象中间变量，将获取的字符串转为json对象
            long code;//状态码，用以反映获取是否成功
            JSONArray ardurl;//解析需要用到的jsonobject数组
            String anstream;//结果字符串
            code = jsonstream.getLong("code");

            if (code == 0) {//若返回结果正确
                //借助json库解析response
                jsonstream = jsonstream.getJSONObject("data");
                ardurl = jsonstream.getJSONArray("durl");
                jsonstream = ardurl.getJSONObject(3);
                anstream = jsonstream.getString("url");

                return anstream;
            } else {
                return "get stream failed";
            }

        } else if (rmid == -1) {
            return "未开播";
        } else if (rmid == -2) {
            return "房间不存在";
        }

        return "exception";
    }

}
