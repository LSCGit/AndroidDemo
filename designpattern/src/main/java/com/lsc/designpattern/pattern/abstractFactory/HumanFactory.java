package com.lsc.designpattern.pattern.abstractFactory;

import com.lsc.designpattern.pattern.factory.AbstractHumanFactory;

/**
 * Created by lsc on 2020-04-18 12:36.
 * E-Mail:2965219926@qq.com
 */
public interface HumanFactory {

    public Human createYellowHuman();
    public Human createBlackHuman();
    public Human createWhiteHuman();

}
