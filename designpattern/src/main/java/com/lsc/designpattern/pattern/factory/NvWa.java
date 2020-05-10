package com.lsc.designpattern.pattern.factory;

/**
 * Created by lsc on 2020-04-18 12:37.
 * E-Mail:2965219926@qq.com
 *
 * 女娲造人
 */
public class NvWa {

    public static void main(String[] args) {
        //生命八卦炉
        AbstractHumanFactory YinYangLu = new HumanFactory();

        //女娲 第一次造人，火候不足，产生白人
        Human whiteHuman = YinYangLu.createHuman(WhiteHuman.class);

        //女娲第二次造人，火候过足，产生黑人
        Human blackHuman = YinYangLu.createHuman(BlackHuman.class);

        //第三次造人，火候刚好，产生黄人
        Human yellowHuman = YinYangLu.createHuman(YellowHuman.class);
    }
}
