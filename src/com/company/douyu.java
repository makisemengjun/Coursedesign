package com.company;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class douyu {
    static String did = "10000000000000000000000000001501";//magic number,计算md5用
    String t10, t13, key_id, rid;//数字为位数
    Long rate;
    long err_id;

    douyu(long short_id, long rate) throws CException {
        this.rate = rate;
        Date date = new Date();
        long ltmp = date.getTime();
        t13 = String.valueOf(ltmp);
        t10 = String.valueOf(ltmp / 1000);
        //以下用正则表达式找到房间号
        String tmp_url = "https://m.douyu.com/" + short_id;
        StringBuilder sb_tmp = MyHttp.get(tmp_url);
        String result_get_rid = sb_tmp.toString();
        Pattern p_rid = Pattern.compile("\"rid\":(\\d{1,7})");
        Matcher matcher = p_rid.matcher(result_get_rid);
        if (matcher.find()) {
            String tmp = matcher.group();
            String str_json = "{" + tmp + "}";
            JSONObject json_temp = new JSONObject(str_json);
            rid = String.valueOf(json_temp.getLong("rid"));
        } else {
            throw new CException("房间号错误");
        }
    }

    private String md5(String data) {
        String ans = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data.getBytes());//TODO :: may require utf-8 settings
            BigInteger hash = new BigInteger(1, md5.digest());//符号
            ans = hash.toString(16);
            while (ans.length() < 32) {
                ans = "0" + ans;
            }
            return ans;
        } catch (NoSuchAlgorithmException e) {

        }
        return ans;
    }

    private String get_pc_js() {
        /*
           cdn:主线路ws-h5,备用线路tct-h5
           rate: 1流畅；2高清；3超清；4蓝光4M；0蓝光8M或10M
         */
        String cdn = "ws-h5";
        String res = MyHttp.get("https://www.douyu.com/" + rid).toString();
        Pattern p_js_func = Pattern.compile
                ("(var\\svdwdae325w_64we[\\s\\S]*function ub98484234[\\s\\S]*?)function");
        Matcher m_js_func = p_js_func.matcher(res);
        String result = "", func_ub9;
        if (m_js_func.find()) {
            result = m_js_func.group(1);
        }
        func_ub9 = result.replaceAll("eval.*?;}", "strc;}");
        ScriptEngineManager m = new ScriptEngineManager();
        ScriptEngine engine = m.getEngineByName("js");
        String v, rb, func_sign, params;
        try {
            try {
                engine.eval(func_ub9);
            } catch (Exception e) {

            }
            Invocable inv = (Invocable) engine;
            res = inv.invokeFunction("ub98484234").toString();
            p_js_func = Pattern.compile("v=(\\d+)");
            m_js_func = p_js_func.matcher(res);
            if (m_js_func.find()) {
                v = m_js_func.group(1);
                rb = this.md5(rid + did + t10 + v);
                func_sign = res.replaceAll("return rt;}\\);?",
                        "return rt;}");
                func_sign = func_sign.replace("(function (",
                        "function sign(");
                func_sign = func_sign.replace("CryptoJS.MD5(cb).toString()"
                        , "\"" + rb + "\"");
                engine.eval(func_sign);
                inv = (Invocable) engine;
                params = inv.invokeFunction("sign", rid, did, t10).toString();
                String tmp = "&cdn=RECDN&rate=RERATE";
                tmp = tmp.replace("RECDN", cdn);
                tmp = tmp.replace("RERATE", "0");
                params += tmp;
                String url = "https://www.douyu.com/lapi/live/getH5Play/" + rid + "?" + params;
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPost post = new HttpPost(url);
                CloseableHttpResponse response = client.execute(post);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        response.getEntity().getContent()));
                StringBuilder builder = new StringBuilder();
                while ((tmp = br.readLine()) != null) {
                    builder.append(tmp);
                }
                br.close();
                response.close();
                client.close();
                JSONObject jsonObject = new JSONObject(builder.toString());
                jsonObject = jsonObject.getJSONObject("data");
                tmp = jsonObject.getString("rtmp_live");
                System.out.println(tmp);
                Matcher matcher = Pattern.compile("(\\d{1,7}[0-9a-zA-Z]+)").matcher(tmp);
                if (matcher.find()) {
                    tmp = matcher.group(1);
                }
                return tmp;
            }


        } catch (ScriptException e) {
            System.out.println("script exception");
        } catch (NoSuchMethodException e) {
            System.out.println("code error no such method");
        } catch (IOException e) {
            System.out.println("code error IO");
        }

        return "";
    }

    private void get_pre() throws CException {
        try {
            List<NameValuePair> data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("rid", this.rid));
            data.add(new BasicNameValuePair("did", this.did));
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost("https://playweb.douyucdn.cn/lapi" +
                    "/live/hlsH5Preview/" + this.rid);
            post.setHeader("rid", this.rid);
            post.setHeader("time", this.t13);
            post.setHeader("auth", this.md5(rid + t13));
            post.setEntity(new UrlEncodedFormEntity(data));
            CloseableHttpResponse response = client.execute(post);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            StringBuilder builder = new StringBuilder();
            String tmp, res;
            while ((tmp = br.readLine()) != null) {
                builder.append(tmp);
            }
            br.close();
            response.close();
            client.close();

            res = builder.toString();
            System.out.println(res);
            JSONObject json_res = new JSONObject(res);
            err_id = json_res.getLong("error");
            Boolean json_data_null = false;
            JSONObject json_data = null;
            try {
                json_data = json_res.getJSONObject("data");
            } catch (JSONException e) {
                json_data_null = true;
            }

            key_id = "";
            if (!json_data_null) {
                String rtmp_live = json_data.getString("rtmp_live");
                Pattern p_m3u8 = Pattern.compile("(\\d{1,7}[0-9a-zA-Z]+)" +
                        "_?\\d{0,4}(/playlist|.m3u8)");
                Matcher m_m3u8 = p_m3u8.matcher(rtmp_live);
                if (m_m3u8.find()) {
                    key_id = m_m3u8.group(1);
                }
            }
        } catch (Exception e) {
            throw new CException("error");
        }
    }

    public String get_real_url() throws CException {
        this.get_pre();
        if (err_id == 0) {
            ;
        }
        if (err_id == 102) {
            throw new CException("房间不存在");
        } else if (err_id == 104) {
            throw new CException("主播未开播");
        } else {
            //throw new CException("暂不支持此类型");
            key_id = this.get_pc_js();
        }

        return "http://tx2play1.douyucdn.cn/live/" + key_id + ".flv?uuid=";

    }
}
