package com.example.host.mytestapp.uitl;

import android.app.Activity;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

/**
 * Class：SimulateTouch
 * @author: Host-0304 LL
 * @Date: 2018/7/23   15:49
 * @Description: 模拟点击
 *
 */

public class SimulateTouch {

    /**  模拟点击  拿到整个Activity视图的view   */
    public static void simulateTouchEvent(Activity activity, Float x, Float y) {
        long downTime = SystemClock.uptimeMillis();
        long eventTime = SystemClock.uptimeMillis() + 100;
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_DOWN, x, y, metaState);
        activity.dispatchTouchEvent(motionEvent);
        MotionEvent upEvent = MotionEvent.obtain(downTime + 1000, eventTime + 1000,
                MotionEvent.ACTION_UP, x, y, metaState);
        activity.dispatchTouchEvent(upEvent);
        motionEvent.recycle();
        upEvent.recycle();
    }


    /**  模拟点击  拿到视图的小块view   */
    public static void simulateTouchEvent(View view, Float x, Float y) {
        long downTime = SystemClock.uptimeMillis();//点击的时间
        long eventTime = SystemClock.uptimeMillis() + 100;//点击结束的时间
        int metaState = 0;
        MotionEvent motionEvent = MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, metaState);//模拟（点击）的事件
        view.dispatchTouchEvent(motionEvent);
        MotionEvent upEvent = MotionEvent.obtain(downTime + 1000, eventTime + 1000, MotionEvent.ACTION_UP, x, y, metaState);//模拟（完成点击）的事件
        view.dispatchTouchEvent(upEvent);
        motionEvent.recycle();
        upEvent.recycle();
    }

    /**
     * @param view 点击的视图
     * @param activity 当前活动界面
     * @param addx 基于view上加的x增量
     * @param addy 基于view上加的y增量
     * Activity点击公共部分抽取
     */
    public static void activityViewTouchEvent(View view,Activity activity,float addx,float addy){

        int[] outLocation = new int[2];
        /* getLocationInWindow
         * 这个方法是将view的左上角坐标存入数组中.此坐标是相对当前activity而言.
         * 若是普通activity,则y坐标为可见的状态栏高度+可见的标题栏高度+view左上角到标题栏底部的距离.
         * 可见的意思是:在隐藏了状态栏/标题栏的情况下,它们的高度以0计算.
         * */
        view.getLocationInWindow(outLocation);
        int x1 = outLocation[0];
        int y1 = outLocation[1];
        Lg.i( "-----------------"+activity+"."+view+"(x,y)" + x1 + "," + y1);
        simulateTouchEvent(activity,x1 + addx, y1 + addy);
    };
}
