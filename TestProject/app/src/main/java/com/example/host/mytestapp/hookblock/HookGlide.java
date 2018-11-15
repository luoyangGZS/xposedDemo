package com.example.host.mytestapp.hookblock;

import android.app.Activity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.host.mytestapp.base.BaseHook;
import com.example.host.mytestapp.uitl.ConUrl;
import com.example.host.mytestapp.uitl.Lg;

import java.util.regex.Pattern;

import okhttp3.Request;


public class HookGlide extends BaseHook {

    public static String ClassName =RequestManager.class.getName();
    public static String MethodUrl ="load";


    public HookGlide(ClassLoader ld) {
        super(ld);
        addHook(ClassName,MethodUrl,String.class.getName());
        Lg.i("--------Glide.load------------");

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
                    Lg.i("Glide-----------Pattern.matches---"+m+"------obj:" + obj);
                }
            }
        }
        catch (Throwable e)
        {
            Lg.e("Glide--------Pattern.matches,Exception------------:"+e);

        }
    }
}
