package com.example.host.mytestapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.host.mytestapp.hookblock.HookActivity;
import com.example.host.mytestapp.hookblock.HookFresco;
import com.example.host.mytestapp.hookblock.HookGlide;
import com.example.host.mytestapp.hookblock.HookImageLoader;
import com.example.host.mytestapp.hookblock.HookOkHttp;
import com.example.host.mytestapp.hookblock.HookOkHttp2;
import com.example.host.mytestapp.hookblock.HookPicasso;
import com.example.host.mytestapp.hookblock.HookUrl;
import com.example.host.mytestapp.hookblock.Hook_HttpURLConnection;
import com.example.host.mytestapp.uitl.ClassNameUitl;
import com.example.host.mytestapp.uitl.CommentInfoUitl;
import com.example.host.mytestapp.uitl.Lg;
import com.example.host.mytestapp.uitl.SimulateTouch;

import java.lang.reflect.Field;
import java.util.Random;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedBridge.log;

public class Main implements IXposedHookLoadPackage {

   // public static Activity activity;


    /**
     * @param loadPackageParam 这个参数包含了加载的应用程序的一些基本信息
     */
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
//        if ((!loadPackageParam.packageName.equals("com.baidu.nani"))&&(!loadPackageParam.packageName.equals("com.example.host.mytestapp"))) {
//            return;
//        }
//        if (!loadPackageParam.packageName.equals("com.example.host.mytestapp")) {
//            return;
//        }
        try {      if (loadPackageParam == null || loadPackageParam.appInfo == null)
                                {
                                    return;
                                }

                    try{
                        new HookUrl(loadPackageParam.classLoader);
                    }catch(Throwable e){
                        Lg.e("HookOkHttp-----------"+e);
                    }

                    try{
                        new HookOkHttp(loadPackageParam.classLoader);
                    }catch(Throwable e){
                        Lg.e("HookOkHttp-----------"+e);
                    }


                    try{
                        new HookGlide(loadPackageParam.classLoader);
                    }catch(Throwable e){
                        Lg.e("HookGlide-----------"+e);
                    }

                    try{
                        new HookPicasso(loadPackageParam.classLoader);
                    }catch(Throwable e){
                        Lg.e("HookPicasso-----------"+e);
                    }

                      try{
                          new HookFresco(loadPackageParam.classLoader);
                    }catch(Throwable e){
                        Lg.e("HookFresco-----------"+e);
                    }

                    try{
                        new HookImageLoader(loadPackageParam.classLoader);
                    }catch(Throwable e){
                        Lg.e("HookImageLoader-----------"+e);
                    }



                }
                catch(Throwable e){
                    Lg.e("Main-----------"+e);
        }
    }
}

//
//        //主界面fragment跳转
//        XposedHelpers.findAndHookMethod(ClassNameUitl.HOOH_CLASS_INTENT, loadPackageParam.classLoader, "getIntExtra", String.class, int.class, new XC_MethodHook() {
//            /**
//             * @param param  存着方法的参数agrs,方法名Method,要调用方法的所在类 Object:
//             * @throws Throwable
//             */
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
//                if (param.args[0].equals("tab")) {
//                    param.args[1] = 1;
//                }
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully." );
//            }
//        });
//
//        //点开搜索页面
//        XposedHelpers.findAndHookMethod(ClassNameUitl.HOOH_CLASS_NANI_DISCOVER_FRAGMENT, loadPackageParam.classLoader, "ae", new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
//                //thisObject该对象
//                Class clazz = param.thisObject.getClass();
//                Field.setAccessible(clazz.getDeclaredFields(), true);
//                //通过类用getDeclaredField方法的名为Z成员变量
//
//                Field field = clazz.getDeclaredField("Z");
//
//                field.setAccessible(true);
//                final ViewGroup viewGroupZ = (ViewGroup) field.get(param.thisObject);
//                viewGroupZ.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        viewGroupZ.performClick();
//                    }
//                }, 1000L);
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully." );
//            }
//        });
//
//        //设置搜索的值，并且模拟点击搜索
//        XposedHelpers.findAndHookMethod(ClassNameUitl.HOOH_CLASS_NANI_SEARCHVIEW, loadPackageParam.classLoader, "a", new XC_MethodHook() {
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
//                Class clazz = param.thisObject.getClass();
//                Field fielde = clazz.getDeclaredField("e");
//                fielde.setAccessible(true);
//                EditText editTexte = (EditText) fielde.get(param.thisObject);
//
//                //随机拿评论信息[0,length)的一个整数值
//                int i = new Random().nextInt(CommentInfoUitl.UserName.length);
//                editTexte.setText(CommentInfoUitl.UserName[i]);
//
//                //Field.setAccessible(clazz.getDeclaredFields(), true);
//                Field fieldg = clazz.getDeclaredField("g");
//                fieldg.setAccessible(true);
//                final TextView textViewg = (TextView) fieldg.get(param.thisObject);
//                textViewg.postDelayed(new Runnable() {
//                                          @Override
//                                          public void run() {
//                                              textViewg.setEnabled(true);
//                                              textViewg.performClick();
//                                          }
//                                      }
//                        , 1000L);
//
//                // 延时5秒找到控件user_avatar的id，并完成内部任务
//                textViewg.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        int user_avatar_Id = activity.getResources().getIdentifier("user_avatar", "id", "com.baidu.nani");
//                        final ImageView img = activity.findViewById(user_avatar_Id);
//                        Lg.i( "-----------------viewId = " + user_avatar_Id + ",img:" + img);
//
//                        //延时1秒找到控件坐标,并完成模拟点击
//                        img.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                SimulateTouch.activityViewTouchEvent(img,activity,10,10);
//
//                            }
//                        }, 1000L);//数值默认为int,加L表示 长整型 Long 八个字节
//                    }
//                }, 5000L);
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully." );
//
//            }
//        });
//
//
//        //拿到搜索页面SearchActivity
//        XposedHelpers.findAndHookMethod(ClassNameUitl.HOOK_CLASS_NANI_SEARCH_ACTIVITY, loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                try {
//                    Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
//                    activity = (Activity) param.thisObject;
//
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully." );
//            }
//        });
//
//
//
//                    //私人页面作品跳转
//                   new HookActivity(loadPackageParam.classLoader);



















        //私人页面作品跳转
