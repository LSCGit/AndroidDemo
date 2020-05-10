package com.lsc.designpattern.singleResponsibility;

/**
 * Created by lsc on 2020-04-18 00:30.
 * E-Mail:2965219926@qq.com
 */
public interface IConnectionManager {
    //拨打电话
    public void dial(String phoneNumber);
    //挂断
    public void hangUp();
}
