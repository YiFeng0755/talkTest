package com.youme.talktest;

//import com.youme.talktest.BaseDriver;



import android.app.Service;
import android.content.Context;
import android.media.AudioManager;

import org.openqa.selenium.WebElement;


import java.io.IOException;
import java.net.MalformedURLException;

import io.appium.java_client.AppiumDriver;
import javazoom.jl.player.Player;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;

import static android.content.Context.AUDIO_SERVICE;

public class TalkTest {
    public static void main (String args []) throws MalformedURLException{
        ThreadManager tm = new ThreadManager();
        HandleExcel he = new HandleExcel();
        ZhuBoDriver zb = new ZhuBoDriver("Android","8.0.0","eb878778","im.youme.talk.sample",".MainActivity","eb878778","http://127.0.0.1:4723/wd/hub",tm,he);
        Thread t1 = new Thread(zb,"Zhubo");
        t1.start();

        A1Driver A1 = new A1Driver("Android","8.0.0","547ac878","im.youme.talk.sample",".MainActivity","547ac878","http://127.0.0.1:4723/wd/hub",tm,he);
        Thread t2 = new Thread(A1,"A1");
        t2.start();

    }

}

//主播线程
class ZhuBoDriver extends BaseDriver implements Runnable {
    ZhuBoDriver(String platformName,String platformVersion,String deviceName,String appPackage,String appActivity,String udid,String url,ThreadManager tm,HandleExcel he) throws MalformedURLException {
        super(platformName,platformVersion,deviceName,appPackage,appActivity,udid,url,tm,he);
    }
    @Override
    public void run(){
        driver.findElementById("im.youme.talk.sample:id/roomID").clear();
        driver.findElementById("im.youme.talk.sample:id/roomID").sendKeys("100");
        driver.findElementById("im.youme.talk.sample:id/btn_host_mode").click();//主播模式开启房间
        System.out.println(Thread.currentThread().getName());

        //界面用例
        //获取指定的checkable按钮的状态值
        boolean mc = Boolean.parseBoolean(driver.findElementById("im.youme.talk.sample:id/hostMicSwitch").getAttribute("checked"));
        boolean sp = Boolean.parseBoolean(driver.findElementById("im.youme.talk.sample:id/hostSpeakerSwitch").getAttribute("checked"));
        boolean mmc = Boolean.parseBoolean(driver.findElementById("im.youme.talk.sample:id/hostMonitorMicSwitch").getAttribute("checked"));
        boolean mb = Boolean.parseBoolean(driver.findElementById("im.youme.talk.sample:id/hostMonitorBgmSwitch").getAttribute("checked"));
        boolean hp = Boolean.parseBoolean(driver.findElementById("im.youme.talk.sample:id/hostPauseSwitch").getAttribute("checked"));
        String fsep = System.getProperty("file.separator");
        String assetsPath = "app"+fsep+"src"+fsep+"main"+fsep+"assets"+fsep;
        System.out.println(new File(assetsPath).getAbsolutePath());
        String excelPath =assetsPath+"游密语音测试用例集合2017_new.xlsx";
//        String mp3Path = assetsPath+"nekomimi.mp3";
        if (mc==true&&sp==true&&mmc==false&&mb==false&&hp==false){
            System.out.println("用例通过，结果写入excel");
            try{
                HandleExcel.alterCell(excelPath,0,3,5,"通过");
            }catch(IOException e){
                e.printStackTrace();
            }
        }

        //麦克风用例
        //关闭所有选项
        driver.findElementById("im.youme.talk.sample:id/hostMicSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostSpeakerSwitch").click();

        ThreadManager.ZhuBoReady = true;
        ThreadManager.ZbAwait();
        //点击播放2按钮，播放sdcard中的MP3，模拟说话
        driver.findElementById("im.youme.talk.sample:id/play_pause2").click();
        System.out.println("点击了按钮play_pause2");
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();
        //开启麦克风
        driver.findElementById("im.youme.talk.sample:id/hostMicSwitch");
        System.out.println("点击了按钮hostMicSwitch");
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();
        //开启监听
        driver.findElementById("im.youme.talk.sample:id/hostMonitorMicSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostMonitorBgmSwitch").click();
        System.out.println("开启监听");
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();
        //关闭监听，开启背景音乐
        driver.findElementById("im.youme.talk.sample:id/hostMonitorMicSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostMonitorBgmSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostBgmSwitch").click();
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();
        //关闭背景音乐，开启扬声器
        driver.findElementById("im.youme.talk.sample:id/hostBgmSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostSpeakerSwitch").click();
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();
        //关闭扬声器，开启暂停通话
        driver.findElementById("im.youme.talk.sample:id/hostSpeakerSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostPauseSwitch").click();
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();

        //监听用例
        //关闭暂停通话,开启监听
        driver.findElementById("im.youme.talk.sample:id/hostPauseSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostMonitorMicSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostMonitorBgmSwitch").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text.equals("无声音")) {
            Identify.ZhuBoOK = true;
        }
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();

