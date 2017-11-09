package com.smart.bean.rulerview;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * auther   : bean
 * on       : 2017/11/3
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function :
 */

public class MyApp extends Application {
    public static Context mContext;
    public static Handler mHandler;
    public static Thread mMainThread;
    public static int mMainThreadId;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this.getApplicationContext();
        mHandler = new Handler();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();//获取当前线程的ID
    }
}
