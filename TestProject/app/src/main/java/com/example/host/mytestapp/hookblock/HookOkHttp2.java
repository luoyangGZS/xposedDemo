package com.example.host.mytestapp.hookblock;

import com.example.host.mytestapp.base.BaseHook;
import com.example.host.mytestapp.uitl.ConUrl;
import com.example.host.mytestapp.uitl.Lg;

import okhttp3.Request;


public class HookOkHttp2 extends BaseHook {

    public static String ClassName = Request.class.getName();
    public static String MethodUrl ="url";
    public HookOkHttp2(ClassLoader ld) {
        super(ld);
        addHookWithOnlyMethodName(ClassName,MethodUrl);
        Lg.i("--------okhttp2------------");
    }

    @Override
    protected void afterHookedMethod(MethodHookParam methodHookParam){

    }

    @Override
    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        String obj = (String) methodHookParam.args[0];
        if (obj.contains(".jpg")||obj.contains(".jpeg")||obj.contains(".gif")||obj.contains(".png")||obj.contains(".bmp")||obj.equals(ConUrl.urlBlackList[0])){
            methodHookParam.args[0] = ConUrl.imageUrl[0];
         Lg.i("--------okhttp2------------obj:" + obj);
        }
    }
}
