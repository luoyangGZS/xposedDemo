package com.example.host.mytestapp.uitl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import de.robv.android.xposed.XposedBridge;


public class HttpURLConnectionTest {




    public static void HUrlCGetIMage(String url,int what,Handler handler){ try{   //拿到链接
        URL url1 = new URL(url);

        //打开链接
        HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
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
            Lg.i("0000000000000000000000-"+connection);
            Lg.i("0000000000000000000000-"+connection.getClass());
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            Message msg = Message.obtain();
            msg.obj = bitmap;
            msg.what = what;
            handler.sendMessage(msg);}
        connection.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
        Lg.e(e);
    }}
    /**
     * @param url
     * @param what
     * @param handler
     */
    public static void HUrlCGetTest(String url,int what,Handler handler){
        try {
            // 1. 得到访问地址的URL
            URL url1 = new URL(url);
            // 2. 得到网络访问对象java.net.HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            /* 3. 设置请求参数（过期时间，输入、输出流、访问方式），以流的形式进行连接 */
            // 设置是否向HttpURLConnection输出
            connection.setDoOutput(false);
            // 设置是否从httpUrlConnection读入
            connection.setDoInput(true);
            // 设置请求方式
            connection.setRequestMethod("GET");
            // 设置是否使用缓存
            connection.setUseCaches(true);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            connection.setInstanceFollowRedirects(true);
            // 设置超时时间
            connection.setConnectTimeout(3000);
            // 连接
            connection.connect();
            // 4. 得到响应状态码的返回值 responseCode
            int code = connection.getResponseCode();
            // 5. 如果返回值正常，数据在网络中是以流的形式得到服务端返回的数据
            String str = "";
            if (code == 200) { // 正常响应
                // 从流中读取响应信息
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                Lg.i("0000000000000000000000-"+connection);
                Lg.i("0000000000000000000000-"+connection.getClass());
                String line = null;
                while ((line = reader.readLine()) != null) {
                    str += line + "\n";
                }
                reader.close(); // 关闭流
                Message msg = Message.obtain();;
                // 消息对象可以携带数据
                msg.obj = str;
                msg.what = what;
                handler.sendMessage(msg);
            }
            // 6. 断开连接，释放资源
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
            Lg.e(e);
        }

    }

    public static void HUrlCPostTest(String postUrl,String post,int what, Handler handler ){
               try{
                   URL url = new URL(postUrl);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");// 提交模式
                    // conn.setConnectTimeout(10000);//连接超时 单位毫秒
                    // conn.setReadTimeout(2000);//读取超时 单位毫秒
                   // 设置是否向HttpURLConnection输出
                   httpURLConnection.setDoOutput(false);
                   // 设置是否从httpUrlConnection读入
                   httpURLConnection.setDoInput(true);

                    // 获取URLConnection对象对应的输出流
                    PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
                    // 发送请求参数
                    printWriter.write(post);//post的参数 xx=xx&yy=yy
                    // flush输出流的缓冲
                    printWriter.flush();

                    String str = "";
                    // 从流中读取响应信息
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream()));

                   Lg.i("0000000000000000000000-"+httpURLConnection);
                   Lg.i("0000000000000000000000-"+httpURLConnection.getClass());
                     String line = null;
                      while ((line = reader.readLine()) != null) {
                        str += line + "\n";
                    }
                   // 关闭流
                   reader.close();
                    Message msg = Message.obtain();;
                    // 消息对象可以携带数据
                    msg.obj = str;
                    msg.what = what;
                    handler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }


    }


}
