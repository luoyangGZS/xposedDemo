package com.example.host.mytestapp.hookblock;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.host.mytestapp.base.BaseHook;
;
import com.example.host.mytestapp.uitl.ClassNameUitl;
import com.example.host.mytestapp.uitl.Lg;
import com.example.host.mytestapp.uitl.SimulateTouch;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;


public class HookActivity extends BaseHook{


    private static final String ONCREATE = "onCreate";

    public HookActivity(ClassLoader ld)
    {
        super(ld);
        try
        {
           // XposedBridge.log("hook----------1111111111------" );
            addHook(ClassNameUitl.HOOK_CLASS_NANI_PERSON_ACTIVITY, ONCREATE,Bundle.class);//Bundle.class.getName();
            //XposedBridge.log("hook----------22222222------" );
        }
        catch (Throwable e)
        {
            Lg.e(e);
        }
    }

    @Override
    protected void beforeHookedMethod(MethodHookParam param)
    {
        XposedBridge.log("hook----------1111"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
        XposedBridge.log("hook----------22222"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );

    }

    @Override
    protected void afterHookedMethod(MethodHookParam param)
    {

            try {
                Lg.i("hook----------1111"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
                final Activity activity2 = (Activity) param.thisObject;
                int  square_tab_id = activity2.getResources().getIdentifier("txt_person_square_tab","id","com.baidu.nani");
                final TextView square_textView = activity2.findViewById(square_tab_id);
                Lg.i( "1111--------------PersonActivity.onCreate()-square_tab_id = " + square_tab_id );
                if(square_textView.toString() !=null) {
                    Lg.i("111-------------square_tab_id--textView.toString():" + square_textView.toString());
                }
                //延时3秒找到控件坐标完成模拟点击
                square_textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SimulateTouch.activityViewTouchEvent(square_textView,activity2,10,210);
                    }
                }, 3000L);//数值默认为int,加L表示 长整型 Long 八个字节
            } catch (Throwable e) {
                e.printStackTrace();
            }
            Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully." );

        }
    }



//    public HookActivity(String className,ClassLoader ld,String methodName,String idn,String defPackage){
//        super(className,ld, methodName,idn,defPackage);
//        try{
//            addHook(className,ld,methodName);
//        }
//        catch (Throwable e){
//            Lg.e(e);
//        }
//    }
//    @Override
//    protected void beforeHookedMethod(MethodHookParam param) throws Throwable
//    {
//    }
//
//    @Override
//    protected void afterHookedMethod(MethodHookParam param) throws Throwable
//    {
//            super.afterHookedMethod(param);
//            try {
//                Lg.i("hook----------1111"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
//                final Activity activity2 = (Activity) param.thisObject;
//                int  square_tab_id = activity2.getResources().getIdentifier(idn,"id",defPackage);
//                final TextView square_textView = activity2.findViewById(square_tab_id);
//                Lg.i( "1111--------------PersonActivity.onCreate()-square_tab_id = " + square_tab_id );
//                if(square_textView.toString() !=null) {
//                    Lg.i("111-------------square_tab_id--textView.toString():" + square_textView.toString());
//                }
//                //延时3秒找到控件坐标完成模拟点击
//                square_textView.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        SimulateTouch.activityViewTouchEvent(square_textView,activity2,10,210);
//                    }
//                }, 3000L);//数值默认为int,加L表示 长整型 Long 八个字节
//            } catch (Throwable e) {
//                e.printStackTrace();
//            }
//            Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully." );
//
//        }
//    }





