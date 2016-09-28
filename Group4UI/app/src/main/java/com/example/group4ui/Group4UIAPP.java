package com.example.group4ui;


import android.app.Application;
import android.util.Log;

import com.iflytek.cloud.SpeechUtility;



public class Group4UIAPP extends Application{
  
	@Override
	public void onCreate() {
		// 应用程序入口处调用,避免手机内存过小，杀死后台进程,造成SpeechUtility对象为null
		// 设置你申请的应用appid
		Log.d("fuck", "in app");
		SpeechUtility.createUtility(Group4UIAPP.this, "appid="+getString(R.string.app_id));
		
		super.onCreate();
	}
}