//        XposedHelpers.findAndHookMethod(ClassNameUitl.HOOK_CLASS_NANI_PERSON_ACTIVITY, loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//                try {
//                    Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
//                    final Activity activity2 = (Activity) param.thisObject;
//                    int  square_tab_id = activity2.getResources().getIdentifier("txt_person_square_tab","id","com.baidu.nani");
//                    final TextView square_textView = activity2.findViewById(square_tab_id);
//                    Lg.i( "--------------PersonActivity.onCreate()-square_tab_id = " + square_tab_id );
//                    if(square_textView.toString() !=null) {
//                        Lg.i("-------------square_tab_id--textView.toString():" + square_textView.toString());
//                    }
//                    //延时3秒找到控件坐标完成模拟点击
//                    square_textView.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            SimulateTouch.activityViewTouchEvent(square_textView,activity2,10,210);
//                        }
//                    }, 3000L);//数值默认为int,加L表示 长整型 Long 八个字节
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully." );
//            }
//        });


//        //视频播放也点赞，并且点开评论
//        XposedHelpers.findAndHookMethod(ClassNameUitl.HOOK_CLASS_NANI_VIDEOPLAY_ACTIVITY, loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
//                final Activity activity3 = (Activity) param.thisObject;
//
//                //点赞心形图标Id
//                final int cancel_agree_img_id = activity3.getResources().getIdentifier("cancel_agree_img", "id", "com.baidu.nani");
//                //获取评论符号Id
//                final int comment_img_id = activity3.getResources().getIdentifier("comment_img", "id", "com.baidu.nani");
//                //延时五秒，等待视图加载
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        final ImageView cancel_agree_img = activity3.findViewById(cancel_agree_img_id);
//                        Lg.i("-------------cancel_agree_img:"+cancel_agree_img );
//                        SimulateTouch.activityViewTouchEvent(cancel_agree_img,activity3,10,10);
//
//
//                        final ImageView comment_img = activity3.findViewById(comment_img_id);
//                        Lg.i("-------------comment_img:"+comment_img );
//                    /*  延时2秒,评论控件完成模拟点击  */
//                cancel_agree_img.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                           SimulateTouch.activityViewTouchEvent(comment_img,activity3,10,10);
//                        }
//                    }, 2000L);//数值默认为int,加L表示 长整型 Long 八个字节
//
//                    }
//
//                },5000L);
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully." );
//        }
//
//        });
//
//
//
//        //评论也设置评论信息，并点击完成评论
//       XposedHelpers.findAndHookMethod(ClassNameUitl.HOOK_CLASS_NANI_VIDEOCOMMENT_ACTIVITY, loadPackageParam.classLoader, "onCreate", Bundle.class, new XC_MethodHook() {
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------" );
//                final Activity activity4 = (Activity) param.thisObject;
//
//                //拿到评论区的文本编辑框Id
//                final int comment_edit_view_id = activity4.getResources().getIdentifier("comment_edit_view", "id", "com.baidu.nani");
//                Lg.i("----------------comment_edit_view_id = " + comment_edit_view_id);
//                EditText comment_edit_view = activity4.findViewById(comment_edit_view_id);
//
//                //随机拿评论信息[0,length)的一个整数值
//                int i = new Random().nextInt(CommentInfoUitl.commentInfo.length);
//                comment_edit_view.setText(CommentInfoUitl.commentInfo[i]);
//                Lg.i("----------------cancel_agree_img:"+comment_edit_view );
//
//                //拿到评论发送图标的Id
//                int common_publish_img_id = activity4.getResources().getIdentifier("common_publish_img","id","com.baidu.nani");
//                final ImageView common_publish_img = activity4.findViewById(common_publish_img_id);
//
//                //拿到评论返回图标的Id
//                int comment_back_img_id = activity4.getResources().getIdentifier("comment_back_img","id","com.baidu.nani");
//                final ImageView comment_back_img = activity4.findViewById(comment_back_img_id);
//
//                    /* 延时2秒找到评论发送控件坐标，并完成模拟点击  */
//                comment_edit_view.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            SimulateTouch.activityViewTouchEvent(common_publish_img,activity4,10,10);
//                        }
//                    }, 2000L);//数值默认为int,加L表示 长整型 Long 八个字节
//
//                /*延时2秒找到评论返回，并完成模拟点击  */
//                common_publish_img.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            SimulateTouch.activityViewTouchEvent(comment_back_img,activity4,10,10);
//                        }
//                    }, 3000L);
//                Lg.i("hook----------"+param.thisObject.getClass()+"."+param.method.getName()+"()------successfully.成功!" );
//
//        }
//
//        });



