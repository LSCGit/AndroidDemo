package com.lsc.designpattern.ocp;

/**
 * Created by lsc on 2020-04-18 02:53.
 * E-Mail:2965219926@qq.com
 */
public class NovelBook implements IBook{

    private String name;
    private int price;
    private String author;

    public NovelBook(String name,int price,String author){
        this.author = author;
        this.name = name;
        this.price = price;
    }

    public String getAuthor(){
        return this.author;
    }

    public String getName(){
        return  this.name;
    }

    public int getPrice(){
        return this.price;
    }
}
