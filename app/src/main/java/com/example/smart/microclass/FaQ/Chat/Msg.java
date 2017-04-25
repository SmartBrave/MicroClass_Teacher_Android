package com.example.smart.microclass.FaQ.Chat;

/**
 * Created by smart on 2017/3/23.
 */

public class Msg {
    private String message;
    private String sendID;
    private String recieveID;

    public Msg(String content,String sid,String rid){
        this.message=content;
        this.sendID=sid;
        this.recieveID=rid;
    }
    public String getContent(){
        return message;
    }
    public String getSendID() {
        return sendID;
    }
    public String getRecieveID(){
        return recieveID;
    }
}
