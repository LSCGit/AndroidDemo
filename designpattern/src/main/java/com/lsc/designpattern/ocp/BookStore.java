package com.lsc.designpattern.ocp;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by lsc on 2020-04-18 02:56.
 * E-Mail:2965219926@qq.com
 */
public class BookStore {

    private final static ArrayList<IBook> bookList = new ArrayList<>();
    static{
        bookList.add(new NovelBook("天龙八部",3200,"金庸"));
        bookList.add(new NovelBook("巴黎圣母院",5600,"雨果"));
        bookList.add(new NovelBook("悲惨世界",3500,"雨果"));
        bookList.add(new NovelBook("金瓶梅",3200,"岚陵萧萧声"));
    }

    public static void main(String[] args) {
        NumberFormat format =NumberFormat.getCurrencyInstance();
        format.setMaximumFractionDigits(2);

    }
}
