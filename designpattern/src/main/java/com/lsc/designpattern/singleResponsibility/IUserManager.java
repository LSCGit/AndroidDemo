package com.lsc.designpattern.singleResponsibility;

/**
 * Created by lsc on 2020-04-18 00:42.
 * E-Mail:2965219926@qq.com
 *
 * 在我的项目组中，如果有人写了这样一个方法，我不管他写了多少程序，花了多少工夫，一律重写！
 * 原因很简单：方法职责不清晰，不单一，不要让别人猜测这个方法可能是用来处理什么逻辑的。
 */
public interface IUserManager {

    void changeUser(String...changeOptions);

    //优化后
    void changeUsername(String userName);
    void changeHomeAddress(String homeAddress);
}
