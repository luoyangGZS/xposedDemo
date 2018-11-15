package com.example.host.mytestapp.hookblock;

import com.example.host.mytestapp.base.BaseHook;
import com.example.host.mytestapp.uitl.ConUrl;
import com.example.host.mytestapp.uitl.Lg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.regex.Pattern;

import de.robv.android.xposed.XposedBridge;


public class HookUrl extends BaseHook {

    public static String ClassName = URL.class.getName();
    public HookUrl(ClassLoader loader) {
        super(loader);
        addHookConstructor(ClassName, String.class);
        Lg.i("--------HttpURLConnection------------");

    }

    @Override
    protected void afterHookedMethod(MethodHookParam methodHookParam) {
      //   XposedBridge.log("--------addHookConstructor------------111111" + methodHookParam.thisObject);
    }

    /**
     * @param methodHookParam
     */
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
                   Lg.i("HttpURLConnection-----------Pattern.matches---------obj:" + obj);
               }
           }
        }
        catch (Throwable e)
        {
            Lg.e("HttpURLConnection--------Pattern.matches,Exception------------:"+e);

        }


    }
}
