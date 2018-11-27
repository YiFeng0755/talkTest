package com.youme.talktest;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.MalformedInputException;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class testDemo {
    public static void main(String args []) throws MalformedURLException{
        AppiumDriver driver;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "7.1.2");
        capabilities.setCapability("deviceName","eb878778");
        capabilities.setCapability("appPackage", "im.youme.talk.sample");
        capabilities.setCapability("appActivity", ".MainActivity");
        capabilities.setCapability("udid", "eb878778");
        System.out.println(capabilities);
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);

    }
}
