package com.example.smart.microclass.FaQ.AddFriend;

/**
 * Created by smart on 2017/4/9.
 */

public class User {
    private int userID;
    private String userName;
    private String userImagePath;

    public String getUserName(){
        return userName;
    }
    public String getUserImageUrl(){
        return userImagePath;
    }
    public void setUserImageUrl(String url){
        userImagePath=url;
    }
}
