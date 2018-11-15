package com.example.host.mytestapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.tV);


        //这种方法拿不到Int，float的值
//        Intent intent = getIntent();
//        String name = intent.getStringExtra("name");
//        int iq = intent.getIntExtra("IQ",0);
//        float va = intent.getFloatExtra("财富",0.0f);
//        textView.setText("姓名："+name + " , IQ： "+ iq + " ,财富："+ va+"万");



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("name");
        int iq = bundle.getInt("IQ");
        float va = bundle.getFloat("财富");
        textView.setText("姓名："+name + '\n'+" IQ： "+ iq + " , 财富："+ va+"万");
    }
}
