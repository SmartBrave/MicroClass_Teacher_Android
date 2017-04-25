package com.example.smart.microclass.FaQ.ChatItem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smart.microclass.FaQ.Chat.ChatWithMan;
import com.example.smart.microclass.R;

import java.util.List;

/**
 * Created by smart on 2017/4/10.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{
    private Context context;
    private List<Chat> chatList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout chatLayout;
        ImageView chatImage;
        TextView chatName;
        public ViewHolder(View view){
            super(view);
            System.out.println("ViewHolder");
            chatLayout=(LinearLayout)view.findViewById(R.id.chat_item_layout);
            chatImage =(ImageView)view.findViewById(R.id.chat_img);
            chatName=(TextView)view.findViewById(R.id.chat_text);
        }
    }

    public ChatAdapter(List<Chat> list){
        this.chatList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder");
        if(context==null){
            context=parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.chatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Chat chat=chatList.get(position);
                //Toast.makeText(v.getContext(),"you clicked view "+chat.getYouName(), Toast.LENGTH_SHORT).show();
                //click thing
                Intent intent=new Intent();
                intent.putExtra("youID",chat.getYouID()+"");
                intent.setClass(context, ChatWithMan.class);
                context.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("onBindViewHolder");
        Chat chat=chatList.get(position);
        holder.chatName.setText(chat.getYouName());
        Glide.with(context).load(chat.getChatImagePath()).thumbnail(0.2f).into(holder.chatImage);
    }

    @Override
    public int getItemCount() {
        System.out.println("99999999999999");
        return chatList.size();
    }
}
