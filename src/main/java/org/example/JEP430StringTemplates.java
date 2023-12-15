package org.example;

import static java.lang.StringTemplate.RAW;
import static java.util.FormatProcessor.FMT;

public class JEP430StringTemplates {

    public static void main(String[] args) {
        int x = 10, y = 2;
        String s = STR."\{x} + \{y} = \{x + y}";
        System.out.println(s);

        StringTemplate str = RAW."\{x} + \{y} = \{x + y}";
        String info = STR.process(str);
        System.out.println(info);

        String s2 = FMT."%03d\{x} + %03d\{y} = %03d\{x + y}";
        System.out.println(s2);

    }
}
