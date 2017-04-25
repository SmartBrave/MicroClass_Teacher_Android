package com.example.smart.microclass.OtherClass.PlayVideo;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smart.microclass.R;

import java.io.IOException;
import java.util.List;

import static com.example.smart.microclass.OtherClass.GlobalVariable.gson;
import static com.example.smart.microclass.OtherClass.GlobalVariable.httpUtil;
import static com.example.smart.microclass.OtherClass.GlobalVariable.isSuccess;
import static com.example.smart.microclass.OtherClass.GlobalVariable.teacher;
import static com.example.smart.microclass.OtherClass.GlobalVariable.urlBase;

/**
 * Created by smart on 2017/4/15.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder>{
    private Context context;
    private List<Comment> CommentList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView userImage;
        TextView userName;
        TextView msg;
        LinearLayout answerLayout;
        ImageView answerImage;
        TextView answerUserName;
        TextView answerMsg;
        TextView star_txt;
        ImageView star_img;
        LinearLayout answerEditLayout;
        EditText answerEdit;
        Button answerEditButton;
        public ViewHolder(View view){
            super(view);
            userImage =(ImageView)view.findViewById(R.id.comment_img);
            userName=(TextView)view.findViewById(R.id.comment_user_name);
            msg=(TextView)view.findViewById(R.id.comment_msg);
            star_txt=(TextView)view.findViewById(R.id.comment_star_txt);
            star_img=(ImageView)view.findViewById(R.id.comment_star_img);
            answerLayout=(LinearLayout)view.findViewById(R.id.answer_layout);
            answerImage=(ImageView)view.findViewById(R.id.comment_answer_img);
            answerUserName=(TextView)view.findViewById(R.id.comment_answer_name);
            answerMsg=(TextView)view.findViewById(R.id.comment_answer_msg);
            answerEditLayout=(LinearLayout)view.findViewById(R.id.comment_answer_edit_layout);
            answerEdit=(EditText)view.findViewById(R.id.comment_answer_edit);
            answerEditButton=(Button)view.findViewById(R.id.comment_answer_edit_btn);
        }
    }

    public CommentAdapter(List<Comment> list){
        this.CommentList=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(context==null){
            context=parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.comment_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        //holder.cardView.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        int position=holder.getAdapterPosition();
        //        Video video=videoList.get(position);
        //        Toast.makeText(v.getContext(),"you clicked view "+video.getVideoName(), Toast.LENGTH_SHORT).show();
        //        //click thing
        //        Intent intent=new Intent();
        //        intent.putExtra("videoPath",video.getVideoPath());
        //        intent.putExtra("videoID",video.getVideoID());
        //        intent.putExtra("videoName",video.getVideoName());
        //        intent.setClass(context, PlayVideo.class);
        //        context.startActivity(intent);
        //    }
        //});

        //other layout's click thing
        holder.star_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSuccess.equals("success")){
                    Toast.makeText(context,R.string.not_login,Toast.LENGTH_SHORT).show();
                    return;
                }
                int position=holder.getAdapterPosition();
                Comment comment=CommentList.get(position);
                int latestStar=Integer.parseInt(holder.star_txt.getText().toString())+1;
                new UpdateStar(comment.getCommentID(),latestStar).execute();
                holder.star_txt.setText(latestStar+"");
                holder.star_img.setImageResource(R.drawable.star1);
            }
        });
        holder.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isSuccess.equals("success")){
                    Toast.makeText(context,R.string.not_login,Toast.LENGTH_SHORT).show();
                    return;
                }
                int position=holder.getAdapterPosition();
                Comment comment=CommentList.get(position);
                holder.answerEditLayout.setVisibility(View.VISIBLE);
                holder.answerEdit.setFocusable(true);
                holder.answerEdit.requestFocus();
                ///////////////////////////
                holder.answerEdit.setHint("回复"+comment.getUserName());
            }
        });
        holder.answerEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    holder.answerEditLayout.setVisibility(View.GONE);
                }
            }
        });
        holder.answerEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Comment comment=CommentList.get(position);
                String msg=holder.answerEdit.getText().toString();
                if(msg.equals("")){
                    Toast.makeText(context,R.string.not_null,Toast.LENGTH_SHORT).show();
                    return;
                }
                Comment c=new Comment("-1", teacher.userID,teacher.userName,"",comment.getCommentID(),comment.getVideoID(),msg,"0","");
                String json= gson.toJson(c);
                new CreateCommentTask(json).execute();
                holder.answerLayout.setVisibility(View.VISIBLE);
                holder.answerEditLayout.setVisibility(View.GONE);
                Glide.with(context).load(comment.getImagePath()).thumbnail(0.2f).into(holder.answerImage);
                holder.answerMsg.setText(msg);
                holder.answerUserName.setText(comment.getUserName());
                holder.msg.setClickable(false);
            }
        });
        return holder;
    }

    private class CreateCommentTask extends AsyncTask<Void,Void,Boolean>{
        String jsonData;
        String response;
        CreateCommentTask(String data){
            jsonData=data;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            String url=urlBase+"/create_comment";
            try{
                response=httpUtil.post(url,jsonData);
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

    private class UpdateStar extends AsyncTask<Void,Void,Boolean>{
        private String commentID;
        private int star;
        private String response;
        UpdateStar(String id,int s){
            commentID=id;
            star=s;
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            String url=urlBase+"/update_comment_star";
            try{
                response= httpUtil.post(url,"{\"commentID\":\""+commentID+"\",\"star\":\""+star+"\"}");
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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Comment comment=CommentList.get(position);
        holder.userName.setText(comment.getUserName());
        holder.msg.setText(comment.getMsg());
        holder.star_txt.setText(comment.getStar()+"");
        Glide.with(context).load(comment.getImagePath()).thumbnail(0.2f).into(holder.userImage);
        new GetAnswerTask(holder,comment.getCommentID(),comment.getVideoID()).execute();
    }
    private class GetAnswerTask extends AsyncTask<Void,Void,Boolean>{
        ViewHolder holder;
        private String youCommentID;
        private String videoID;
        private String response;
        GetAnswerTask(ViewHolder h,String ycid,String vid){
            holder=h;
            youCommentID=ycid;
            videoID=vid;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url=urlBase+"/get_comment_answer";
            try{
                response=httpUtil.post(url,"{\"youCommentID\":\""+youCommentID+"\",\"videoID\":\""+videoID+"\"}");
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                if(!response.equals("true")){
                    Comment comment=gson.fromJson(response,Comment.class);
                    holder.answerLayout.setVisibility(View.VISIBLE);
                    Glide.with(context).load(comment.getImagePath()).thumbnail(0.2f).into(holder.answerImage);
                    holder.answerUserName.setText(comment.getUserName());
                    holder.answerMsg.setText(comment.getMsg());
                    holder.msg.setClickable(false);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        System.out.println("99999999999999");
        return CommentList.size();
    }
}
