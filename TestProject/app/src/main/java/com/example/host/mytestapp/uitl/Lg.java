package com.example.host.mytestapp.uitl;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.cert.LDAPCertStoreParameters;

/**
 * Class：Lg
 * @author: Host-0304 LL
 * @Date: 2018/7/20   14:18
 * @Description: 自定义打印类
 *
 */

public class Lg {
    private static String  LOG_TAG = "NN";
    private static Boolean   Log_Debug = true;


    public static void i(String log ){
        if(Log_Debug){

            Log.i(LOG_TAG,"I:-----"+log);
        }

    }

    public static void e (String log){
        if(Log_Debug){
            Log.e(LOG_TAG,"E:-----"+log);
        }
    }


    public static void e (Throwable e){
        if(Log_Debug){
            Lg.e(getInfo(e));
        }

    }

    private static String  getInfo(Throwable e) {
        String info = null;
        if(e != null) {
            Writer stringWriter = null;
            PrintWriter pw = null;
            try {
                stringWriter = new StringWriter();
                pw = new PrintWriter(stringWriter);
                e.printStackTrace(pw);
                info = stringWriter.toString();
            } finally {
                try {
                    if (pw != null) {
                        pw.close();
                    }

                    if (stringWriter != null) {
                        stringWriter.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
              return info;

        }



}
