package com.example.smart.microclass.OtherClass;

import com.example.smart.microclass.HomePage.Video;

import java.util.List;

/**
 * Created by smart on 2017/3/31.
 */

public class Teacher extends Person{
    public  String userID;
    public  String userAccount;
    public  String userName;
    public  String password;
    public  String registerTime;
    public  List<Video> videoList;
    public  List<Person> friendList;

    public Teacher(){
    }

    public void set(String id,String name,String time,String account,String passwd){
        this.userID=id;
        this.userName=name;
        this.registerTime=time;
        this.userAccount=account;
        this.password=passwd;
    }

    public void uploadVideo(Video v){
        videoList.add(v);
    }

    public void addFriend(Person p){
        friendList.add(p);
    }
}
