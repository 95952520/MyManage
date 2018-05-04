package demo;

import java.io.File;
import java.io.FileInputStream;

public class Demo {
    public static void main(String[] args) throws Exception {
        File file = new File("D://1.txt");
        FileInputStream inputStream = new FileInputStream(file);
    }
}
