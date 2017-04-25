package com.example.smart.microclass.HomePage;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.smart.microclass.OtherClass.PlayVideo.PlayVideo;
import com.example.smart.microclass.R;

import java.util.List;


/**
 * Created by smart on 2017/4/2.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder>{
    private Context context;
    private List<Video> videoList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView videoImage;
        TextView videoName;
        public ViewHolder(View view){
            super(view);
            System.out.println("11111111111111");
            cardView=(CardView)view;
            videoImage =(ImageView)view.findViewById(R.id.video_img);
            videoName=(TextView)view.findViewById(R.id.video_name);
            System.out.println("22222222222222");
        }
    }

    public VideoAdapter(List<Video> videolist){
        System.out.println("33333333333333");
        this.videoList=videolist;
        for(int i=0;i<videoList.size();i++){
            System.out.println(videoList.get(i).getVideoName());
        }
        System.out.println("44444444444444");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("55555555555555");
        if(context==null){
            context=parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.video_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Video video=videoList.get(position);
                Toast.makeText(v.getContext(),"you clicked view "+video.getVideoName(), Toast.LENGTH_SHORT).show();
                //click thing
                Intent intent=new Intent();
                intent.putExtra("videoPath",video.getVideoPath());
                System.out.println("-----------------VideoAdapter----->videoID: "+video.getVideoID());
                intent.putExtra("videoID",video.getVideoID()+"");
                intent.putExtra("videoName",video.getVideoName());
                intent.setClass(context, PlayVideo.class);
                context.startActivity(intent);
            }
        });
        //other layout's click thing
        //return new ViewHolder(view);
        System.out.println("66666666666666");
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        System.out.println("onBindViewHolder, position: "+position);
        System.out.println("videoList.size(): "+videoList.size());
        //for(int i=0;i<videoList.size();i++){
        //    System.out.println(videoList.get(i).getVideoName());
        //    System.out.println(videoList.get(i).getVideoID());
        //    System.out.println(videoList.get(i).getVideoPath());
        //    System.out.println("------------------------------------");
        //}
        Video video=videoList.get(position);
        holder.videoName.setText(video.getVideoName());
        //Glide.with(context).load(video.getVideoID()).into(holder.videoImage);
        //new GetVideoThumbnail(video.getVideoPath(),holer.videoImage).execute();
        System.out.println("video.getvideoPath: "+video.getVideoPath());
        Glide.with(context).load(video.getVideoPath()).thumbnail(0.2f).into(holder.videoImage);
        //if(Vitamio.isInitialized(MyApplication.getContext())){
        //    holder.videoView.setVideoURI(Uri.parse(video.getVideoPath()));
        //}
        System.out.println("88888888888888");
    }

    @Override
    public int getItemCount() {
        System.out.println("99999999999999");
        return videoList.size();
    }

    //private class GetVideoThumbnail extends AsyncTask<Void,Void,Bitmap>{
    //    private String VideoUrl;
    //    private ImageView imageView;

    //    GetVideoThumbnail(String url,ImageView iv){
    //        this.VideoUrl=url;
    //        this.imageView=iv;
    //    }

    //    @Override
    //    protected Bitmap doInBackground(Void... params) {
    //        System.out.println("QQQQQQQQQQQQQQQQQQQ");
    //        System.out.println("videourl: "+VideoUrl);

    //        Bitmap bitmap= ThumbnailUtils.createVideoThumbnail(VideoUrl, MediaStore.Video.Thumbnails.MICRO_KIND);
    //        System.out.println("bitmap: "+bitmap);
    //        bitmap=ThumbnailUtils.extractThumbnail(bitmap,100,100,ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

    //        System.out.println("WWWWWWWWWWWWWWWWWWW");
    //        return bitmap;
    //    }

    //    @Override
    //    protected void onPostExecute(Bitmap bitmap) {
    //        System.out.println("EEEEEEEEEEEEEEEEEEE");
    //        System.out.println("bitmap: "+bitmap);
    //        imageView.setImageBitmap(bitmap);
    //    }
    //}
}
