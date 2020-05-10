package com.lsc.designpattern.liskovSubstitutionPrinciple;

/**
 * Created by lsc on 2020-04-18 01:02.
 * E-Mail:2965219926@qq.com
 *
 * 机枪实现类
 */
public class MachineGun extends AbstractGun {

    @Override
    public void shoot() {
        System.out.println("机枪扫射。。。");
    }
}
