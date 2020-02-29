package com.lsc.qq;

/**
 * Created by lsc on 2020-02-25 15:56.
 * E-Mail:2965219926@qq.com
 */
public class TEst {

    public static void main(String[] args) {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 4;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;

        System.out.println(c == a);
        System.out.println(e == f);
        System.out.println(c == (a + b));
        System.out.println(c.equals(a + b));

        System.out.println(g == (a+b));
        System.out.println(g.equals(a + b) );

    }
}
