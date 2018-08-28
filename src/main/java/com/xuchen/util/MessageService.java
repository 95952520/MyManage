package com.xuchen.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xuchen.util.tencent.OcrApi;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 发送短信
 */
public class MessageService {
    public static void main(String[] args) {
    }

    public static void doSend(String phone) {
        m1(phone);
        m2(phone);
        m3(phone);
        m4(phone);
        m5(phone);
        m6(phone);
        m7(phone);
        m8(phone);
        m9(phone);
    }

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private static final int codeCount = 3;

    private static void m1(String phone) {
        String url = "http://www.topart.cn/Share/Account/send6num.html";
        Map<String, String> map = new HashMap<>();
        map.put("account", phone);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    private static void m2(String phone) {
        String url = "http://user.uuu9.com/api/sendSMS?callback=jsonp" + System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    private static void m3(String phone) {
        String url = "https://hqgq.com/user/register/send_verify_code";
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    private static void m4(String phone) {
        String url = "http://passport.dance365.com/send-verify-code2";
        Map<String, String> map = new HashMap<>();
        map.put("type", "new");
        map.put("mobile", phone);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    private static void m5(String phone) {
        String url = "https://www.shanghaimaxicheng.com/user/ajax.ashx";
        Map<String, String> map = new HashMap<>();
        map.put("__op", "1");
        map.put("cycode", "+86");
        map.put("accountname", phone);
        map.put("imgcode", "NONE");
        map.put("codetype", "signup");
        String s = null;
        try {
            s = HttpClientUtil.sendPost(url, map);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    private static void m6(String phone) {
        String url = "http://www.nbcsgo.com/userInfo/checkPhone.do?countryCode=86&phone="+phone;
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, null);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    private static void m7(String phone) {
        String url = "http://www.gongboshi.com/member/register.php";
        String s = null;
        Map<String, String> map = new HashMap<>();
        map.put("action", "5ffb9eb9c64cf881b6c4e6d8f58bb5e5");
        map.put("value", phone);
        try {
            s = HttpClientUtil.sendPost(url, map);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    private static void m8(String phone) {
        String url = "http://www.topart.cn/Share/Account/send6num.html";
        String s = null;
        Map<String, String> map = new HashMap<>();
        map.put("account", phone);
        try {
            s = HttpClientUtil.sendPost(url, map);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    private static void m9(String phone) {
        String url = "https://www.b5csgo.com.cn/code/sendPhoneCode.do";
        String s = null;
        Map<String, String> map = new HashMap<>();
        map.put("b5UserId", String.valueOf(ThreadLocalRandom.current().nextInt(1,1000)));
        map.put("codeType", "1");
        map.put("countryCode", "86");
        map.put("phone", phone);
        try {
            s = HttpClientUtil.sendPost(url, map);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        String decode = MyUtils.decode(s);
        logger.info("给[{}]发送短信成功[{}]", phone, decode.length()>50?decode.substring(0,50):decode);
    }

    /**
     * 有验证码demo(尚未完成)
     */
    private static void c1(String phone) {
        String code = null;
        int i = 0;
        while (i < codeCount) {
            i++;
            BufferedImage bufferedImage = HttpClientUtil.getBufferedImage("http://www.kede.com/image/validates?validKey=CheckCodeRegister&t=" + System.currentTimeMillis());
            code = OcrApi.doOcr(bufferedImage);
            if (code.length() == 4) {
                break;
            }
        }
        if (i == codeCount) {
            logger.warn("验证码识别{}次失败", codeCount);
            return;
        }
        String url = "http://www.kede.com/account/SendRegistValidationCode";
        HashMap<String, String> map = new HashMap<>();
        map.put("mobile", phone);
        map.put("validcode", code);
        map.put("uid", "");
        map.put("code", "");
        String s;
        String decode = "";
        try {
            s = HttpClientUtil.sendPost(url, map, false, true);
            decode = MyUtils.decode(s);
        } catch (Exception e) {
            logger.error(url + "短信发送失败", e);
        }
        logger.info("给[{}]发送短信[{}],验证码[{}]共识别了{}次", phone, decode.length()>50?decode.substring(0,50):decode, code, i);
    }
}
