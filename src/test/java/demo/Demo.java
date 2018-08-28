package demo;


import com.sun.tools.javac.jvm.ClassReader;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javadoc.JavadocClassReader;
import com.sun.tools.javadoc.JavadocEnter;
import com.sun.tools.javadoc.JavadocTool;
import com.xuchen.entity.Goods;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Demo {

    /**
     * ceshi
     */
    private int i;

    public static void main(String[] args) throws Exception {
        Field[] fields = Demo.class.getDeclaredFields();
        Field.setAccessible(fields, true);
        Field f = fields[0];
        System.out.println(Arrays.toString(f.getAnnotations()));
    }
}
