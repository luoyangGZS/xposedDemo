package com.example.host.mytestapp.uitl;

import android.os.Handler;
import android.os.Message;

import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpTest {

    public static  void okHttpGetTest(String Url, final int what, final Handler handler){
                    final OkHttpClient client = new OkHttpClient();
            final Request request=new Request.Builder()
                 .get()
                .url(Url)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
                        Lg.i("打印GET响应的数据：" + response.body().toString());
                        String data = null;
                        try {
                            data = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Message msg = Message.obtain();
                        // 消息对象可以携带数据
                        msg.obj = data;//response.body();
                        msg.what = what;
                        handler.sendMessage(msg);
                    } else {
                        throw new IOException("Unexpected code " + response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public static void okHttpGetTest1(String Url, final Handler handler, final TextView textView){
        //1,创建OKHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //2,创建一个Request
        Request request = new Request.Builder().url(Url).build();
        //3,创建一个call对象
        Call call = mOkHttpClient.newCall(request);
        //4,将请求添加到调度中
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String message = response.body().string();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("okHttpGet"+message);

                        }
                    });

                }
            }

        });


    }
}
