package com.example.host_0303.xposednani;

import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import de.robv.android.xposed.IXposedHookLoadPackage;

public abstract class AbstractBaseHook implements IXposedHookLoadPackage{
    protected String packageName;
    protected ClassLoader loadPackageParamClassLoader;
    public void clickView(View view){
        view.performClick();
    }

    public final Object getObjectByField(Object target,Field field){
        Object result = null;
        try {
            result =  field.get(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public final Object getObjectByField(Object target,String filedName){
        Object result = null;
        try{
            Field field = getField(target.getClass(),filedName);
            result = field.get(target);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    public final Field getField(Class clazz,String fieldName){
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            if (!field.isAccessible())
                field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    public final Field getField(String clazzName,String fieldName){
        Field field = null;
        try{
            if (loadPackageParamClassLoader!=null){
                Class tmpClazz = loadPackageParamClassLoader.loadClass(clazzName);
                field = tmpClazz.getDeclaredField(fieldName);
                if (!field.isAccessible())
                    field.setAccessible(true);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return field;
    }

    public final Method getMethod(Class clazz,String methodName,Class... params){
        Method method = null;
        try{
            method = clazz.getDeclaredMethod(methodName,params);
            if (!method.isAccessible())
                method.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public final Method getMethod(String clazzName,String methodName,Class... params){
        Method method = null;
        try{
            Class clazz = loadPackageParamClassLoader.loadClass(clazzName);
            method = clazz.getDeclaredMethod(methodName, params);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }
    public final Method[] getMethods(Class clazz,String methodName){
        ArrayList<Method> resultMethods = new ArrayList<>();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method tmpMethod : methods) {
            if ( methodName == null || tmpMethod.getName().equals(methodName) ) {
                if (!tmpMethod.isAccessible())
                    tmpMethod.setAccessible(true);
                resultMethods.add(tmpMethod);
            }
        }
        return resultMethods.toArray(new Method[resultMethods.size()]);
    }
}
