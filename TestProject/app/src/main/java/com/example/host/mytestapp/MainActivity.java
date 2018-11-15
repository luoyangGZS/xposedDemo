package com.example.host.mytestapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.host.mytestapp.uitl.ConUrl;
import com.example.host.mytestapp.uitl.HttpURLConnectionTest;
import com.example.host.mytestapp.uitl.Lg;
import com.example.host.mytestapp.uitl.OkHttpTest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private ImageView imageView;


    private  String[] getUrl = {"https://ext.baidu.com/api/comment/v1/comment/getlist?appid=101&sid=114551_124692_109775_123799_123733_120179_124267_123019_118896_118862_118850_118818_118794_107315_117330_117428_124603_122790_124752_124619_123984_124560_124109_114820_124612_124525_124937_123980_120260_124030_124298_110085_121265_123290_116145&cuid=&isInf=1&start=0&num=2&use_uk=1&use_list=1&is_need_at=1&order=12&thread_id=1038000007854017&callback=_box_jsonp14",
    "https://ext.baidu.com/api/like/v1/common/list?ids=wenda_a36cfa1fef9400cb4a585467c178b6207b80507%2Cwenda_8aaca4122e0a64ebd29c5859f06f80919f747ff%2Cwenda_22dacd9bf32b6680f6f940503e1658da8c5deb9&get_type=2&sfrom=wenda&source=wenda_level1&_=1532402065553&callback=Zepto1532402064904"};

    private String[] postUrl ={"https://ext.baidu.com/api/comment/v1/comment/getlist?",
            "http://api.ksapisrv.com/rest/n/client/event/push?"};
    private String[] post ={"appid=101&sid=114551_124692_109775_123799_123733_120179_124267_123019_118896_118862_118850_118818_118794_107315_117330_117428_124603_122790_124752_124619_123984_124560_124109_114820_124612_124525_124937_123980_120260_124030_124298_110085_121265_123290_116145&cuid=&isInf=1&start=0&num=2&use_uk=1&use_list=1&is_need_at=1&order=12&thread_id=1038000007854017&callback=_box_jsonp14",
            "mod=LGE%28AOSP%20on%20HammerHead%29&lon=0&country_code=CN&did=ANDROID_5b3913b05600ead0&client_key=3c2cd3f3&app=0&net=WIFI&os=android&sig2=f4eaa69db3b78ae2e16db48cb9472d41&oc=HUAWEI&ud=0&c=HUAWEI&sys=ANDROID_6.0.1&appver=5.8.2.6462&ftt=&language=zh-cn&iuid=&lat=0&did_gt=1531914362568&ver=5.8&max_memory=192"};

    private String[] imageUrl ={"http://01.imgmini.eastday.com/mobile/20180724/20180724075423_c7794e7e26733e24f90ffe12e3512888_7_mwpm_03200403.jpg",
            "http://04.imgmini.eastday.com/mobile/20180724/20180724_02d69787c0d6f6ac6765bbd469b75bea_cover_mwpm_03200403.jpg"};


    // 此方法在主线程中调用，可以更新UI
    Handler  handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {

                    case 0:
                    // 将文本字符显示
                        textView1.setText("what=" + msg.what + ","+(String)msg.obj);
                        break;
                     case 1:
                        textView2.setText("what=" + msg.what + ","+(String)msg.obj);
                         break;
                     case 2:
                        textView3.setText("what=" + msg.what + ","+(String)msg.obj);
                         break;
                         case 3:
                      imageView.setImageBitmap((Bitmap) msg.obj);
                         break;
                default:
                    break;
            }
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.tV1);
        textView2 = findViewById(R.id.tV2);
        textView3 = findViewById(R.id.tV3);
        textView4 = findViewById(R.id.tV4);
        imageView = findViewById(R.id.iV);

    }

    public void getImageView(View view)  {
    Thread t = new Thread(){
        public void  run() {
            HttpURLConnectionTest.HUrlCGetIMage(ConUrl.regexDemo[2], 3, handler);
        }
    };
     t.start();

    }


    public void HttpURLConnectionTest(View view){
           Thread t1 = new Thread() {
               public void run() {
                   HttpURLConnectionTest.HUrlCGetTest(getUrl[0], 0, handler);
               }
           };

           Thread t2 = new Thread() {
               public void run() {
                HttpURLConnectionTest.HUrlCPostTest(postUrl[1],post[1],1,handler);
               }
           };
           t1.start();
           t2.start();

    }


    public void OkHttpTest(View view){

        //get请求
        //对于同步请求在请求时需要开启子线程，请求成功后需要跳转到UI线程修改UI。 响应实现response = client.newCall(request).execute();
       //OkHttpTest.okHttpGetTest(getUrl[0],2,handler);
        //这种方式不用再次开启子线程，但回调方法call.enqueue(new Callback(){}是执行在子线程中，所以在更新UI时还要跳转到UI线程中。
        OkHttpTest.okHttpGetTest1(getUrl[0],handler,textView3);//

        //post请求
        //1,创建OKhttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //2,创建Request
        RequestBody formBody = new FormBody.Builder()
                .add("mod", "LGE%28AOSP%20on%20HammerHead%29")
                .add("lon", "0")
                .build();

        Request request = new Request.Builder()
                .url(postUrl[1])
                .post(formBody)
                .build();
        //3，创建call对象并将请求对象添加到调度中
        mOkHttpClient.newCall(request).enqueue(new Callback() {
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
                            textView4.setText("OkHttpPost:"+message);
                        }
                    });
                }
            }
            });

    }





    /**
     * @param view
     */
    public void onClickTest(View view){
        Intent intent = new Intent(this,SecondActivity.class);
        //这种方法拿不到Int，float的值
//        intent.putExtra("name","诸葛");
//        intent.putExtra("IQ","200");
//        intent.putExtra("财富","110.1");

        //Bundle绑定
        Bundle bundle = new Bundle();
        bundle.putString("name","诸葛亮");
        bundle.putInt("IQ",200);
        bundle.putFloat("财富",101.1f);
        intent.putExtras(bundle);

        //开启页面跳转
        startActivity(intent);
    }
}
