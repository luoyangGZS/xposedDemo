package com.example.host.mytestapp.base;



import com.example.host.mytestapp.uitl.Lg;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public abstract  class BaseHook extends XC_MethodHook {



    protected static ClassLoader mClassLoader;

    public BaseHook( ClassLoader ld){

        mClassLoader = ld;
    }


    public void addHook(String className, String method, Object... parameterTypesAndCallback) {

        Object[] objArr2 = new Object[(parameterTypesAndCallback.length + 1)];
        for (int i = 0; i < parameterTypesAndCallback.length; i++) {
            objArr2[i] = parameterTypesAndCallback[i];
        }
        objArr2[parameterTypesAndCallback.length] = this;
        XposedHelpers.findAndHookMethod(className, mClassLoader, method, objArr2);
    }


    public void addHookConstructor(String className, Object... parameterTypesAndCallback)
    {
        Object[] objArr2 = new Object[(parameterTypesAndCallback.length + 1)];
        for (int i = 0; i < parameterTypesAndCallback.length; i++)
        {
            objArr2[i] = parameterTypesAndCallback[i];
        }
        objArr2[parameterTypesAndCallback.length] = this;
        XposedHelpers.findAndHookConstructor(className, mClassLoader, objArr2);
    }

    /**
     * @param className 类名
     * @param methodStr 方法名
     */
    public void addHookWithOnlyMethodName(String className, String methodStr)
    {
        try
        {
            /** findClass 使用指定的类加载器查找类
             类名称	上面提到的格式之一的类名。
             类加载器	类加载器，或null引导类加载器。
             返回，对类名的引用。 */
            Class findClass = XposedHelpers.findClass(className, mClassLoader);
            if (findClass != null)
            {
                /**  getDeclaredMethods()获取所有的（包括私有的）本类的方法
                 * method.setAccessible(true); // 反射函数是私有的时，抑制Java的访问控制检查,
                 * 应用java反射，得到公有方法 getMethods()获取所有的（包括继承的，如Object类中的）方法 */
                Method[] declaredMethods = findClass.getDeclaredMethods();
                ArrayList arrayList = new ArrayList();
                /**得到多个方法进行遍历    */
                for (Method method : declaredMethods)
                {
                    /**文件名是methodStr与方法不抽象  Modifiers：修饰符   */
                    if (method.getName().equals(methodStr) && !Modifier.isAbstract(method.getModifiers()))
                    {
                        //装载方法
                        arrayList.add(method);
                    }
                }

                //集合适配
                Iterator it = arrayList.iterator();
                while (it.hasNext())
                {
                    /**使用指定的回调挂钩任何方法  callback: 调用hooked方法时要执行的回调。
                     * 返回：可用于移除钩子的对象。
                     */
                    XposedBridge.hookMethod((Method) it.next(), this);
                }
            }
        }
        catch (Exception e)
        {
            Lg.e(e);
        }
    }


    protected abstract void afterHookedMethod(MethodHookParam methodHookParam);

    protected abstract void beforeHookedMethod(MethodHookParam methodHookParam);

}













//    public  String className;
//    public ClassLoader mClassLoader;
//    public  String methodName;
//    public  String idn;
//    public  String defPackage;
//
//    public BaseHook(String className,ClassLoader ld,String methodName,String id,String defPackage){
//        this.className = className;
//        this.mClassLoader = ld;
//        this.methodName = methodName;
//        this.idn = id;
//        this.defPackage = defPackage;
//    }
//
//    public void addHook(String methodName,Object... ParameterTypesAndCallback){
//        Object[] objArray = new Object[ParameterTypesAndCallback.length +1];
//        for(int i=0;i<ParameterTypesAndCallback.length;i++){
//            objArray[i] = ParameterTypesAndCallback[i];
//        }
//        objArray[ParameterTypesAndCallback.length] = this;
//        XposedHelpers.findAndHookMethod(className,mClassLoader,methodName,objArray);
//
//    }



