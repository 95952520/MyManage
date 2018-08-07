package util.api;


import com.xuchen.util.MyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpClientUtil;
import util.api.tencent.OcrApi;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * 发送短信
 */
public class MessageService {
    public static void main(String[] args) {
    }

    private static void doSend(String phone){
        m1(phone);
        m2(phone);
        m3(phone);
        m4(phone);
        m5(phone);
    }

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);
    private static final int codeCount = 3;
    private static void m1(String phone) {
        String url = "http://www.topart.cn/Share/Account/send6num.html";
        Map<String,String> map = new HashMap<>();
        map.put("account",phone);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map);
        } catch (Exception e) {
            logger.error(url+"短信发送失败",e);
        }
        logger.info("给[{}]发送短信成功",phone,MyUtils.decode(s));
    }

    private static void m2(String phone) {
        String url = "http://user.uuu9.com/api/sendSMS?callback=jsonp"+System.currentTimeMillis();
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map);
        } catch (Exception e) {
            logger.error(url+"短信发送失败",e);
        }
        logger.info("给[{}]发送短信成功[{}]",phone,MyUtils.decode(s));
    }

    private static void m3(String phone) {
        String url = "https://hqgq.com/user/register/send_verify_code";
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map);
            logger.info(MyUtils.decode(s));
        } catch (Exception e) {
            logger.error(url+"短信发送失败",e);
        }
        logger.info("给[{}]发送短信[{}]",phone,MyUtils.decode(s));
    }

    private static void m4(String phone) {
        String url = "http://passport.dance365.com/send-verify-code2?type=new";
        Map<String,String> map = new HashMap<>();
        map.put("mobile",phone);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map);
            logger.info(MyUtils.decode(s));
        } catch (Exception e) {
            logger.error(url+"短信发送失败",e);
        }
        logger.info("给[{}]发送短信[{}]",phone,MyUtils.decode(s));
    }

    private static void m5(String phone) {
        String url = "https://www.shanghaimaxicheng.com/user/ajax.ashx";
        Map<String,String> map = new HashMap<>();
        map.put("__op","1");
        map.put("cycode","+86");
        map.put("accountname",phone);
        map.put("imgcode","NONE");
        map.put("codetype","signup");
        String s = null;
        try {
            s = HttpClientUtil.sendPost(url, map);
            logger.info(MyUtils.decode(s));
        } catch (Exception e) {
            logger.error(url+"短信发送失败",e);
        }
        logger.info("给[{}]发送短信[{}]",phone,MyUtils.decode(s));
    }

    private static void m6(String phone) {
        String url = "https://weibo.com/signup/v5/formcheck?type=sendsms&value="+phone+"&zone=0086&__rnd="+System.currentTimeMillis();
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, null);
            logger.info(MyUtils.decode(s));
        } catch (Exception e) {
            logger.error(url+"短信发送失败",e);
        }
        logger.info("给[{}]发送短信[{}]",phone,MyUtils.decode(s));
    }

    /**
     * 有验证码demo(尚未完成)
     */
    private static void c1(String phone) {
        String code = null;
        int i = 0;
        while (i < codeCount){
            i++;
            BufferedImage bufferedImage = HttpClientUtil.getBufferedImage("https://captcha.tiancity.com/getimage.ashx?fid=400&tid=5256e0406ea94e86b6cf821396616146");
            code = OcrApi.doOcr(bufferedImage);
            if (code.length()>3){
                break;
            }
        }
        if (i == codeCount){
            logger.warn("验证码识别{}次失败",codeCount);
            return;
        }
        String url = "https://member.tiancity.com/Handler/SendHandler.ashx";
        HashMap<String,String> map = new HashMap<>();
        map.put("td","924171007ff6f02ad040df3a4eacde37");
        map.put("op","6");
        map.put("ud",phone);
        map.put("rc",code);
        String s = null;
        try {
            s = HttpClientUtil.sendGet(url, map,false,true);
            logger.info(MyUtils.decode(s));
        } catch (Exception e) {
            logger.error(url+"短信发送失败",e);
        }
        logger.info("给[{}]发送短信[{}],验证码[{}]共识别了{}次",phone,MyUtils.decode(s),code,i);
    }
}
