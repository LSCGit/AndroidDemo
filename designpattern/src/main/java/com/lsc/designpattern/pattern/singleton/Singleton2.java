package com.lsc.designpattern.pattern.singleton;

/**
 * Created by lsc on 2020-04-18 11:10.
 * E-Mail:2965219926@qq.com
 *
 * 在高并发情况下，请注意单例模式的线程同步问题。单例模式有几种不同的实现方式，
 *
 * 懒汉式 不安全
 * 单例模式在低并发的情况下尚不会出现问题，若系统压力增大，
 * 并发量增加时则可能在内存中出现多个实例，破坏了最初的预期。
 * 为什么会出现这种情况呢？如一个线程A执行到singleton = newSingleton()，
 * 但还没有获得对象（对象初始化是需要时间的），第二个线程B也在执行，执行到（singleton == null）判断，
 * 那么线程B获得判断条件也是为真，于是继续运行下去，线程A获得了一个对象，线程B也获得了一个对象，在内存中就出现两个对象！
 */
public class Singleton2 {

    private static Singleton2 singleton2 = null;
    //限制产生多个对象
    private Singleton2(){}

    public static Singleton2 getSingleton2(){
        if (singleton2 == null){
            singleton2 = new Singleton2();
        }
        return singleton2;
    }

}
