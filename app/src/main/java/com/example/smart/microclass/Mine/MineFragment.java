package com.example.smart.microclass.Mine;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smart.microclass.Mine.Login.LoginActivity;
import com.example.smart.microclass.Mine.MineInformation.MineInformation;
import com.example.smart.microclass.OtherClass.MyApplication;
import com.example.smart.microclass.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.smart.microclass.OtherClass.GlobalVariable.httpUtil;
import static com.example.smart.microclass.OtherClass.GlobalVariable.isSuccess;
import static com.example.smart.microclass.OtherClass.GlobalVariable.teacher;
import static com.example.smart.microclass.OtherClass.GlobalVariable.urlBase;


public class MineFragment extends Fragment implements View.OnClickListener{

    private LinearLayout login;
    private LinearLayout upload;
    private LinearLayout download;
    private LinearLayout settings;
    private LinearLayout unloginLayout;
    private LinearLayout loginLayout;
    private ImageView image;
    private ImageView loginImage;
    private TextView text;
    private boolean isInit=false;

    public static final int CHOOSE_USER_PHOTO=2;
    public static final int SELECT_VIDEO=3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.tab04,container,false);

        isSuccess=new String("");
        login=(LinearLayout) view.findViewById(R.id.mine_login);
        upload= (LinearLayout) view.findViewById(R.id.mine_upload);
        download= (LinearLayout) view.findViewById(R.id.mine_download);
        settings= (LinearLayout) view.findViewById(R.id.mine_settings);
        unloginLayout=(LinearLayout)view.findViewById(R.id.mine_unlogin_layout);
        loginLayout=(LinearLayout)view.findViewById(R.id.mine_login_layout);
        image=(ImageView)view.findViewById(R.id.mine_unlogin_img);
        text=(TextView)view.findViewById(R.id.mine_unlogin_text);
        loginImage=(ImageView)view.findViewById(R.id.mine_login_img);

        login.setOnClickListener(this);
        upload.setOnClickListener(this);
        download.setOnClickListener(this);
        settings.setOnClickListener(this);
        //image.setOnClickListener(this);
        loginImage.setOnClickListener(this);

        return view;
    }
    private void init(){
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.mine_login_img:
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                }else{
                    openAlbum();
                }
                break;
            case R.id.mine_unlogin_img:
            case R.id.mine_login:
            case R.id.mine_unlogin_text:
            case R.id.mine_login_text:
                if(!isLogin()){
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),LoginActivity.class);
                    startActivityForResult(intent,1);
                }else {
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),MineInformation.class);
                    startActivity(intent);
                }
                break;
            case R.id.mine_upload:
            case R.id.mine_upload_img:
            case R.id.mine_upload_text:
                if(!isLogin()){
                    Toast.makeText(MyApplication.getContext(), R.string.not_login, Toast.LENGTH_SHORT).show();
                    break;
                }
                if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},2);
                }else{
                    selectVideo();
                }
                break;
            case R.id.mine_download:
            case R.id.mine_download_img:
            case R.id.mine_download_text:
                if(!isLogin()){
                    Toast.makeText(MyApplication.getContext(), R.string.not_login, Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            case R.id.mine_settings:
            case R.id.mine_settings_img:
            case R.id.mine_settings_text:
                if(!isLogin()){
                    Toast.makeText(MyApplication.getContext(), R.string.not_login, Toast.LENGTH_SHORT).show();
                    break;
                }
                break;
            default:
                break;
        }
    }
    private void selectVideo(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("video/*");
        startActivityForResult(intent,SELECT_VIDEO);
    }
    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_USER_PHOTO);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else{
                    Toast.makeText(getActivity(),R.string.denied_permission,Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    selectVideo();
                }else{
                    Toast.makeText(getActivity(),R.string.denied_permission,Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public boolean isLogin(){
        return isSuccess.equals("success");
    }

    //access data from login page
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode){
            case 1:
                if(resultCode==RESULT_OK){
                    isSuccess=data.getStringExtra("isSuccess");
                    if(isSuccess.equals("success")){
                        unloginLayout.setVisibility(View.GONE);
                        loginLayout.setVisibility(View.VISIBLE);
                        image=(ImageView) getView().findViewById(R.id.mine_login_img);
                        text=(TextView) getView().findViewById(R.id.mine_login_text);
                        if(teacher.userName.equals("new user")){
                            text.setText(R.string.new_user);
                        }else{
                            text.setText(teacher.userName);
                        }
                    }else{
                    }
                }
                break;
            case CHOOSE_USER_PHOTO:
                if(resultCode==RESULT_OK){
                    String imagePath;
                    if(Build.VERSION.SDK_INT>=19){
                        //4.4 and higher system version use this func
                        imagePath=handleImageOnKitKat(data,"image");
                    }else{
                        //4.4 lower system version use this func
                        imagePath=handleImageBeforeKitKat(data,"image");
                    }
                    new UploadUserImagetoServerTask(imagePath).execute();
                    displayImage(imagePath);
                }
                break;//一个break害死人啊
            case SELECT_VIDEO:
                if(resultCode==RESULT_OK){
                    String videoPath;
                    if(Build.VERSION.SDK_INT>=19){
                        //4.4 and higher system version use this func
                        videoPath=handleImageOnKitKat(data,"video");
                    }else{
                        //4.4 lower system version use this func
                        videoPath=handleImageBeforeKitKat(data,"video");
                    }
                    new UploadVideotoServerTask(videoPath).execute();
                }
                break;
            default:
                break;
        }
    }


    @TargetApi(19)
    private String handleImageOnKitKat(Intent data,String type){
        String Path=null;
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(getActivity(),uri)){
            //if it's Uri of type document,handle it by document
            String docID=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docID.split(":")[1];
                String selection;
                if(type.equals("image")){
                    selection= MediaStore.Images.Media._ID+"="+id;
                    Path=getPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection,"image");
                }else if(type.equals("video")){
                    selection=MediaStore.Video.Media._ID+"="+id;
                    Path=getPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,selection,"video");
                }
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docID));
                if(type.equals("image")){
                    Path=getPath(contentUri,null,"image");
                }else if(type.equals("video")){
                    Path=getPath(contentUri,null,"video");
                }
        }else if("content".equalsIgnoreCase(uri.getScheme())) {
                //if uri's type is content,handle it by normal func
                if (type.equals("image")) {
                    Path = getPath(uri, null, "image");
                } else if (type.equals("video")) {
                    Path = getPath(uri, null, "video");
                }
            }
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //if uri's type is file,get imagePath derictly
            Path=uri.getPath();
        }
        //System.out.println("path is : "+Path);
        return Path;
    }

    private String handleImageBeforeKitKat(Intent data,String type){
        Uri uri=data.getData();
        String Path;
        if(type.equals("image")){
            Path=getPath(uri,null,"image");
        }else{
            Path=getPath(uri,null,"video");
        }
        return Path;
    }
    private String getPath(Uri uri,String selection,String type){
        String path=null;
        //get image's actual path by Uri and selection
        Cursor cursor=getActivity().getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                if(type.equals("image")){
                    path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }else if(type.equals("video")){
                    path=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                }
            }
            cursor.close();
        }
        return path;
    }
    private void displayImage(String imagePath){
        if(imagePath!=null){
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            image.setImageBitmap(bitmap);
        }else{
            Toast.makeText(getContext(), "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    private class UploadVideotoServerTask extends AsyncTask<Void,Void,Boolean>{
        private List<String> VideoPath;
        UploadVideotoServerTask(String path){
            VideoPath=new ArrayList<>();
            VideoPath.add(path);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            String url=urlBase+"/upload_video";
            boolean flag=false;
            try{
                flag= httpUtil.uploadVideo(url,VideoPath);
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
            if(flag){
                return true;
            }else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
                Toast.makeText(getActivity(), R.string.upload_video_success, Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), R.string.upload_video_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class UploadUserImagetoServerTask extends AsyncTask<Void,Void,Boolean>{
        private List<String> ImagePath;
        UploadUserImagetoServerTask(String path){
            ImagePath=new ArrayList<>();
            ImagePath.add(path);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            String url=urlBase+"/upload_user_image";
            boolean flag=false;
            try{
                flag=httpUtil.uploadImage(url,ImagePath);
            }catch(IOException e){
                e.printStackTrace();
                return false;
            }
            if(flag){
                return true;
            }else{
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(aBoolean){
            }else{
                Toast.makeText(getActivity(), R.string.upload_user_image_fail, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //next function is test func,delete is ok
    public static final String TAG ="MineFragment";

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
    public void onStart() {
        super.onStart();
        //System.out.println(TAG+"onStart");
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
