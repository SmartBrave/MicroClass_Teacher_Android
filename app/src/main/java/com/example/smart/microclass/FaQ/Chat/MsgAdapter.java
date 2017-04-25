package com.example.smart.microclass.FaQ.Chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smart.microclass.R;

import java.util.List;

import static com.example.smart.microclass.OtherClass.GlobalVariable.teacher;

/**
 * Created by smart on 2017/3/23.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    private Context context;
    private List<Msg> mMsgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightlayout;
        TextView leftMsg;
        TextView rightMsg;
        ImageView leftImg;
        ImageView rightImg;

        public ViewHolder(View view){
            super(view);
            leftLayout=(LinearLayout)view.findViewById(R.id.left_layout);
            rightlayout=(LinearLayout)view.findViewById(R.id.right_layout);
            leftMsg=(TextView)view.findViewById(R.id.left_msg);
            rightMsg=(TextView)view.findViewById(R.id.right_msg);
            leftImg=(ImageView)view.findViewById(R.id.left_img);
            rightImg=(ImageView)view.findViewById(R.id.right_img);
        }
    }

    public MsgAdapter(List<Msg> msgList){
        mMsgList=msgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.msg_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg=mMsgList.get(position);
        //System.out.println("onBindViewHolder --->  position:"+position);
        //System.out.println("onBindViewHolder --->  msg.getcontent():"+msg.getContent());
        //System.out.println("onBindViewHolder --->  msg.getsendid():"+msg.getSendID());
        //System.out.println("onBindViewHolder --->  msg.getrecievedid():"+msg.getRecieveID());
        //System.out.println("teacher.userid:"+teacher.userID);
        if(msg.getRecieveID().equals(teacher.userID)){
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightlayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
            Glide.with(context).load(msg.getSendID()).thumbnail(0.2f).into(holder.leftImg);
        }else if(msg.getSendID().equals(teacher.userID)){
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightlayout.setVisibility(View.VISIBLE);
            holder.rightMsg.setText(msg.getContent());
        }else{
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightlayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mMsgList.size();
    }
}
