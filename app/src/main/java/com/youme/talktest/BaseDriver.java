package com.youme.talktest;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class BaseDriver {
        public AppiumDriver<WebElement> driver;
        public ThreadManager ThreadManager;
        public HandleExcel HandleExcel;
        BaseDriver(String platformName,String platformVersion,String deviceName,String appPackage,String appActivity,String udid,String url,ThreadManager tm,HandleExcel he) throws MalformedURLException {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("platformName", platformName);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("deviceName",deviceName);
            capabilities.setCapability("appPackage", appPackage);
            capabilities.setCapability("appActivity", appActivity);
            capabilities.setCapability("udid", udid);
            capabilities.setCapability("unicodeKeyboard", true);
            capabilities.setCapability("resetKeyboard", true);
            capabilities.setCapability("noReset", true);
            System.out.println(capabilities);
            this.driver = new AndroidDriver(new URL(url), capabilities);
            this.ThreadManager = tm;
            this.HandleExcel = he;
        }
//        public AppiumDriver getDriver(){
//            return driver;
//        }
        public WebElement waitElementById(String id,long interval){
            long bTime = System.currentTimeMillis();
            WebElement ele = null;
            while (true){
                try{
                    ele =driver.findElementById(id);
                    break;
                }catch (Exception e){

                }
                long cTime = System.currentTimeMillis();
                if (cTime-bTime>interval){
                    System.out.println("未找到指定元素");
                    break;
                }
            }
            return ele;
        }

        public List<WebElement> waitElementsById(String id,long interval){
            long bTime = System.currentTimeMillis();
            List<WebElement> ele = null;
            while (true){
                try{
                    ele =driver.findElementsById(id);
                    if(!ele.isEmpty()){
                        break;
                    }
                }catch (Exception e){

                }
                long cTime = System.currentTimeMillis();
                if (cTime-bTime>interval){
                    System.out.println("未找到指定元素");
                    break;
                }
            }
            return ele;
        }

    public WebElement waitElementByClass(String className,long interval){
        long bTime = System.currentTimeMillis();
        WebElement ele = null;
        while (true){
            try{
                ele =driver.findElementByClassName(className);
                break;
            }catch (Exception e){

            }
            long cTime = System.currentTimeMillis();
            if (cTime-bTime>interval){
                System.out.println("未找到指定元素");
                break;
            }
        }
        return ele;
    }

    public List<WebElement> waitElementsByClass(String className,long interval){
        long bTime = System.currentTimeMillis();
        List<WebElement> ele = null;
        while (true){
            try{
                ele =driver.findElementsByClassName(className);
                if(!ele.isEmpty()){
                    break;
                }
            }catch (Exception e){

            }
            long cTime = System.currentTimeMillis();
            if (cTime-bTime>interval){
                System.out.println("未找到指定元素");
                break;
            }
        }
        return ele;
    }
}
