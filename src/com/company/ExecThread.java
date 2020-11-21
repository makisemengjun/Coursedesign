package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ExecThread extends Thread{
    InputStream is;
    String type;
    ExecThread(InputStream is,String type){
        this.is=is;
        this.type=type;
    }
    public void run(){
        try {
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line = null;
            while ((line = br.readLine()) != null)
                System.out.println(line);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
