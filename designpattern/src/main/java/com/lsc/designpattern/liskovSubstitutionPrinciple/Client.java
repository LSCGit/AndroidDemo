package com.lsc.designpattern.liskovSubstitutionPrinciple;

/**
 * Created by lsc on 2020-04-18 01:08.
 * E-Mail:2965219926@qq.com
 *
 * 场景类
 */
public class Client {

    public static void main(String[] args){
        //产生三毛这个士兵
        Soldier sanMao = new Soldier();
        //给三毛一支枪
        sanMao.setGun(new Rifle());
        sanMao.killEnemy();
    }
}
