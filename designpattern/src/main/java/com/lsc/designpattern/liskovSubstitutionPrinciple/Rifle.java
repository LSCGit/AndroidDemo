package com.lsc.designpattern.liskovSubstitutionPrinciple;

/**
 * Created by lsc on 2020-04-18 01:02.
 * E-Mail:2965219926@qq.com
 *
 * 步枪 实现类
 */
public class Rifle extends AbstractGun {

    @Override
    public void shoot() {
        System.out.println("步枪射击。。。");
    }
}
