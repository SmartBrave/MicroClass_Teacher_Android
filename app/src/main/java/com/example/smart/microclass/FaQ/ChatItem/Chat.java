package com.example.smart.microclass.FaQ.ChatItem;

/**
 * Created by smart on 2017/4/10.
 */

public class Chat {
    private int youID;
    private String youName;
    private String chatImagePath;

    public String getYouName(){
        return youName;
    }
    public String getChatImagePath(){
        return chatImagePath;
    }
    public void setImagePath(String s){
        chatImagePath=s;
    }
    public int getYouID(){
        return youID;
    }
}
