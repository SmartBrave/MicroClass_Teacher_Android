package com.example.smart.microclass.Course;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.smart.microclass.OtherClass.MyApplication;
import com.example.smart.microclass.R;

/**
 * Created by smart on 2017/3/25.
 */

public class CourseFragment extends Fragment implements View.OnClickListener{

    private LinearLayout html;
    private LinearLayout JavaScript;
    private LinearLayout nodejs;
    private LinearLayout css;
    private LinearLayout php;
    private LinearLayout java;
    private LinearLayout python;
    private LinearLayout c;
    private LinearLayout cplusplus;
    private LinearLayout go;
    private LinearLayout csharp;
    private LinearLayout android;
    private LinearLayout ios;
    private LinearLayout mysql;
    private LinearLayout access;
    private LinearLayout oracle;
    private LinearLayout sqlserver;
    private LinearLayout mongodb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.tab02,container,false);

        html=(LinearLayout)view.findViewById(R.id.html);
        JavaScript=(LinearLayout)view.findViewById(R.id.JavaScript);
        nodejs=(LinearLayout)view.findViewById(R.id.nodejs);
        css=(LinearLayout)view.findViewById(R.id.css);
        php=(LinearLayout)view.findViewById(R.id.php);
        java=(LinearLayout)view.findViewById(R.id.java);
        python=(LinearLayout)view.findViewById(R.id.python);
        c=(LinearLayout)view.findViewById(R.id.c);
        cplusplus=(LinearLayout)view.findViewById(R.id.cplusplus);
        go=(LinearLayout)view.findViewById(R.id.go);
        csharp=(LinearLayout)view.findViewById(R.id.csharp);
        android=(LinearLayout)view.findViewById(R.id.android);
        ios=(LinearLayout)view.findViewById(R.id.ios);
        mysql=(LinearLayout)view.findViewById(R.id.mysql);
        access=(LinearLayout)view.findViewById(R.id.access);
        oracle=(LinearLayout)view.findViewById(R.id.oracle);
        sqlserver=(LinearLayout)view.findViewById(R.id.sqlserver);
        mongodb=(LinearLayout)view.findViewById(R.id.mongodb);

        html.setOnClickListener(this);
        JavaScript.setOnClickListener(this);
        nodejs.setOnClickListener(this);
        css.setOnClickListener(this);
        php.setOnClickListener(this);
        java.setOnClickListener(this);
        python.setOnClickListener(this);
        c.setOnClickListener(this);
        cplusplus.setOnClickListener(this);
        go.setOnClickListener(this);
        csharp.setOnClickListener(this);
        android.setOnClickListener(this);
        ios.setOnClickListener(this);
        mysql.setOnClickListener(this);
        access.setOnClickListener(this);
        oracle.setOnClickListener(this);
        sqlserver.setOnClickListener(this);
        mongodb.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        String type;
        switch (v.getId()){
            case R.id.html:
                type="html";
                break;
            case R.id.JavaScript:
                type="javascript";
                break;
            case R.id.nodejs:
                type="nodejs";
                break;
            case R.id.css:
                type="css";
                break;
            case R.id.php:
                type="php";
                break;
            case R.id.java:
                type="java";
                break;
            case R.id.python:
                type="python";
                break;
            case R.id.c:
                type="c";
                break;
            case R.id.cplusplus:
                type="cplusplus";
                break;
            case R.id.go:
                type="go";
                break;
            case R.id.csharp:
                type="csharp";
                break;
            case R.id.android:
                type="android";
                break;
            case R.id.ios:
                type="ios";
                break;
            case R.id.mysql:
                type="mysql";
                break;
            case R.id.access:
                type="access";
                break;
            case R.id.oracle:
                type="oracle";
                break;
            case R.id.sqlserver:
                type="sqlserver";
                break;
            case R.id.mongodb:
                type="mongodb";
                break;
            default:
                type="";
                break;
        }
        Intent intent=new Intent();
        intent.putExtra("type",type);
        System.out.println("aaaaaaaaaaaaaaaaaaa   type: "+type);
        intent.setClass(MyApplication.getContext(),VideoList.class);
        startActivity(intent);
    }

    public static final String TAG ="CourseFragmeng";

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
