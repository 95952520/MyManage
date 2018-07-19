package util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadUtil {

    public static void urlToFileName(String url, String pathName) throws Exception {
        File f = new File(pathName);
        urlToFile(url,f);
    }

    public static void urlToFile(String url, File file) throws Exception{
        URL ul = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) ul.openConnection();
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        try (BufferedInputStream bi = new BufferedInputStream(conn.getInputStream());
             BufferedOutputStream bo = new BufferedOutputStream(new FileOutputStream(file))){
            byte[] by = new byte[1024];
            int len;
            while ((len = bi.read(by)) != -1) {
                bo.write(by, 0, len);
            }
        }
    }
}
