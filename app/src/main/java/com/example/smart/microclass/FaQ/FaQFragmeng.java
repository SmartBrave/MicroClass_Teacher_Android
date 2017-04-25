package com.example.smart.microclass.FaQ;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smart.microclass.FaQ.AddFriend.MyFriend;
import com.example.smart.microclass.FaQ.ChatItem.Chat;
import com.example.smart.microclass.FaQ.ChatItem.ChatAdapter;
import com.example.smart.microclass.OtherClass.MyApplication;
import com.example.smart.microclass.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.smart.microclass.OtherClass.GlobalVariable.gson;
import static com.example.smart.microclass.OtherClass.GlobalVariable.httpUtil;
import static com.example.smart.microclass.OtherClass.GlobalVariable.isSuccess;
import static com.example.smart.microclass.OtherClass.GlobalVariable.teacher;
import static com.example.smart.microclass.OtherClass.GlobalVariable.urlBase;

/**
 * Created by smart on 2017/3/25.
 */

public class FaQFragmeng extends Fragment implements View.OnClickListener{

    private LinearLayout faq_add_friend;
    private ImageView faq_add_friend_img;
    private TextView faq_add_friend_text;
    private RecyclerView faq_chat_list;
    private SwipeRefreshLayout fresh;
    private Chat[] chats={};
    private List<Chat> list=new ArrayList<>();
    private ChatAdapter chatAdapter;
    private GridLayoutManager layoutManager;
    private boolean isInit=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab03,container,false);


        faq_add_friend=(LinearLayout)view.findViewById(R.id.faq_add_friend);
        faq_add_friend_img=(ImageView)view.findViewById(R.id.faq_add_friend_img);
        faq_add_friend_text=(TextView)view.findViewById(R.id.faq_add_friend_text);
        faq_chat_list=(RecyclerView)view.findViewById(R.id.faq_chat_list);

        faq_add_friend.setOnClickListener(this);
        faq_add_friend_img.setOnClickListener(this);
        faq_add_friend_text.setOnClickListener(this);

        fresh=(SwipeRefreshLayout)view.findViewById(R.id.fresh_chat);
        fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(!isSuccess.equals("success")){
                    Toast.makeText(MyApplication.getContext(), R.string.not_login, Toast.LENGTH_SHORT).show();
                    fresh.setRefreshing(false);
                    return;
                }
                new FreshTask().execute();
            }
        });

        new GetQuestionTask().execute();
        return view;
    }
    private void init(){
        if(!isSuccess.equals("success")){
            return;
        }
        layoutManager=new GridLayoutManager(MyApplication.getContext(),1);
        faq_chat_list.setLayoutManager(layoutManager);
        isInit=true;
    }

    private class FreshTask extends AsyncTask<Void,Void,Boolean> {
        String response;
        @Override
        protected void onPreExecute() {
            if(isInit==false){
                init();
            }
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            if(isInit==true){
                String url = urlBase + "/get_question";
                try {
                    response = httpUtil.post(url, "{\"userID\":\"" + teacher.userID + "\"}");
                } catch (IOException e) {
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
            if (aBoolean) {
                chats= gson.fromJson(response, Chat[].class);
                list.clear();
                for (int i = 0; i < chats.length; i++) {
                    list.add(chats[i]);
                }
                if(chatAdapter==null){
                    chatAdapter=new ChatAdapter(list);
                    faq_chat_list.setAdapter(chatAdapter);
                }else{
                    chatAdapter.notifyDataSetChanged();
                }
                fresh.setRefreshing(false);
            }
        }
    }

    private class GetQuestionTask extends AsyncTask<Void,Void,Boolean>{
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
                String url=urlBase+"/get_question";
                try{
                    response=httpUtil.post(url,"{\"userID\":\""+teacher.userID+"\"}");
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
                    chats=gson.fromJson(response,Chat[].class);
                    list.clear();
                    for(int i=0;i<chats.length;i++){
                        list.add(chats[i]);
                    }
                if(chatAdapter==null){
                    chatAdapter=new ChatAdapter(list);
                    faq_chat_list.setAdapter(chatAdapter);
                }else{
                    chatAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.faq_add_friend:
            case R.id.faq_add_friend_img:
            case R.id.faq_add_friend_text:
                //login or not
                if(!isSuccess.equals("success")){
                    Toast.makeText(getActivity(),R.string.not_login,Toast.LENGTH_SHORT).show();
                    break;
                }
                Intent intent=new Intent();
                intent.setClass(getActivity(), MyFriend.class);
                startActivity(intent);
                break;
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        new GetQuestionTask().execute();
    }

    //next function is test func,delete is ok
    public static final String TAG ="FaQFragment";

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
