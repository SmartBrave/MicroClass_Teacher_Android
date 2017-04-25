package com.example.smart.microclass.OtherClass.PlayVideo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.smart.microclass.OtherClass.MyApplication;
import com.example.smart.microclass.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.Vitamio;
import io.vov.vitamio.widget.MediaController;

import static com.example.smart.microclass.OtherClass.GlobalVariable.gson;
import static com.example.smart.microclass.OtherClass.GlobalVariable.httpUtil;
import static com.example.smart.microclass.OtherClass.GlobalVariable.urlBase;
import static io.vov.vitamio.widget.VideoView.VIDEO_LAYOUT_SCALE;

public class PlayVideo extends AppCompatActivity {
    io.vov.vitamio.widget.VideoView videoView;
    private int mVideoLayout=VIDEO_LAYOUT_SCALE;
    private Comment[] comments={};
    private List<Comment> commentList=new ArrayList<>();
    private CommentAdapter adapter;
    private RecyclerView commentRecyclerView;
    private GridLayoutManager layoutManager;
    private String videoPath;
    private String videoID;
    private String videoName;
    private boolean isInit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_video);

        videoView=(io.vov.vitamio.widget.VideoView)findViewById(R.id.video);
        commentRecyclerView=(RecyclerView)findViewById(R.id.comment);

        Intent intent=getIntent();
        videoPath=intent.getStringExtra("videoPath");
        videoID=intent.getStringExtra("videoID");
        videoName=intent.getStringExtra("videoName");

        new GetCommentsTask().execute();

        //init
        Vitamio.isInitialized(this);
        videoView.setVideoPath(videoPath);
        videoView.setKeepScreenOn(true);
        videoView.setVideoLayout(mVideoLayout,1.5f);
        videoView.requestFocus();
        MediaController controller=new MediaController(this);
        videoView.setMediaController(controller);
        videoView.start();
    }
    private void init(){
        layoutManager=new GridLayoutManager(MyApplication.getContext(),1);
        commentRecyclerView.setLayoutManager(layoutManager);
        isInit=true;
    }

    private class GetCommentsTask extends AsyncTask<Void,Void,Boolean>{
        private String response;
        @Override
        protected void onPreExecute() {
            if(isInit==false){
                init();
            }
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isInit==true){
                String url=urlBase+"/get_comments";
                try{
                    response=httpUtil.post(url,"{\"videoID\":\""+videoID+"\"}");
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
                comments= gson.fromJson(response,Comment[].class);
                commentList.clear();
                for(int i=0;i<comments.length;i++){
                    commentList.add(comments[i]);
                }
                if(adapter==null) {
                    adapter = new CommentAdapter(commentList);
                    commentRecyclerView.setAdapter(adapter);
                }else{
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }
}
