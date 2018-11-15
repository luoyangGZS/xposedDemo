package com.example.host.mytestapp.hookblock;

import android.media.Image;
import android.net.Uri;
import android.widget.ImageView;

import com.example.host.mytestapp.base.BaseHook;
import com.example.host.mytestapp.uitl.ConUrl;
import com.example.host.mytestapp.uitl.Lg;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.regex.Pattern;


public class HookImageLoader extends BaseHook {

    public static String ClassName =ImageLoader.class.getName();
    public static String MethodUrl ="displayImage";


    public HookImageLoader(ClassLoader ld) {
        super(ld);
        addHookWithOnlyMethodName(ClassName,MethodUrl);
        Lg.i("--------ImageLoader.displayImage------------");

    }

    @Override
    protected void afterHookedMethod(MethodHookParam methodHookParam){
        //ImageLoader.getInstance().displayImage("", (ImageView) methodHookParam.thisObject);

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
                    Lg.i("ImageLoader-----------Pattern.matches--------obj:" + obj);
                }
            }
        }
        catch (Throwable e)
        {
            Lg.e("ImageLoader--------Pattern.matches,Exception------------:"+e);

        }
    }
}
