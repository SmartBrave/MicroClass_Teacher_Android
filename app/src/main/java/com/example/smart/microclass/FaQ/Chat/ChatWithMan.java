package com.example.smart.microclass.FaQ.Chat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smart.microclass.OtherClass.MyApplication;
import com.example.smart.microclass.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.smart.microclass.OtherClass.GlobalVariable.gson;
import static com.example.smart.microclass.OtherClass.GlobalVariable.httpUtil;
import static com.example.smart.microclass.OtherClass.GlobalVariable.teacher;
import static com.example.smart.microclass.OtherClass.GlobalVariable.urlBase;

public class ChatWithMan extends AppCompatActivity {
    private Msg[] msgs={};
    private List<Msg> msgList=new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private LinearLayoutManager layoutManager;
    private String youID;
    private int heartTime=1000;
    private boolean not_kill=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        inputText=(EditText)findViewById(R.id.input_text);
        send=(Button)findViewById(R.id.send);
        msgRecyclerView=(RecyclerView)findViewById(R.id.msg_recycler_view);

        Intent intent=getIntent();
        youID=intent.getStringExtra("youID");

        new GetChatTaskWithMan(youID).execute();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content =inputText.getText().toString();
                if(!"".equals(content)){
                    Msg msg=new Msg(content,teacher.userID,youID);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size()-1);
                    msgRecyclerView.scrollToPosition(msgList.size()-1);
                    inputText.setText("");
                    new SendMessageTask(msg).execute();
                }else{
                    Toast.makeText(MyApplication.getContext(),R.string.msg_not_null,Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                not_kill=false;
                finish();
                break;
        }
        return false;
    }

    private class SendMessageTask extends AsyncTask<Void,Void,Boolean>{
        private String response;
        private Msg msg;

        SendMessageTask(Msg m){
            msg=m;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            String url=urlBase+"/send_message";
            try{
                response=httpUtil.post(url,"{\"message\":\""+msg.getContent()+"\",\"sendID\":\""+msg.getSendID()+"\",\"recieveID\":\""+msg.getRecieveID()+"\"}");
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
            return true;
        }

        //@Override
        //protected void onPostExecute(Boolean aBoolean) {
        //    if(aBoolean){
        //    }
        //}
    }
    private class GetChatTaskWithMan extends AsyncTask<Void,String,Boolean> {
        private String response;
        private String lastResponse;
        private String youID;

        GetChatTaskWithMan(String id){
            youID=id;
            lastResponse="";
        }

        @Override
        protected void onPreExecute() {
            if(adapter==null){
                layoutManager=new LinearLayoutManager(MyApplication.getContext());
                msgRecyclerView.setLayoutManager(layoutManager);
            }
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url=urlBase+"/get_chat_with_man";
            while(true){
                try{
                    response=httpUtil.post(url,"{\"userID\":\""+teacher.userID+"\",\"youID\":\""+youID+"\"}");
                }catch(IOException e){
                    e.printStackTrace();
                }
                publishProgress(response);
                switch (heartTime){
                    case 1000:
                        if(!response.equals(lastResponse)){ //has new message
                            //do nothing
                        }else{
                            heartTime=heartTime*2;
                        }
                        break;
                    case 2000:
                        if(!response.equals(lastResponse)){
                            heartTime=1000;
                        }else{
                            heartTime=heartTime*2;
                        }
                        break;
                    case 4000:
                        if(!response.equals(lastResponse)){
                            heartTime=1000;
                        }else{
                            heartTime=heartTime*2;
                        }
                        break;
                    case 8000:
                        if(!response.equals(lastResponse)){
                            heartTime=1000;
                        }else{
                            heartTime=heartTime*2;
                        }
                        break;
                    case 16000:
                        if(!response.equals(lastResponse)){
                            heartTime=1000;
                        }else{
                            //do nothing
                        }
                        break;
                    default:
                        break;
                }
                if(not_kill){
                    try{
                        Thread.sleep(heartTime);
                    }catch(InterruptedException i){
                        i.printStackTrace();
                    }
                }else{
                    return false;
                }
            }
        }

        @Override
        protected synchronized void onProgressUpdate(String... values) {
            //later on,add incremental update function
            msgs=gson.fromJson(values[0],Msg[].class);
            msgList.clear();
            for(int i=0;i<msgs.length;i++){
                msgList.add(msgs[i]);
            }
            if(adapter==null){
                adapter=new MsgAdapter(msgList);
                msgRecyclerView.setAdapter(adapter);
            }else{
                if(!response.equals(lastResponse)) {
                    adapter.notifyItemInserted(msgList.size() - 1);
                }
            }
            lastResponse=values[0];
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                msgs=gson.fromJson(response,Msg[].class);
                msgList.clear();
                for(int i=0;i<msgs.length;i++){
                    msgList.add(msgs[i]);
                }
                adapter=new MsgAdapter(msgList);
                msgRecyclerView.setAdapter(adapter);
            }
        }
    }
}
