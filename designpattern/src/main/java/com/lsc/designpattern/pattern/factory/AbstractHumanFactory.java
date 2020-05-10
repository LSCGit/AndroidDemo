package com.lsc.designpattern.pattern.factory;

/**
 * Created by lsc on 2020-04-18 12:34.
 * E-Mail:2965219926@qq.com
 *
 * 抽象人类创建工厂
 *
 * 通过定义泛型对createHuman的输入参数产生两层限制：
 *  必须是Class类型；
 *  必须是Human的实现类。
 */
public abstract class AbstractHumanFactory {

    public abstract <T extends Human> T createHuman(Class<T> c);
}
