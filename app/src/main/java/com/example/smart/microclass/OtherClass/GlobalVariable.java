package com.example.smart.microclass.OtherClass;

import com.example.smart.microclass.OtherClass.HTTP.HttpUtil;
import com.google.gson.Gson;

/**
 * Created by smart on 2017/4/1.
 */

public class GlobalVariable {
    public static Teacher teacher;
    public static String urlBase="http://192.168.1.78:8080";
    //public static String response;   could have problem in multiThread;
    public static Gson gson=new Gson();
    public static HttpUtil httpUtil=new HttpUtil();
    public static String isSuccess="false";
}
