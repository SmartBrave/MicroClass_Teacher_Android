package com.example.smart.microclass.HomePage;

/**
 * Created by smart on 2017/3/31.
 */

public class Video {
    private int videoID;
    private String videoName;
    private String videoPath;
    private int videoSize;//Mb
    private int videoLength;//seconds
    private String tag;//evert video must has a tag in order to search

    public Video(int id,String name,String path,int size,int length){
        this.videoID=id;
        this.videoName=name;
        this.videoPath=path;
        this.videoSize=size;
        this.videoLength=length;
    }

    public String getVideoName() {
        return videoName;
    }

    public int getVideoID() {
        return videoID;
    }

    public String getVideoPath(){
        return videoPath;
    }
}
