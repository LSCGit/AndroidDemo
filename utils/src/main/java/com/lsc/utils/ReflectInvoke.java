package com.lsc.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lsc on 2020-02-21 18:30.
 * E-Mail:2965219926@qq.com
 *
 * 对反射进行封装
 */
public class ReflectInvoke {

    /**
     * 反射得到实例
     *
     *Example
     * className : "com.lsc.androidutils/reflect/ReflectInvoke"
     * pareTypes : Class[] classes = {int.class,String.class};
     * pareValues : Object[] objects = {110 , "reflect"};
     *
     * @param className  类的全路径名
     * @param pareTypes  函数的参数类型 数组
     * @param pareValues 构造函数的值 数组
     * @return 返回反射的实例
     */
    public static Object createObject(String className, Class[] pareTypes,
                                      Object[] pareValues){


        try{
            //得到类类型
            Class r = Class.forName(className);
            //得到构造函数
            Constructor constructor = r.getDeclaredConstructor(pareTypes);
            //禁止java访问检查 private
            constructor.setAccessible(true);
            return constructor.newInstance(pareValues);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 无参构造函数 反射得到 实例
     * @param className
     * @return
     */
    public static Object createObject(String className){

        return createObject(className, new Class[]{},new Object[]{});
    }

    /**
     * 一个参数构造函数 反射得到 实例
     * @param className
     * @param pareType
     * @param pareValue
     * @return
     */
    public static Object createObject(String className,Class pareType,
                                      Object pareValue){
        return createObject(className,new Class[]{pareType},new Object[]{pareValue});
    }


    /**
     * 通过实例对象 反射 调用方法
     *
     * 若没实例对象 配合 createObject（）使用
     *
     * @param object  实例对象
     * @param methodName 方法名
     * @param pareTypes 参数类型
     * @param pareValues 参数值
     * @return
     */
    public static Object invokeInstanceMethod(Object object, String methodName,
                                              Class[] pareTypes, Object[] pareValues){

        if (object == null){
            return null;
        }

        try {
            //调用一个private方法
            Method method  = object.getClass().getDeclaredMethod(methodName,pareTypes);
            method.setAccessible(true);
            return method.invoke(object,pareValues);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 通过类名调用静态方法
     *
     * @param className
     * @param methodName
     * @param pareTypes
     * @param pareValues
     * @return
     */
    public static Object invokeStaticMethod(String className, String methodName,
                                            Class[] pareTypes, Object[] pareValues){

        try{
            Class _class = Class.forName(className);
            Method method = _class.getDeclaredMethod(methodName,pareTypes);
            method.setAccessible(true);
            return method.invoke(null,pareValues);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Object getFieldObject(Object object,String fieldName){

        return getFieldObject(object.getClass().toString(),fieldName);
    }

    public static Object getFieldObject(Class _class,Object object,
                                        String fieldName){

        try {
            Field field = _class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        }  catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 反射得到字段的值
     *
     * @param className
     * @param object  实例对象
     * @param fieldName
     * @return
     */
    public static Object getFieldObject(String className, Object object,
                                        String fieldName){

        try {
            Class _class = Class.forName(className);
            Field field = _class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setFieldObject(Object object,String fieldName,
                                      Object fieldValue){
        setFieldObject(object.getClass().toString(), object, fieldName, fieldValue);
    }

    public static void setFieldObject(Class _class,Object object,
                                      String fieldName, Object fieldValue){

        try{
            Field field = _class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object,fieldValue);
        }  catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     * 通过反射设置字段值
     *
     * @param className
     * @param object
     * @param fieldName
     * @param fieldValue
     */
    public static void setFieldObject(String className,Object object,
                                      String fieldName, Object fieldValue){

        try{
            Class _class = Class.forName(className);
            Field field = _class.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object,fieldValue);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过反射 得到 静态字段
     *
     * @param className
     * @param fieldName
     * @return
     */
    public static Object getStaticFieldObject(String className, String fieldName){

        return getFieldObject(className,null,fieldName);
    }

    /**
     *
     * 通过反射 设置 静态字段
     * @param className
     * @param fieldName
     * @param fieldValue
     */
    public static void setStaticFieldObject(String className, String fieldName,
                                            Object fieldValue){

        setFieldObject(className,null,fieldName,fieldValue);
    }

}
