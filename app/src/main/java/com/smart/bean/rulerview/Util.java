package com.smart.bean.rulerview;

import android.content.Context;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;


import java.util.concurrent.locks.ReentrantLock;

/**
 * auther   : bean
 * on       : 2017/11/3
 * QQ       : 596928539
 * github   : https://github.com/Xbean1024
 * function :
 */

public class Util {

    /**
     * @return 返回全局的上下文
     */
    public static Context getContext(){
        return MyApp.mContext;
    }

    /**
     * @return 获取主线程的handler
     */
    public static Handler getHandler(){
        return MyApp.mHandler;
    }

    /**
     * @param colorId 颜色ID
     * @return 颜色值
     */
    public static int getColor(int colorId){
        return getContext().getResources().getColor(colorId);
    }

    /**
     * @param viewId id
     * @return 返回指定布局的视图
     */
    public static View getView(int viewId){
        return View.inflate(getContext(),viewId,null);
    }

    /** 获取在 xml 中定义的 string-array 数组
     * @param strArrayId
     * @return
     */
    public static String[] getStringArray(int strArrayId){
        return getContext().getResources().getStringArray(strArrayId);
    }

    /** 将dp转换位px
     * @param dp  dp
     * @return px
     */
    public static int dp2px(int dp){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density+0.5);
    }
    public static int px2dp(int px){
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (px/density +0.5);
    }

    /** 获取屏幕的宽
     * @return 宽
     */
    public static int getWindowWidth(){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }
    /** 获取屏幕的高
     * @return 高
     */
    public static int getWindowHeight(){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }
    public static void showToast(String msg){
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
