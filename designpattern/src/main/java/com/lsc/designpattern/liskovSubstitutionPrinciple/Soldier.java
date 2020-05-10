package com.lsc.designpattern.liskovSubstitutionPrinciple;

/**
 * Created by lsc on 2020-04-18 01:05.
 * E-Mail:2965219926@qq.com
 *
 * 士兵的实现类
 */
public class Soldier {

    //定义士兵的枪支
    private AbstractGun mGun;

    //给士兵一支枪
    public void setGun(AbstractGun gun){
        mGun = gun;
    }

    public void killEnemy(){
        System.out.println("士兵开始杀敌人");
        mGun.shoot();
    }
}
