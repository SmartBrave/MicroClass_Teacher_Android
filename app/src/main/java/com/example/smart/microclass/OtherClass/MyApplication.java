package com.example.smart.microclass.OtherClass;

import android.app.Application;
import android.content.Context;

/**
 * Created by smart on 2017/4/6.
 */

public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }
}
