package com.example.smart.microclass.FaQ.AddFriend;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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


public class MyFriend extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout faq_in_add_friend_txt;
    private ImageView faq_add_friend_img;
    private TextView faq_in_add_friend_text;
    private LinearLayout faq_in_add_friend;
    private EditText add_friend_edittext;
    private Button add_friend_button;
    private RecyclerView friend_list;
    private SwipeRefreshLayout fresh;
    private User[] users={};
    private List<User> list=new ArrayList<>();
    private FriendAdapter friendAdapter;
    private GridLayoutManager layoutManager;
    private boolean isInit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_friend);

        faq_in_add_friend_txt=(LinearLayout) findViewById(R.id.faq_in_add_friend_txt);
        faq_add_friend_img=(ImageView) findViewById(R.id.faq_add_friend_img);
        faq_in_add_friend_text=(TextView) findViewById(R.id.faq_in_add_friend_text);
        faq_in_add_friend=(LinearLayout)findViewById(R.id.faq_in_add_friend);
        add_friend_edittext=(EditText) findViewById(R.id.add_friend_edittext);
        add_friend_button=(Button) findViewById(R.id.add_friend_button);
        friend_list=(RecyclerView) findViewById(R.id.friend_list);
        fresh=(SwipeRefreshLayout)findViewById(R.id.fresh_friend);

        faq_in_add_friend_txt.setOnClickListener(this);
        faq_add_friend_img.setOnClickListener(this);
        faq_in_add_friend_text.setOnClickListener(this);
        add_friend_edittext.setOnClickListener(this);
        add_friend_button.setOnClickListener(this);

        new GetFriendList().execute();

        fresh.setColorSchemeResources(R.color.colorPrimary);
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
    }
    private void init(){
        if(!isSuccess.equals("success")){
            return;
        }
        layoutManager=new GridLayoutManager(MyApplication.getContext(),1);
        friend_list.setLayoutManager(layoutManager);
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
                String url = urlBase + "/get_friend_list";
                try {
                    response = httpUtil.post(url, "{\"userID\":" + teacher.userID + "}");
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
                users = gson.fromJson(response, User[].class);
                list.clear();
                for (int i = 0; i < users.length; i++) {
                    list.add(users[i]);
                }
                if(friendAdapter==null){
                    friendAdapter=new FriendAdapter(list);
                    friend_list.setAdapter(friendAdapter);
                }else{
                    friendAdapter.notifyDataSetChanged();
                }
                fresh.setRefreshing(false);
            }
        }
    }

    private class GetFriendList extends AsyncTask<Void,Void,Boolean>{
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
                String url=urlBase+"/get_friend_list";
                try{
                    response=httpUtil.post(url,"{\"userID\":"+teacher.userID+"}");
                }catch (IOException e){
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
                users=gson.fromJson(response,User[].class);
                list.clear();
                for(int i=0;i<users.length;i++){
                    list.add(users[i]);
                }
                if(friendAdapter==null){
                    friendAdapter=new FriendAdapter(list);
                    friend_list.setAdapter(friendAdapter);
                }else{
                    friendAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.faq_in_add_friend_txt:
            case R.id.faq_add_friend_img:
            case R.id.faq_in_add_friend_text:
                faq_in_add_friend_txt.setVisibility(View.GONE);
                faq_in_add_friend.setVisibility(View.VISIBLE);
                break;
            case R.id.add_friend_button:
                AddFriend addFriend;
                addFriend=new AddFriend(teacher.userID,add_friend_edittext.getText().toString());
                addFriend.execute();
                break;
        }
    }

    private class AddFriend extends AsyncTask<Void,Void,String>{
        private String myID;
        private String phone;
        private String response;

        public AddFriend(String id,String p){
            this.myID=id;
            this.phone=p;
        }
        @Override
        protected void onPreExecute() {
            if(isInit==false){
                init();
            }
        }
        @Override
        protected String doInBackground(Void... params) {
            String url=urlBase+"/add_friend";
            String data="{\"myID\":\""+myID+"\",\"phone\":\""+phone+"\"}";
            try{
                response=httpUtil.post(url,data);
            }catch(IOException e){
                e.printStackTrace();
                return "net_error";
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s.equals("net_error")) {
                Toast.makeText(MyFriend.this,R.string.net_error,Toast.LENGTH_SHORT).show();
            }else if(response.equals("not exist")){
                Toast.makeText(MyFriend.this,R.string.user_not_exist,Toast.LENGTH_SHORT).show();
            }else if(response.equals("already friend")){
                Toast.makeText(MyFriend.this,R.string.already_friend,Toast.LENGTH_SHORT).show();
            }else if(response.equals("failed")){
                Toast.makeText(MyFriend.this,R.string.server_error,Toast.LENGTH_SHORT).show();
            }else if(response.equals("")){

            }else{//"true"
                Toast.makeText(MyFriend.this,R.string.waiting,Toast.LENGTH_SHORT).show();
                add_friend_edittext.clearComposingText();
            }
        }
        //other case:had already apply ,but other man not agree yet
    }
}
