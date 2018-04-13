package com.xuchen.util;

import com.xuchen.entity.SysUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MyUtils {

    private static Calendar calendar = Calendar.getInstance();
    private static Random random = new Random();
    private final static String randomStr = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjklmnpqrstuvwxyz123456789";

    /**
     * 获取当前年月
     */
    public static String getYearMonth() {
        return new SimpleDateFormat("yyyyMM").format(new Date());
    }

    /**
     * 获取当前月日
     */
    public static String getMonthDay() {
        return new SimpleDateFormat("MMdd").format(new Date());
    }

    /**
     * 获取当前年
     */
    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int i = calendar.get(Calendar.YEAR);
        return i;
    }

    public static int getNowUnixTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }

    public static int getUnixTime(Date date) {
        return (int) (date.getTime() / 1000);
    }

    /**
     * 获取传入时间的变化值
     *
     * @param type  1.时 2.天 3.月
     * @param value 该时间单位的变化值
     * @return
     */
    public static Date getQueryDate(Date origin, Integer type, Integer value) {
        calendar.setTime(origin);
        switch (type) {
            case 1:
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + value);
                break;
            case 2:
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + value);
                break;
            case 3:
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + value);
                break;
            default:
                return null;
        }
        return calendar.getTime();
    }

    /**
     * 获取该天23:59:59的Date
     *
     * @return
     */
    public static Date getEndOfDay(Date origin) {
        calendar.setTime(origin);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取该天剩下的秒数
     *
     * @return
     */
    public static long getLeftSecends() {
        Date date = new Date();
        Date endOfDay = getEndOfDay(date);
        return (endOfDay.getTime() - date.getTime()) / 1000;
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
        if (string == null || "".equals(String.valueOf(string).trim()))
            return false;
        return true;
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
}
