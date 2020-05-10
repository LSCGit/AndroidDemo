package com.lsc.designpattern.singleResponsibility;

/**
 * Created by lsc on 2020-04-18 00:25.
 * E-Mail:2965219926@qq.com
 *
 * IPhone这个接口可不是只有一个职责，它包含了两个职责：一个是协议管理，一个是数据传送。
 * dial()和hangup()两个方法实现的是协议管理，分别负责拨号接通和挂机；
 * chat()实现的是数据的传送，把我们说的话转换成模拟信号或数字信号传递到对方，
 * 然后再把对方传递过来的信号还原成我们听得懂的语言。
 */
public interface IPhone {

    //拨打电话  协议
    public void dial(String phoneNumber);
    //通话  数据传输
    public void chat(Object object);
    //挂断  协议
    public void hangUp();
}

