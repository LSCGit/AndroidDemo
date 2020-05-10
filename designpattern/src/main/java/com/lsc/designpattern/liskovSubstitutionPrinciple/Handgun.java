package com.lsc.designpattern.liskovSubstitutionPrinciple;

/**
 * Created by lsc on 2020-04-18 01:02.
 * E-Mail:2965219926@qq.com
 *
 * 手枪实现类
 */
public class Handgun extends AbstractGun {

    @Override
    public void shoot() {
        System.out.println("手枪射击。。。");
    }
}
