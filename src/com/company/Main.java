package com.company;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        /**
        bili tmp = new bili();
        String strmp;
        long trid;
        strmp = bili.get_real_url(26445, 10000, "web");
        System.out.println(strmp);
        */
        try{
            MairaDB.select("select * from ");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
}
