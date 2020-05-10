package com.lsc.designpattern.pattern.abstractFactory;


/**
 * Created by lsc on 2020-04-18 12:37.
 * E-Mail:2965219926@qq.com
 *
 * 女娲造人
 */
public class NvWa {

    public static void main(String[] args) {
        //男性生产线
        HumanFactory maleFactory = new MaleFactory();
        //女性生产线
        HumanFactory femaleFactory = new FemaleFactory();
        //生产一个男性黄色人
        Human maleYellowHuman = maleFactory.createYellowHuman();
    }
}
