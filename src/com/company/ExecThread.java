package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//建立一个只读取消息，什么都不做的处理线程类
public class ExecThread extends Thread {
    //获取错误流或输出流信息
    InputStream is;
    //输入流的类型
    String type;

    ExecThread(InputStream is, String type) {
        this.is = is;
        this.type = type;
    }

    public void run() {
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null);
                //System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
