package demo;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Demo {
    public static void main(String[] args) throws Exception {
        BufferedInputStream bi = null;
        BufferedOutputStream bo = null;
        File file = new File("D:/1.jpg");
        URL url = new URL("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1525704277448&di=86769a13a86161e" +
                "6c1fdd469abe77205&imgtype=0&src=http%3A%2F%2Fwww.chinairn.com%2FUserFiles%2Fimage%2F20180507%2F20180507144852_5184.jpg");
        try {
            bi = new BufferedInputStream(url.openStream());
            bo = new BufferedOutputStream(new FileOutputStream(file));
            byte[] by = new byte[1024];
            int len;
            while ((len = bi.read(by)) != -1) {
                bo.write(by, 0, len);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (bo != null)
                    bo.close();
                if (bi != null)
                    bi.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
