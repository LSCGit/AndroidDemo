package com.lsc.designpattern.pattern.template;

/**
 * Created by lsc on 2020-04-18 14:49.
 * E-Mail:2965219926@qq.com
 *
 * 抽象悍马模型
 */
public abstract class HummerModel2 {

    abstract void start();
    public abstract void stop();
    abstract void alarm();
    abstract void engineBoom();
    public void run(){
        this.run();
        if (isAlarm()) {
            this.alarm();
        }
        this.engineBoom();
        this.stop();
    }
    //钩子方法
    abstract boolean isAlarm();
}
