package com.example.smart.microclass.FaQ.AddFriend;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smart.microclass.R;

import java.util.List;

/**
 * Created by smart on 2017/4/9.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{
    private Context context;
    private List<User> userList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout userLayout;
        ImageView userImage;
        TextView userName;
        public ViewHolder(View view){
            super(view);
            System.out.println("ViewHolder");
            userLayout=(LinearLayout)view.findViewById(R.id.user_item_layout);
            userImage =(ImageView)view.findViewById(R.id.user_img);
            userName=(TextView)view.findViewById(R.id.user_name);
        }
    }

    public FriendAdapter(List<User> userlist){
        this.userList=userlist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("onCreateViewHolder");
        if(context==null){
            context=parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.userLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                User user=userList.get(position);
                Toast.makeText(v.getContext(),"you clicked view "+user.getUserName(), Toast.LENGTH_SHORT).show();
                //click thing
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("onBindViewHolder");
        User user=userList.get(position);
        holder.userName.setText(user.getUserName());
        Glide.with(context).load(user.getUserImageUrl()).thumbnail(0.2f).into(holder.userImage);
    }

    @Override
    public int getItemCount() {
        System.out.println("99999999999999");
        return userList.size();
    }
}
