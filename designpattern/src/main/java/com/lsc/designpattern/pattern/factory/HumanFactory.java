package com.lsc.designpattern.pattern.factory;

/**
 * Created by lsc on 2020-04-18 12:36.
 * E-Mail:2965219926@qq.com
 */
public class HumanFactory extends AbstractHumanFactory {

    @Override
    public <T extends Human> T createHuman(Class<T> c) {

        Human human =null;
        try {
            human = (T)Class.forName(c.getName()).newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return (T)human;
    }
}
