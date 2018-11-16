package com.example.host_0303.xposednani;


import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.host_0303.xposednani.util.CommitsUtil;
import com.example.host_0303.xposednani.util.LogUtil;
import com.example.host_0303.xposednani.util.PackageUtil;
import com.example.host_0303.xposednani.util.RandomUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_RECYCLER_VIEW$H;
import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_SEARCH_RESULT_DATA;
import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_DISCOVER_FRAGMENT;
import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_PERSON_TABLE_FRAGMENT;
import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_RECYCLEVIEW_G;
import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_SEARCH_ACTIVITY;
import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_VIDEO_COMMENT_ACTIVITY;
import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_VIDEO_PLAY_FRAGMENT;
import static com.example.host_0303.xposednani.util.PackageUtil.CLASSPATH_VIDEO_PLAY_FRAGMENT_VIEW_BINDING;

public class BaseHook  extends AbstractBaseHook implements IXposedHookLoadPackage {
    private Object discoverFragment;
    private Object layoutManager;
    private final static int DELAY_TIME = 1000;
    // FragmentManager 具备五个页面：主页、挑战、拍视频、消息、我的
    private final static int[] FRAGMENT_PAGE = {0,1,2,3,4};
    // ViewPager 页面选择，0用户的作品，1喜欢的作品
    private final static int[] VIEWPAGER_WORKS_AND_LIKE = {0,1};
    //作品列表，从1开始计算
    private final static int INDEX_WORKS = 1;
    //用户名或者用户ID
    private final static String USER_ID = "Beatbox_Hozer";
    private final static int USER_LIST_POSITION = 0;
    private final static int USER_LIST_USERTYPE = 1;
    private final static boolean USER_LIST_ISRECOMMEND = false;
    public BaseHook(){
        packageName = "com.baidu.nani";
    }
    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals(packageName)){
            loadPackageParamClassLoader = lpparam.classLoader;
            goToDiscoverFragment();
            goToSearchFragment();
            searchUser();
            goToUserFragment();
            chooseVideo();
            likeVideoHook();
            commentsHook();
        }
    }
    //跳转到挑战界面
    private void goToDiscoverFragment(){
        XposedHelpers.findAndHookMethod(PackageUtil.CLASSPATH_MAINACTIVITY, loadPackageParamClassLoader, "a", int.class, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param){
                param.args[0] = FRAGMENT_PAGE[1];
            }
        });
    }
    // 跳转到搜索界面
    private void goToSearchFragment(){
        XposedHelpers.findAndHookMethod(PackageUtil.CLASSPATH_MAINACTIVITY, loadPackageParamClassLoader, "onResume", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param){
                try {

                    /*通过路径拿到com.baidu.nani.discover.DiscoverFragment类
                    *
                    *ClassLoader.loadClass(className)方法,不进行包括初始化等一些列步骤，那么静态块和静态对象就不会得到执行
                    *Class.forName(className)默认是需要初始化。一旦初始化，就会触发目标对象的 static块代码执行，
                    * static参数也也会被再次初始化。
                    * */

                    Class discoverClass = loadPackageParamClassLoader.loadClass(CLASSPATH_DISCOVER_FRAGMENT);

                    /*.
                     */
                    /*通过相应类用XposedHelpers.findField（className,fieldName）方法找到名为mDiscoverSearchLayout的成员变量

                    另一种方法//通过类用getDeclaredField方法的名为Z成员变量
                    Field field = clazz.getDeclaredField(fieldName:"Z");
                    ViewGroup viewGroupZ = (ViewGroup) field.get(param.thisObject);//得到相应属性
                    FieldByName和FindField都是用来查找字段，不同在于FindField找不带符合条件的字段时返回的是null，
                    而FieldByName找不到则直接抛出异常。
                    * */
                    Field searchField = (XposedHelpers.findField(discoverClass, "mDiscoverSearchLayout"));
                    if (discoverFragment == null) {
                        LogUtil.log("discoverFragment对象是空");
                    } else {
                        //通过对象参数找到布局控件
                        LinearLayout searchLayout = (LinearLayout) searchField.get(discoverFragment);//通过相应类，得到该类相应属性
                        clickView(searchLayout);
                    }
                } catch (ClassNotFoundException e) {
                    LogUtil.log(e.toString());
                } catch (IllegalAccessException e) {
                    LogUtil.log(e.toString());
                }
            }
        });

        // 获得搜索界面
        /**  这个获取很巧妙  */
        XposedHelpers.findAndHookMethod(CLASSPATH_DISCOVER_FRAGMENT, loadPackageParamClassLoader, "ah", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param){
                discoverFragment = param.getResult();
            }
        });
    }
    // 搜索用户
    private void searchUser(){
        XposedHelpers.findAndHookMethod(CLASSPATH_SEARCH_ACTIVITY, loadPackageParamClassLoader, "onCreate",Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                try {
                    //拿到searchActivity类名，要拿里头的名为mSearchView的成员变量
                    Field field = XposedHelpers.findField(param.thisObject.getClass(),"mSearchView");
                    //拿到该成员变量的属性
                    FrameLayout frameLayout = (FrameLayout)field.get(param.thisObject);
                    if (frameLayout == null){
                        LogUtil.log("naniSearchView对象是空");
                    }else{
                        //搜索的框架打开，拿到Activtiy开启类的a方法，java反射，调用a方法
                        Method method =  param.thisObject.getClass().getMethod("a",String.class);
                        // 被搜索用户的ID或者用户名
                        method.invoke(param.thisObject,USER_ID);
                    }
                } catch (NoSuchMethodException e) {
                    LogUtil.log(e.toString());
                } catch (IllegalAccessException e) {
                    LogUtil.log(e.toString());
                } catch (InvocationTargetException e) {
                    LogUtil.log(e.toString());
                }
            }
        });
    }
    // 当用户搜索结果得到后，进入用户界面
    private void goToUserFragment(){

        Class dataClass = null;
        try {
            dataClass = loadPackageParamClassLoader.loadClass(CLASSPATH_SEARCH_RESULT_DATA);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        XposedHelpers.findAndHookMethod(CLASSPATH_SEARCH_ACTIVITY, loadPackageParamClassLoader, "a", dataClass, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                try {
                    Method clickMethod = param.thisObject.getClass().getMethod("a", int.class, int.class, boolean.class);
                    // 当搜索结果得到后，选择第几个用户
                    clickMethod.invoke(param.thisObject, USER_LIST_POSITION, USER_LIST_USERTYPE, USER_LIST_ISRECOMMEND);
                } catch (NoSuchMethodException e) {
                    LogUtil.log(e.toString());
                } catch (IllegalAccessException e) {
                    LogUtil.log(e.toString());
                } catch (InvocationTargetException e) {
                    LogUtil.log(e.toString());
                }
            }
        });
    }
    private void chooseVideo(){
        // 获得layoutManager
        Class classViewH = null ;
        try {
            classViewH =loadPackageParamClassLoader.loadClass(CLASSPATH_RECYCLER_VIEW$H);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        XposedHelpers.findAndHookMethod(CLASSPATH_RECYCLEVIEW_G, loadPackageParamClassLoader, "setLayoutManager", classViewH, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param){
                LogUtil.log(param.args[0].getClass().toString());
                if (layoutManager == null)
                    layoutManager = param.args[0];
            }
        });
        // 进入用户界面后，选择用户视频
        XposedHelpers.findAndHookMethod(CLASSPATH_PERSON_TABLE_FRAGMENT, loadPackageParamClassLoader, "ae", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param)  {
                try{
                    Field recyclerViewField = (param.thisObject.getClass()).getDeclaredField("mRecyclerView");
                    recyclerViewField.setAccessible(true);
                    // 进入视频播放界面的的函数，参数是view，表格（作品0，喜欢1），位置从1起始。
                    Method gotPlay = (param.thisObject.getClass()).getDeclaredMethod("a", View.class, int.class, int.class);
                    // 获得该用户视频的view
                    Method i = (loadPackageParamClassLoader.loadClass(CLASSPATH_RECYCLER_VIEW$H)).getDeclaredMethod("i", int.class);
                    Thread.currentThread().sleep(DELAY_TIME);
                    gotPlay.invoke(param.thisObject, i.invoke(layoutManager, 0), VIEWPAGER_WORKS_AND_LIKE[0], INDEX_WORKS);
                } catch (InvocationTargetException e) {
                    LogUtil.log(e.toString());
                } catch (NoSuchMethodException e) {
                    LogUtil.log(e.toString());
                } catch (InterruptedException e) {
                    LogUtil.log(e.toString());
                } catch (IllegalAccessException e) {
                    LogUtil.log(e.toString());
                } catch (NoSuchFieldException e) {
                    LogUtil.log(e.toString());
                } catch (ClassNotFoundException e) {
                    LogUtil.log(e.toString());
                }
            }
        });
    }
    // 实现自动点赞
    private void likeVideoHook(){
        Class classVideoPlayFragment = null;
        try{
            classVideoPlayFragment = loadPackageParamClassLoader.loadClass(CLASSPATH_VIDEO_PLAY_FRAGMENT);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        XposedHelpers.findAndHookConstructor(CLASSPATH_VIDEO_PLAY_FRAGMENT_VIEW_BINDING, loadPackageParamClassLoader, classVideoPlayFragment, View.class, new XC_MethodHook() {
            private Object target;
            @Override
            protected void beforeHookedMethod(MethodHookParam param) {
                target = param.args[0];
            }
            @Override
            protected void afterHookedMethod(MethodHookParam param) {
                try {
                   FrameLayout mCancelLikeView = (FrameLayout)getObjectByField(target,"mAgreeLayout");
                    clickView(mCancelLikeView);
                    LogUtil.log(" mCanclerLikeView click");
                    Method method = target.getClass().getDeclaredMethod("onCommentClick");
                    method.invoke(target);
                } catch (NoSuchMethodException e) {
                    LogUtil.log(e.toString());
                } catch (IllegalAccessException e) {
                    LogUtil.log(e.toString());
                } catch (InvocationTargetException e) {
                    LogUtil.log(e.toString());
                }
            }
        });
    }
    // 实现自动评论
    private void commentsHook(){
        XposedHelpers.findAndHookMethod(CLASSPATH_VIDEO_COMMENT_ACTIVITY, loadPackageParamClassLoader, "onCreate", Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param){
                try {
                    //先拿Activity类 中调用的类，通过该类的实例mCommentView拿到类对象CommentView类
                    Object mCommentView = getObjectByField(param.thisObject,"mCommentView");
                    //在从CommentView中拿其中调用的EditText类，拿到相应文本对象
                    EditText mEditView = (EditText)getObjectByField(mCommentView,"mEditView");
                    int commitIndex = RandomUtil.randomInt(CommitsUtil.COMMITS.length);
                    mEditView.setText(CommitsUtil.COMMITS[commitIndex]);
                    Thread.currentThread().sleep(DELAY_TIME);
                    Method setOnPubulishClick = (mCommentView.getClass()).getDeclaredMethod("setOnPubulishClick", View.class);
                    if (!setOnPubulishClick.isAccessible())
                        setOnPubulishClick.setAccessible(true);
                    setOnPubulishClick.invoke(mCommentView, mEditView);
                } catch (InvocationTargetException e) {
                    LogUtil.log(e.toString());
                } catch (NoSuchMethodException e) {
                    LogUtil.log(e.toString());
                } catch (InterruptedException e) {
                    LogUtil.log(e.toString());
                } catch (IllegalAccessException e) {
                    LogUtil.log(e.toString());
                }
            }
        });
    }

}
