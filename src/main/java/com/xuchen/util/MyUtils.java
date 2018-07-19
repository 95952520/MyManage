package com.xuchen.util;

import com.xuchen.entity.SysUser;
import io.netty.util.internal.ThreadLocalRandom;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

public final class MyUtils {

    protected static final Logger logger = LoggerFactory.getLogger(MyUtils.class);
    private static final String randomStr = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghjklmnpqrstuvwxyz123456789";

    public static Date getDateByLocal(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }


    /**
     * 获取用户IP
     * 线上环境是nginx反向代理，增加一层x-real-ip
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
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
     * @return 任意一个为空则返回true
     */
    public static boolean isEmpty(Object... strings) {
        if (strings==null){
            return true;
        }
        for (Object string : strings) {
            if (string == null || "".equals(String.valueOf(string).trim()))
                return true;
        }
        return false;
    }

    /**
     * 校验是否为空
     */
    public static boolean isNotEmpty(Object string) {
        return !isEmpty(string);
    }

    /**
     * 获取随机数
     * @param count 获得随机数数量
     */
    public static String getRandomStr(Integer count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(randomChar());
        }
        return sb.toString();
    }

    public static void encrypPassword(SysUser sysUser) {
        String newPassword = new SimpleHash("md5", sysUser.getPassword(), ByteSource.Util.bytes(sysUser.getUserName()), 2).toHex();
        sysUser.setPassword(newPassword);
    }


    public static void downloadFileFromUrl(String fileUrl, File newFile) {
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
     *
     * @param str     图片的str
     * @param newFile 生成的文件
     */
    public static void createFileFromStr(String str, File newFile) throws IOException {
        String imgString = str.substring(str.indexOf(",") + 1);
        byte[] decode = Base64.getDecoder().decode(imgString);
        FileOutputStream os = new FileOutputStream(newFile);
        os.write(decode);
        os.close();
    }

    public static Object getFieldValue(Object myEntity, String column) {
        if (myEntity == null){
            return null;
        }
        Field fields[] = myEntity.getClass().getDeclaredFields();
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            if (column.equals(field.getName())) {
                try {
                    return field.get(myEntity);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        logger.warn("[" + myEntity.getClass() + "]没有成员变量[" + column + "]");
        return null;
    }


    public static String transLateByEnum(String toTranslate, Class<? extends Enum> enumClass) {
        return transLateByEnum(toTranslate, enumClass, ",", "、");
    }


    public static String transLateByEnum(String toTranslate, Class<? extends Enum> enumClass, String oldSplitChar, String newSplitChar) {
        String[] split = toTranslate.split(oldSplitChar);
        StringBuilder sb = new StringBuilder();
        try {
            for (int i = 0; i < split.length; i++) {
                sb.append(enumClass.getMethod("getValueById", Integer.TYPE).invoke(enumClass, Integer.valueOf(split[i])));
                if (i != split.length - 1) {
                    sb.append(newSplitChar);
                }
            }
            return sb.toString();
        } catch (Exception e) {
            logger.warn("[{}]转化根据枚举类[{}]转换失败", toTranslate, enumClass);
            return "";
        }
    }

    public static String arrayToString(Object[] arr) {
        if (arr == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i != arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * 比较两个yyyy-MM-dd HH:mm:ss/HH:mm:ss
     * 第一个时间是否在第二个之前
     */
    public static boolean isBefore(String dateStr,String comparedDateStr) {
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!dateStr.contains("-")){
            dateStr = "1970-01-01 "+dateStr;
            comparedDateStr = "1970-01-01 "+comparedDateStr;
        }
        try {
            return s.parse(dateStr).before(s.parse(comparedDateStr));
        } catch (ParseException e) {
            logger.warn("比较的时间[{}]、[{}]转化date失败",dateStr,comparedDateStr);
            return false;
        }
    }


    private static char randomChar() {
        return randomStr.charAt(ThreadLocalRandom.current().nextInt(randomStr.length()));
    }

}