        //开启背景乐
        driver.findElementById("im.youme.talk.sample:id/hostBgmSwitch").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text1 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text1.equals("无声音")) {
            Identify.ZhuBoOK = true;
        }
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();

        //开启扬声器
        driver.findElementById("im.youme.talk.sample:id/hostSpeakerSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/play_pause").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text2 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text2.equals("有声音")) {
            Identify.ZhuBoOK = true;
        }
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();

        //开启麦克风，关闭背景音乐
        driver.findElementById("im.youme.talk.sample:id/hostMicSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostBgmSwitch").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text3 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text3.equals("有声音")) {
            Identify.ZhuBoOK = true;
        }
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();

        //开启背景乐，关闭扬声器
        driver.findElementById("im.youme.talk.sample:id/play_pause").click();
        driver.findElementById("im.youme.talk.sample:id/hostBgmSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostSpeakerSwitch").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text4 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text4.equals("无声音")) {
            Identify.ZhuBoOK = true;
        }
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();

        //开启扬声器
        driver.findElementById("im.youme.talk.sample:id/play_pause").click();
        driver.findElementById("im.youme.talk.sample:id/hostSpeakerSwitch").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text5 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text5.equals("有声音")) {
            Identify.ZhuBoOK = true;
        }
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();

        //背景乐用例
        //开启背景乐，关闭其他，主播说话
        driver.findElementById("im.youme.talk.sample:id/play_pause").click();
        driver.findElementById("im.youme.talk.sample:id/hostMonitorMicSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostMonitorBgmSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostMicSwitch").click();
        driver.findElementById("im.youme.talk.sample:id/hostSpeakerSwitch").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text6 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text6.equals("无声音")) {
            Identify.ZhuBoOK = true;
        }
        ThreadManager.A1Signal();
        ThreadManager.ZbAwait();

    }
}

