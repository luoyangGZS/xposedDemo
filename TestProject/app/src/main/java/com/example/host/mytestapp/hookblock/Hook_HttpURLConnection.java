package com.example.host.mytestapp.hookblock;

import com.example.host.mytestapp.base.BaseHook;
import com.example.host.mytestapp.uitl.ConUrl;
import com.example.host.mytestapp.uitl.Lg;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import de.robv.android.xposed.XposedBridge;


public class Hook_HttpURLConnection extends BaseHook{

    private static String ClassName ="com.android.okhttp.internal.huc.HttpURLConnectionImpl";
    //private static String ClassName = HttpURLConnection.class.getName();
    private static String GET_INPUT_STREAM ="getInputStream";

    public Hook_HttpURLConnection(ClassLoader loader){
        super(loader);

        addHook(ClassName,GET_INPUT_STREAM);
    }

    @Override
    protected void afterHookedMethod(MethodHookParam methodHookParam) {


        }




    @Override
    protected void beforeHookedMethod(MethodHookParam methodHookParam) {


        XposedBridge.log("---------------hook111111111 :"+methodHookParam.thisObject);
        HttpURLConnection httpURLConnection = (HttpURLConnection) methodHookParam.thisObject;
        Lg.i("---------------hook111111111 :"+httpURLConnection.getURL());
        if(httpURLConnection.getURL().equals(ConUrl.imageUrl[0])) {
            try {
                URL url = new URL(ConUrl.imageUrl[1]);

                //打开链接
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //设置是否输出
                connection.setDoOutput(false);
                //设置是否读入
                connection.setDoInput(true);
                //设置请求方式
                connection.setRequestMethod("GET");
                //设置是否缓存
                connection.setUseCaches(true);
                //设置是否重定向
                connection.setInstanceFollowRedirects(true);
                //设置超时时间
                connection.setConnectTimeout(3000);
                //连接
                connection.connect();
                //得到响应码
                int code = connection.getResponseCode();
                if (code == 200) {//正常响应
                    // BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    InputStream in = connection.getInputStream();
                    methodHookParam.setResult(in);}


                Lg.i("-------------hook22222222222:"+connection.getURL());


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
