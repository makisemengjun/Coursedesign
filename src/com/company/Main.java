package com.company;

public class Main {
    public static void main(String[] args) {
        //gui.print();
        try {
            douyu a = new douyu(9190899, 0);
            System.out.println(a.get_real_url());
        }
        catch (CException e){
            ;
        }
    }

}
