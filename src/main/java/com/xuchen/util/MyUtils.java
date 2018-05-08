package com.xuchen.util;

import com.xuchen.entity.SysUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MyUtils {

    private static Calendar calendar = Calendar.getInstance();
    private static Random random = new Random();
    private final static String randomStr = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjklmnpqrstuvwxyz123456789";

    public static Date getDateByLocal(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }



    /**
     * 获取用户真实IP
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 若用户经过多层代理则request.getHeader("x-forwarded-for")返回的为多个IP
        // 用逗号分隔，获取其中不为unknown的第一个IP作为用户的IP
        if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (int i = 0; i < ips.length; i++) {
                if (!ips[i].equals("unknown")) {
                    ip = ips[i];
                    break;
                }
            }
        }
        return ip;
    }

    /**
     * 校验是否为空
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(Object string) {
        if (string == null || "".equals(String.valueOf(string).trim()))
            return true;
        return false;
    }

    /**
     * 校验是否为空
     *
     * @param string
     * @return
     */
    public static boolean isNotEmpty(Object string) {
        return !isEmpty(string);
    }

    /**
     * 校验Integer是否的该范围
     *
     * @param i     参数
     * @param begin 起始(含)
     * @param end   截止(含)
     */
    public static boolean isBetween(Integer i, Integer begin, Integer end) {
        if (i == null) {
            return false;
        }
        if (begin != null && i < begin) {
            return false;
        }
        if (end != null && i > end) {
            return false;
        }
        return true;
    }

    /**
     * 去掉url中域名部分
     *
     * @param url
     * @return
     */
    public static String splitUrlDomain(String url) {
        if (url.contains("http")) {
            return url.substring(url.indexOf("/", 10) + 1);
        }
        return url;
    }

    /**
     * 获取随机数
     *
     * @param count 获得随机数数量
     * @return
     */
    public static String getRandomStr(Integer count) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(randomChar());
        }
        return sb.toString();
    }

    public static void encrypPassword(SysUser sysUser){
        String newPassword = new SimpleHash("md5", sysUser.getPassword(),  ByteSource.Util.bytes(sysUser.getUserName()), 2).toHex();
        sysUser.setPassword(newPassword);
    }

    private static char randomChar() {
        return randomStr.charAt(random.nextInt(randomStr.length()));
    }

    public static void downloadFileFromUrl(String fileUrl,File newFile){
        BufferedInputStream bi = null;
        BufferedOutputStream bo = null;
        try {
            URL url = new URL(fileUrl);
            bi = new BufferedInputStream(url.openStream());
            bo = new BufferedOutputStream(new FileOutputStream(newFile));
            byte[] by = new byte[1024];
            int len;
            while ((len = bi.read(by)) != -1) {
                bo.write(by, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    /**
     * 将图片的str流转成文件
     * @param str 图片的str
     * @param newFile 生成的文件
     */
    public static void createFileFromStr(String str,File newFile) throws IOException {
        String imgString = str.substring(str.indexOf(",") + 1);
        BASE64Decoder d = new BASE64Decoder();
        byte[] bs = d.decodeBuffer(imgString);
        FileOutputStream os = new FileOutputStream(newFile);
        os.write(bs);
        os.close();
    }
}
