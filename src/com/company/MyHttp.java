package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MyHttp {//设置Http请求函数

    public static StringBuilder get(String strurl) {//实现简单的get方法

        try {//把字符串转换为URL对象
            URL url_stream = new URL(strurl);

            //使用方法打开链接,并使用connection接受返回值
            HttpURLConnection connection;
            connection = (HttpURLConnection) url_stream.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            //获取connection输入流,并用is接受response
            InputStream is = connection.getInputStream();

            //转换成字符串
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            //临时变量用以串联结果
            String tmp;
            //转换成json方便使用json库
            StringBuilder builder = new StringBuilder();
            while ((tmp = br.readLine()) != null) {
                builder.append(tmp);
            }
            br.close();
            isr.close();
            is.close();
            connection.disconnect();
            //以上结束了此次连接
            //返回StringBuilder对象
            return builder;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder tmp = new StringBuilder();
        tmp.append("error");
        return tmp;
    }

    public static String SetURLquery(String strurl, String... keyvaluespairs) {
        strurl += "?";
        int len = keyvaluespairs.length;
        for (int i = 0; i < len / 2 - 1; i++) {
            strurl += keyvaluespairs[2 * i] + "=" + keyvaluespairs[2 * i + 1] + "&";
        }
        strurl += keyvaluespairs[len - 2] + "=" + keyvaluespairs[len - 1];
        return strurl;
    }

    public static String AddURLquery(String strurl, String... keyvaluespairs) {
        int len = keyvaluespairs.length;
        strurl += "&";
        for (int i = 0; i < len / 2 - 1; i++) {
            strurl += keyvaluespairs[2 * i] + "=" + keyvaluespairs[2 * i + 1] + "&";
        }
        strurl += keyvaluespairs[len - 2] + "=" + keyvaluespairs[len - 1];
        return strurl;
    }
}
