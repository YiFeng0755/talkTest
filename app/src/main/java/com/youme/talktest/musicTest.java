package com.youme.talktest;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class musicTest {
    public static void main (String args []){
        try{
        Player player = new Player(new BufferedInputStream(new FileInputStream(new File("C:\\Users\\aa\\Desktop\\111\\YIM_Android_2.0x\\talkTest\\app\\src\\main\\assets\\nekomimi.mp3"))));
        player.play();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
