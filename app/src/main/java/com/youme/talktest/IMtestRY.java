package com.youme.talktest;

import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class IMtestRY {
    public static void main (String args[]) throws MalformedURLException{
        ThreadManager tm = new ThreadManager();
        HandleExcel he = new HandleExcel();
        User01 u1 = new User01("Android","8.1.0","121c03b","cn.rongcloud.im",".ui.activity.SplashActivity","121c03b","http://127.0.0.1:4723/wd/hub",tm,he);
        Thread t1 = new Thread(u1,"User1");
        t1.start();

        User02 u2 = new User02("Android","7.1.1","b8577a94","cn.rongcloud.im",".ui.activity.SplashActivity","b8577a94","http://127.0.0.1:4724/wd/hub",tm,he);
        Thread t2 = new Thread(u2,"User2");
        t2.start();
    }
}

//黑鲨
class User01 extends BaseDriver implements Runnable {
    User01(String platformName,String platformVersion,String deviceName,String appPackage,String appActivity,String udid,String url,ThreadManager tm,HandleExcel he) throws MalformedURLException {
        super(platformName,platformVersion,deviceName,appPackage,appActivity,udid,url,tm,he);
    }

    @Override
    public void run() {
        List<WebElement> eles = waitElementsById("cn.rongcloud.im:id/tab_text_contact",4000);
        WebElement e1 = null;
        for (int i=0;i<eles.toArray().length;i++){
            System.out.println(eles.get(i).getAttribute("text"));
            if (eles.get(i).getAttribute("text").equals("通讯录")){
                e1 = eles.get(i);
            }
        }
        e1.click();

        List<WebElement> eles1 = waitElementsById("cn.rongcloud.im:id/friendname",4000);
        WebElement e2 = null;
        for (int i=0;i<eles1.toArray().length;i++){
            System.out.println(eles1.get(i).getAttribute("text"));
            if (eles1.get(i).getAttribute("text").equals("youme123")){
                e2 = eles1.get(i);
            }
        }
        e2.click();

        List<WebElement> eles2 = waitElementsByClass("android.widget.Button",4000);
        WebElement e3 = null;
        for (int i=0;i<eles2.toArray().length;i++){
            System.out.println(eles2.get(i).getAttribute("text"));
            if (eles2.get(i).getAttribute("text").equals("发起会话")){
                e3 = eles2.get(i);
            }
        }
        e3.click();

        ThreadManager.A1Await();
        WebElement sendText = waitElementById("cn.rongcloud.im:id/rc_edit_text",4000);
        sendText.click();
        Process p = null;
        for (int i=0;i<1000;i++){

            String cmd = "cmd /c adb -s 121c03b shell input text "+Integer.toString(i);
            try {
                p = Runtime.getRuntime().exec(cmd);
            }catch(IOException e){
                e.printStackTrace();
            }

            waitElementById("cn.rongcloud.im:id/rc_send_toggle",4000).click();
            ThreadManager.A2Signal();
            if(i!=999){
                ThreadManager.A1Await();
            }
        }
        p.destroy();
    }
}

//一加
class User02 extends BaseDriver implements Runnable {
    User02(String platformName,String platformVersion,String deviceName,String appPackage,String appActivity,String udid,String url,ThreadManager tm,HandleExcel he) throws MalformedURLException {
        super(platformName,platformVersion,deviceName,appPackage,appActivity,udid,url,tm,he);
    }

    @Override
    public void run() {
        List<WebElement> eles = waitElementsById("cn.rongcloud.im:id/tab_text_contact",4000);
        WebElement e1 = null;
        for (int i=0;i<eles.toArray().length;i++){
            System.out.println(eles.get(i).getAttribute("text"));
            if (eles.get(i).getAttribute("text").equals("通讯录")){
                e1 = eles.get(i);
            }
        }
        e1.click();

        List<WebElement> eles1 = waitElementsById("cn.rongcloud.im:id/friendname",4000);
        WebElement e2 = null;
        for (int i=0;i<eles1.toArray().length;i++){
            System.out.println(eles1.get(i).getAttribute("text"));
            if (eles1.get(i).getAttribute("text").equals("ym123")){
                e2 = eles1.get(i);
            }
        }
        e2.click();

        List<WebElement> eles2 = waitElementsByClass("android.widget.Button",4000);
        WebElement e3 = null;
        for (int i=0;i<eles2.toArray().length;i++){
            System.out.println(eles2.get(i).getAttribute("text"));
            if (eles2.get(i).getAttribute("text").equals("发起会话")){
                e3 = eles2.get(i);
            }
        }
        e3.click();

        ThreadManager.A1Signal();
        ThreadManager.A2Await();
        List <Integer> l1 = new ArrayList<>();
        for(int i=0;i<1000;i++){
            List<WebElement> eles3 = waitElementsById("android:id/text1",4000);
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

    }
}