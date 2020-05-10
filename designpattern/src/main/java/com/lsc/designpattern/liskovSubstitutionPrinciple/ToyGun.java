package com.lsc.designpattern.liskovSubstitutionPrinciple;

/**
 * Created by lsc on 2020-04-18 01:11.
 * E-Mail:2965219926@qq.com
 */
public class ToyGun extends AbstractGun {

    //玩具枪不能射击，但编译器要求实现。虚构一个
    @Override
    public void shoot() {
        //不能射击，方法不实现。
    }
}