//听众1线程
class A1Driver extends BaseDriver implements Runnable {
    A1Driver(String platformName,String platformVersion,String deviceName,String appPackage,String appActivity,String udid,String url,ThreadManager tm,HandleExcel he) throws MalformedURLException {
        super(platformName,platformVersion,deviceName,appPackage,appActivity,udid,url,tm,he);
    }
    @Override
    public void run(){
        driver.findElementById("im.youme.talk.sample:id/roomID").clear();
        driver.findElementById("im.youme.talk.sample:id/roomID").sendKeys("100");
        driver.findElementById("im.youme.talk.sample:id/btn_listener_mode").click();//听众模式进入房间
        System.out.println(Thread.currentThread().getName());
        String fsep = System.getProperty("file.separator");
        String assetsPath = "app"+fsep+"src"+fsep+"main"+fsep+"assets"+fsep;
        System.out.println(new File(assetsPath).getAbsolutePath());
        String excelPath =assetsPath+"游密语音测试用例集合2017_new.xlsx";
        while (!ThreadManager.ZhuBoReady){
            try{
                Thread.sleep(2000);
                System.out.println("主播线程未准备好，等待2秒");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        ThreadManager.ZbSignal();
        ThreadManager.ZhuBoReady = false;
        ThreadManager.A1Await();
        String text = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text.equals("无声音")) {
            try{
                HandleExcel.alterCell(excelPath,0,4,5,"通过");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击按钮,获取声音状态，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text1 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text1.equals("有声音")) {
            try{
                HandleExcel.alterCell(excelPath,0,5,5,"通过");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text2 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text2.equals("有声音")) {
            try{
                HandleExcel.alterCell(excelPath,0,6,5,"通过");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text3 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text3.equals("有声音")) {
            try{
                HandleExcel.alterCell(excelPath,0,7,5,"通过");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text4 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text4.equals("有声音")) {
            try{
                HandleExcel.alterCell(excelPath,0,8,5,"通过");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        driver.findElementById("im.youme.talk.sample:id/hostPauseSwitch").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text5 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text5.equals("无声音")) {
            try{
                HandleExcel.alterCell(excelPath,0,9,5,"通过");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text6 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text6.equals("无声音")&&Identify.ZhuBoOK) {
            System.out.println(String.format("ZhuBo线程条件是否满足：%s",Identify.ZhuBoOK));
            try{
                HandleExcel.alterCell(excelPath,0,10,5,"通过");
                Identify.ZhuBoOK = false;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        driver.findElementById("im.youme.talk.sample:id/hostPauseSwitch").click();
        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text7 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text7.equals("有声音")&&Identify.ZhuBoOK) {
            System.out.println(String.format("ZhuBo线程条件是否满足：%s",Identify.ZhuBoOK));
            try{
                HandleExcel.alterCell(excelPath,0,11,5,"通过");
                Identify.ZhuBoOK = false;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text8 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text8.equals("有声音")&&Identify.ZhuBoOK) {
            System.out.println(String.format("ZhuBo线程条件是否满足：%s",Identify.ZhuBoOK));
            try{
                HandleExcel.alterCell(excelPath,0,12,5,"通过");
                Identify.ZhuBoOK = false;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text9 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text9.equals("有声音")&&Identify.ZhuBoOK) {
            System.out.println(String.format("ZhuBo线程条件是否满足：%s",Identify.ZhuBoOK));
            try{
                HandleExcel.alterCell(excelPath,0,13,5,"通过");
                Identify.ZhuBoOK = false;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text10 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text10.equals("有声音")&&Identify.ZhuBoOK) {
            System.out.println(String.format("ZhuBo线程条件是否满足：%s",Identify.ZhuBoOK));
            try{
                HandleExcel.alterCell(excelPath,0,14,5,"通过");
                Identify.ZhuBoOK = false;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text11 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text11.equals("有声音")&&Identify.ZhuBoOK) {
            System.out.println(String.format("ZhuBo线程条件是否满足：%s",Identify.ZhuBoOK));
            try{
                HandleExcel.alterCell(excelPath,0,15,5,"通过");
                Identify.ZhuBoOK = false;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();//11月6日写到此处

        //点击获取声音状态按钮，验证当前系统是否有声音
        driver.findElementById("im.youme.talk.sample:id/getVoiceStatusBtn").click();
        System.out.println("点击了按钮getVoiceStatusBtn");
        String text12 = driver.findElementById("im.youme.talk.sample:id/voiceStatusTV").getAttribute("text");
        if (text12.equals("有声音")&&Identify.ZhuBoOK) {
            System.out.println(String.format("ZhuBo线程条件是否满足：%s",Identify.ZhuBoOK));
            try{
                HandleExcel.alterCell(excelPath,0,16,5,"通过");
                Identify.ZhuBoOK = false;
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        ThreadManager.ZbSignal();
        ThreadManager.A1Await();



    }
}