package com.youme.talktest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class test {
    public static void main(String args[]) {
        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime);
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentTime);
        System.out.println(date);
        System.out.println("结束时间："+df.format(date));
    }
}
