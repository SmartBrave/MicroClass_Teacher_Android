package com.example.smart.microclass.OtherClass.HTTP;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.smart.microclass.OtherClass.GlobalVariable.teacher;

/**
 * Created by smart on 2017/3/27.
 */

public class HttpUtil{

    private static OkHttpClient client;
    public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
    public static final MediaType IMAGE=MediaType.parse("image/png");
    public static final MediaType   VIDEO=MediaType.parse("video/mp4");

    public HttpUtil(){
        client=new OkHttpClient();
    }

    public String get(String url)throws IOException{
        Request request=new Request.Builder().url(url).build();
        Response response=client.newCall(request).execute();
        return response.body().string();
    }

    public String getVideoWithTag(String url,String tag)throws IOException{
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        //builder.addFormDataPart("userID",teacher.userID+"",RequestBody.create(JSON,"{\"userID\":\""+teacher.userID+"\"}"));
        builder.addFormDataPart("tag",tag,RequestBody.create(JSON,"{\"tag\":\""+tag+"\"}"));

        MultipartBody requestBody=builder.build();
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=client.newCall(request);
        Response response=call.execute();
        if(!response.isSuccessful()){
            throw new IOException("Unexpected code "+response);
        }
        return response.body().string();
    }
    public String getVideo(String url,String tag)throws IOException{
        System.out.println("oooooooooooooo");
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        System.out.println("pppppppppppppp");
        builder.addFormDataPart("userID",teacher.userID+"",RequestBody.create(JSON,"{\"userID\":\""+teacher.userID+"\"}"));
        builder.addFormDataPart("tag",tag,RequestBody.create(JSON,"{\"tag\":\""+tag+"\"}"));
        System.out.println("qqqqqqqqqqqqqq");

        MultipartBody requestBody=builder.build();
        System.out.println("rrrrrrrrrrrrrr");
        Request request=new Request.Builder().url(url).post(requestBody).build();
        System.out.println("ssssssssssssss");
        Call call=client.newCall(request);
        System.out.println("tttttttttttttt");
        Response response=call.execute();
        System.out.println("uuuuuuuuuuuuuu");
        if(!response.isSuccessful()){
            System.out.println("vvvvvvvvvvvvvv");
            throw new IOException("Unexpected code "+response);
        }
        System.out.println("wwwwwwwwwwwwww");
        return response.body().string();
    }

    public String post(String url,String json) throws IOException{
        System.out.println("1111111111111111111");
        System.out.println("url: "+url+"       json: "+json);
        RequestBody body=RequestBody.create(JSON,json);
        System.out.println("2222222222222222222");
        Request request=new Request.Builder().url(url).post(body).build();
        System.out.println("3333333333333333333");
        Call call=client.newCall(request);
        System.out.println("4444444444444444444");
        Response response=call.execute();
        System.out.println("5555555555555555555");
        if(!response.isSuccessful()){
            System.out.println("6666666666666666666");
            throw new IOException("Unexpected code "+response);
        }
        System.out.println("7777777777777777777");
        return response.body().string();
    }
    public boolean uploadImage(String url,List<String> imagePaths) throws IOException{
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(int i=0;i<imagePaths.size();i++){
            File f=new File(imagePaths.get(i));
            if(f!=null){
                builder.addFormDataPart("img",f.getPath(),RequestBody.create(IMAGE,f));
            }
        }
        builder.addFormDataPart("userID",teacher.userID+"",RequestBody.create(JSON,"{\"userID\":\""+teacher.userID+"\"}"));
        MultipartBody requestBody=builder.build();
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=client.newCall(request);
        Response response=call.execute();
        if(!response.isSuccessful()){
            throw new IOException("Unexpected code "+response);
        }
        return true;
    }
    public boolean uploadVideo(String url,List<String> videoPaths) throws IOException{
        MultipartBody.Builder builder=new MultipartBody.Builder().setType(MultipartBody.FORM);
        for(int i=0;i<videoPaths.size();i++){
            File f=new File(videoPaths.get(i));
            if(f!=null){
                builder.addFormDataPart("video",f.getPath(),RequestBody.create(VIDEO,f));
            }
        }
        builder.addFormDataPart("userID",teacher.userID+"",RequestBody.create(JSON,"{\"userID\":\""+teacher.userID+"\"}"));
        MultipartBody requestBody=builder.build();
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=client.newCall(request);
        Response response=call.execute();
        if(!response.isSuccessful()){
            throw new IOException("Unexpected code "+response);
        }
        return true;
    }

}

//public class HttpUtil {
//    public static String post(String address,String data) {
//        HttpURLConnection connection = null;
//        try {
//            URL url = new URL(address);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setConnectTimeout(8000);
//            connection.setReadTimeout(8000);
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
//            out.writeBytes(data);
//            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder response = new StringBuilder();
//            String line;
//            System.out.println("555555555555555");
//            while ((line = reader.readLine()) != null) {
//                System.out.println("666666666666666666");
//                response.append(line);
//            }
//            return response.toString();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return e.getMessage();
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//    }
//    public static String get(String address){
//        HttpURLConnection connection=null;
//        try{
//            URL url=new URL(address);
//            connection=(HttpURLConnection)url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setConnectTimeout(8000);
//            connection.setReadTimeout(8000);
//            connection.setDoInput(true);
//            connection.setDoOutput(true);
//            InputStream in=connection.getInputStream();
//            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
//            StringBuilder response=new StringBuilder();
//            String line;
//            System.out.println("555555555555555");
//            while((line=reader.readLine())!=null){
//                System.out.println("666666666666666666");
//                response.append(line);
//            }
//            return response.toString();
//        }catch(Exception e){
//            e.printStackTrace();
//            return e.getMessage();
//        }finally {
//            if(connection!=null){
//                connection.disconnect();
//            }
//        }
//    }
//
//
//    //public static boolean isSuccess;
//    //public static boolean sendOkHttpRequest(String address,okhttp3.Callback callback){
//    //    System.out.println("xxxxxxxxxxxxxxxxx");
//    //    OkHttpClient client=new OkHttpClient();
//    //    System.out.println("yyyyyyyyyyyyyyyyy");
//    //    Request request=new Request.Builder().url(address).build();
//    //    System.out.println("zzzzzzzzzzzzzzzzz");
//    //    client.newCall(request).enqueue(callback);
//    //    System.out.println("wwwwwwwwwwwwwwwww");
//    //    return isSuccess;
    //}
//}
