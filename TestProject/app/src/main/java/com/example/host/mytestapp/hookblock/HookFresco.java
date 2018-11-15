package com.example.host.mytestapp.hookblock;

import android.content.Context;
import android.net.Uri;

import com.example.host.mytestapp.base.BaseHook;
import com.example.host.mytestapp.uitl.ConUrl;
import com.example.host.mytestapp.uitl.Lg;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;


public class HookFresco extends BaseHook {

    public static String ClassName =Uri.class.getName();
    public static String MethodUrl ="parse";


    public HookFresco(ClassLoader ld) {
        super(ld);
        addHook(ClassName,MethodUrl,String.class.getName());
        Lg.i("--------Fresco--------Uri.parse------------");

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
                    Lg.i("Fresco-----------Pattern.matches---------obj:" + obj);
                }
            }
        }
        catch (Throwable e)
        {
            Lg.e("Fresco--------Pattern.matches,Exception------------:"+e);

        }



    }
}
