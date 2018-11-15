package com.example.host.mytestapp.hookblock;

import com.bumptech.glide.Glide;
import com.example.host.mytestapp.base.BaseHook;
import com.example.host.mytestapp.uitl.ConUrl;
import com.example.host.mytestapp.uitl.Lg;


import java.util.regex.Pattern;

import de.robv.android.xposed.XposedBridge;
import okhttp3.HttpUrl;
import okhttp3.Request;


public class HookOkHttp extends BaseHook {

    public static String ClassName = Request.Builder.class.getName();
    public static String MethodUrl ="url";

//    public static String ClassName2 = HttpUrl.class.getName();
//    public static String MethodUrl2 ="parse";
//
//    public static String ClassName3 = HttpUrl.class.getName();
//    public static String MethodUrl3 ="get";


    public HookOkHttp(ClassLoader ld) {
        super(ld);
        addHookWithOnlyMethodName(ClassName,MethodUrl);
//        addHookWithOnlyMethodName(ClassName2,MethodUrl2);
//        addHookWithOnlyMethodName(ClassName3,MethodUrl3);
    //    Lg.i("--------okhttp------------");
    }

    @Override
    protected void afterHookedMethod(MethodHookParam methodHookParam){

    }

    @Override
    protected void beforeHookedMethod(MethodHookParam methodHookParam) {
        String obj = (String) methodHookParam.args[0];
        try {
            for (int i = 0; i < ConUrl.pictureFormat.length; i++) {
                if (obj.contains(ConUrl.pictureFormat[i])) {
                    //if (obj.contains(".jpg") || obj.contains(".webp") || obj.contains(".jpeg") || obj.contains(".gif") || obj.contains(".png") || obj.contains(".bmp")) {
                    methodHookParam.args[0] = ConUrl.imageUrl[0];
                    Lg.i("Fresco--------Uri.parse------------obj:" + obj);
                }
            }
        }
        catch(Throwable e){
            Lg.e(e);
        }


        try{
            for (int i=0;i<ConUrl.regex.length;i++) {
                boolean m = Pattern.matches(ConUrl.regex[i], obj);
                if (m) {
                    methodHookParam.args[0] = ConUrl.imageUrl[0];
                    Lg.i("okhttp-----------Pattern.matches---------obj:" + obj);
                }
            }
        }
        catch (Throwable e)
        {
            Lg.e("okhttp--------Pattern.matches,Exception------------:"+e);

        }
    }
}
