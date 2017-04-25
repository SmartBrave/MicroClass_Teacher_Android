package com.example.smart.microclass.Course;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.smart.microclass.HomePage.Video;
import com.example.smart.microclass.HomePage.VideoAdapter;
import com.example.smart.microclass.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.smart.microclass.OtherClass.GlobalVariable.gson;
import static com.example.smart.microclass.OtherClass.GlobalVariable.httpUtil;
import static com.example.smart.microclass.OtherClass.GlobalVariable.urlBase;

public class VideoList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout fresh;
    private Video[] videos={};
    private List<Video> videoList=new ArrayList<>();
    private VideoAdapter videoAdapter;
    private String type;
    private boolean isInit=false;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_video_list);
        videoAdapter=new VideoAdapter(videoList);
        recyclerView.setAdapter(videoAdapter);

        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        new GetVideosWithTag(type).execute();

        fresh=(SwipeRefreshLayout)findViewById(R.id.fresh_videoList);
        fresh.setColorSchemeResources(R.color.colorPrimary);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FreshTask freshTask=new FreshTask(type);
                freshTask.execute();
            }
        });
    }
    private void initVideos(){
        layoutManager=new GridLayoutManager(VideoList.this,2);
        recyclerView.setLayoutManager(layoutManager);
        isInit=true;
    }
    private class FreshTask extends AsyncTask<Void,Void,Boolean> {
        String type;
        String response;
        FreshTask(String t){
            type=t;
        }

        @Override
        protected void onPreExecute() {
            if(isInit==false){
                initVideos();
            }
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isInit==true){
                String url=urlBase+"/get_videos_with_tag";
                try{
                    response=httpUtil.getVideoWithTag(url,type);
                }catch(IOException e){
                    e.printStackTrace();
                    return false;
                }
                return true;
            }else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                videos=gson.fromJson(response,Video[].class);
                videoList.clear();
                for(int i=0;i<videos.length;i++){
                    videoList.add(videos[i]);
                }
                if(videoAdapter==null){
                    videoAdapter=new VideoAdapter(videoList);
                    recyclerView.setAdapter(videoAdapter);
                }else{
                    videoAdapter.notifyDataSetChanged();
                }
                fresh.setRefreshing(false);
            }
        }
    }
    private class GetVideosWithTag extends AsyncTask<Void,Void,Boolean>{
        private String type;
        private String response;
        GetVideosWithTag(String d){
            this.type=d;
        }

        @Override
        protected void onPreExecute() {
            if(isInit==false){
                initVideos();
            }
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isInit==true){
                String url=urlBase+"/get_videos_with_tag";
                try{
                    response=httpUtil.getVideoWithTag(url,type);
                }catch(IOException e){
                    e.printStackTrace();
                    return false;
                }
                return true;
            }else{
                return false;
            }
        }
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                videos=gson.fromJson(response,Video[].class);
                videoList.clear();
                for(int i=0;i<videos.length;i++){
                    videoList.add(videos[i]);
                }
                if(videoAdapter==null) {
                    videoAdapter = new VideoAdapter(videoList);
                    recyclerView.setAdapter(videoAdapter);
                }else{
                    videoAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
