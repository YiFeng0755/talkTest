package com.youme.talktest;

import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IMtestWY {
    public static void main (String args []) throws MalformedURLException{
        ThreadManager tm = new ThreadManager();
        HandleExcel he = new HandleExcel();
        User1 u1 = new User1("Android","8.1.0","121c03b","com.netease.nim.demo",".main.activity.WelcomeActivity","121c03b","http://127.0.0.1:4723/wd/hub",tm,he);
        Thread t1 = new Thread(u1,"User1");
        t1.start();

        User2 u2 = new User2("Android","7.1.1","b8577a94","com.netease.nim.demo",".main.activity.WelcomeActivity","b8577a94","http://127.0.0.1:4724/wd/hub",tm,he);
        Thread t2 = new Thread(u2,"User2");
        t2.start();
    }
}

//黑鲨
class User1 extends BaseDriver implements Runnable {
    User1(String platformName,String platformVersion,String deviceName,String appPackage,String appActivity,String udid,String url,ThreadManager tm,HandleExcel he) throws MalformedURLException {
        super(platformName,platformVersion,deviceName,appPackage,appActivity,udid,url,tm,he);
    }

    @Override
    public void run() {
//        waitElementById("com.netease.nim.demo:id/edit_login_account",4000).sendKeys("ym234");
//        waitElementById("com.netease.nim.demo:id/edit_login_password",4000).sendKeys("123456");
//        waitElementById("com.netease.nim.demo:id/action_bar_right_clickable_textview",4000).click();
//        List<WebElement> eles0 = driver.findElementsById("com.netease.nim.demo:id/tab_title_label");
//        System.out.println(eles0);
//        WebElement ele00 = waitElementById("com.netease.nim.demo:id/tv_nickname",4000);
//        System.out.println(ele00);
//        ele00.click();
        List<WebElement> eles = waitElementsById("com.netease.nim.demo:id/tv_nickname",4000);
        System.out.println(eles);
        WebElement e1 = null;
        for (int i=0;i<eles.toArray().length;i++){
            System.out.println(eles.get(i).getAttribute("text"));
            if (eles.get(i).getAttribute("text").equals("ert")){
                e1 = eles.get(i);
            }
        }
        e1.click();
//        List<WebElement> eles2 = waitElementsById("com.netease.nim.demo:id/contacts_item_name",4000);
//        eles2.get(1).click();
//        waitElementById("com.netease.nim.demo:id/begin_chat",4000).click();
        ThreadManager.A1Await();
        WebElement sendText = waitElementById("com.netease.nim.demo:id/editTextMessage",4000);
        sendText.click();
        Process p = null;
        for (int i=0;i<1000;i++){
            String cmd = "cmd /c adb -s 121c03b shell input text "+Integer.toString(i);
            try {
                p = Runtime.getRuntime().exec(cmd);
            }catch(IOException e){
                e.printStackTrace();
            }
            waitElementById("com.netease.nim.demo:id/buttonSendMessage",4000).click();
            ThreadManager.A2Signal();
            if(i!=999){
                ThreadManager.A1Await();
            }
        }
        p.destroy();
    }
}

//一加
class User2 extends BaseDriver implements Runnable {
    User2(String platformName,String platformVersion,String deviceName,String appPackage,String appActivity,String udid,String url,ThreadManager tm,HandleExcel he) throws MalformedURLException {
        super(platformName,platformVersion,deviceName,appPackage,appActivity,udid,url,tm,he);
    }

    @Override
    public void run() {
//        waitElementById("com.netease.nim.demo:id/edit_login_account",4000).sendKeys("ym235");
//        waitElementById("com.netease.nim.demo:id/edit_login_password",4000).sendKeys("123456");
//        waitElementById("com.netease.nim.demo:id/action_bar_right_clickable_textview",4000).click();
        List<WebElement> eles = waitElementsById("com.netease.nim.demo:id/tv_nickname",4000);
        System.out.println(eles);
        WebElement e1 = null;
        for (int i=0;i<eles.toArray().length;i++){
            System.out.println(eles.get(i).getAttribute("text"));
            if (eles.get(i).getAttribute("text").equals("qwe")){
                e1 = eles.get(i);
            }
        }
        e1.click();
//        List<WebElement> eles2 = waitElementsById("com.netease.nim.demo:id/contacts_item_name",4000);
//        WebElement e2 = null;
//        for (int i=0;i<eles2.toArray().length;i++){
//            if (!eles2.get(i).getAttribute("text").equals("默认好友")){
//                e2 = eles.get(i);
//            }
//        }
//        e2.click();
//        waitElementById("com.netease.nim.demo:id/begin_chat",4000).click();
        ThreadManager.A1Signal();
        ThreadManager.A2Await();
        List <Integer> l1 = new ArrayList<>();
        for(int i=0;i<1000;i++){
            List<WebElement> eles3 = waitElementsById("com.netease.nim.demo:id/nim_message_item_text_body",4000);
            for (int j=0;j<eles3.toArray().length;j++){
                if (eles3.get(j).getAttribute("text").equals(Integer.toString(i))){
                    l1.add(i);
                }
            }
            ThreadManager.A1Signal();
            if(i!=999){
                ThreadManager.A2Await();
            }
        }
        List <Integer> l2 = new ArrayList<>();//l1数组中缺少的元素
        for(int i=0;i<1000;i++){
            if(!l1.contains(i)){
                l2.add(i);
            }
        }
        int num = 1000;
        int lenl2 = l2.size();
        int cha = num - lenl2;
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        String result = numberFormat.format((float) cha / (float) num * 100);
        System.out.println("消息到达率为:" + result + "%");
        System.out.println(String.format("缺少元素：%s",l2));
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentTime);
        System.out.println("结束时间："+df.format(date));
    }
}