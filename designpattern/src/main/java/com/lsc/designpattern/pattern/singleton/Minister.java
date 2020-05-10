package com.lsc.designpattern.pattern.singleton;

/**
 * Created by lsc on 2020-04-18 10:18.
 * E-Mail:2965219926@qq.com
 */
public class Minister {

    public static void main(String[] args) {
        for (int day=0;day<3;day++){
            Emperor emperor = Emperor.getInstance();

            emperor.say();
        }
    }
}
