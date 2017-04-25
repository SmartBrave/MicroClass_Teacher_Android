package com.example.smart.microclass.HomePage;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smart.microclass.OtherClass.MyApplication;
import com.example.smart.microclass.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.smart.microclass.OtherClass.GlobalVariable.gson;
import static com.example.smart.microclass.OtherClass.GlobalVariable.httpUtil;
import static com.example.smart.microclass.OtherClass.GlobalVariable.isSuccess;
import static com.example.smart.microclass.OtherClass.GlobalVariable.urlBase;


/**
 * Created by smart on 2017/3/25.
 */

public class HomePageFragment extends Fragment{

    private DrawerLayout drawerLayout;
    //private Video[] videos={new Video(1,"one","onePath",10,20),new Video(2,"two","twoPath",30,40)};
    private Video[] videos={};
    private List<Video> videoList=new ArrayList<>();
    private VideoAdapter videoAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private GridLayoutManager  layoutManager;
    private boolean isInit=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab01,container,false);

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        swipeRefresh=(SwipeRefreshLayout)view.findViewById(R.id.swipe_fresh);

        new GetVideofromServerTask().execute();

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isSuccess.equals("success")){
                    Toast.makeText(MyApplication.getContext(), R.string.not_login, Toast.LENGTH_SHORT).show();
                    swipeRefresh.setRefreshing(false);
                    return;
                }
                FreshTask freshTask=new FreshTask();
                freshTask.execute();
            }
        });
        return view;
    }
    private void initVideos(){
        if(!isSuccess.equals("success")){
            return;
        }
        layoutManager=new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        isInit=true;
    }
    private class FreshTask extends AsyncTask<Void,Void,Boolean>{
        String response;
        @Override
        protected void onPreExecute() {
            if(isInit==false){
                initVideos();
            }
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isInit==true){
                String url=urlBase+"/get_videos";
                try{
                    response=httpUtil.getVideo(url,"");
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
            if(aBoolean==true){
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
                swipeRefresh.setRefreshing(false);
            }
        }
    }
    private class GetVideofromServerTask extends AsyncTask<Void,Void,Boolean>{
        String response;
        @Override
        protected void onPreExecute() {
            if(isInit==false){
                initVideos();
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if(isInit==true){
                String url=urlBase+"/get_videos";
                try{
                    response=httpUtil.getVideo(url,"");
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
            if(aBoolean==true){
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
    @Override
    public void onStart() {
        super.onStart();
        //System.out.println(TAG+"onStart");
        new GetVideofromServerTask().execute();
    }






    public static final String TAG ="HomePageFragment";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //System.out.println(TAG+"onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //System.out.println(TAG+"onCreate");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //System.out.println(TAG+"onActivityCreated");
    }


    @Override
    public void onResume() {
        super.onResume();
        //System.out.println(TAG+"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        //System.out.println(TAG+"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        //System.out.println(TAG+"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //System.out.println(TAG+"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //System.out.println(TAG+"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //System.out.println(TAG+"onDetach");
    }
}
