package com.example.smart.microclass.OtherClass.PlayVideo;

/**
 * Created by smart on 2017/4/15.
 */

public class Comment {
    private String commentID;
    private String userID;
    private String userName;
    private String imagePath;
    private String youCommentID;//answer other answer
    private String videoID;
    private String msg;
    private String star;
    private String publishTime;

    public Comment(String ci,String ui,String un,String ip,String yci,String vi,String m,String s,String t){
        commentID=ci;
        userID=ui;
        userName=un;
        imagePath=ip;
        youCommentID=yci;
        videoID=vi;
        msg=m;
        star=s;
        publishTime=t;
    }
    public String getCommentID(){
        return commentID;
    }
    public String getUserID(){
        return userID;
    }
    public String getUserName(){
        return userName;
    }
    public String getImagePath(){
        return imagePath;
    }
    public String getYouCommentID(){
        return youCommentID;
    }
    public String getVideoID(){
        return videoID;
    }
    public String getMsg(){
        return msg;
    }
    public String getStar(){
        return star;
    }
}
