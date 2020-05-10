package com.lsc.designpattern.pattern.template;

/**
 * Created by lsc on 2020-04-18 14:57.
 * E-Mail:2965219926@qq.com
 */
public abstract class AbstractClass {

    //基本方法
    abstract void doSomething();
    //基本方法
    abstract void doAnything();

    //模版方法
    void templateMethod(){
        //调用基本方法，完成相关逻辑
    }
}
