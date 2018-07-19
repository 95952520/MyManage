package util.api.wechat;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.HttpClientUtil;

import java.util.HashMap;


public class WeChatApi {

    protected static final Logger logger = LoggerFactory.getLogger(WeChatApi.class);
    private static final String appID = "appID";
    private static final String appSecret = "appSecret";
    private static final String accessTokenApi = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String checkTokenApi = "https://api.weixin.qq.com/sns/auth";
    private static final String getUserInfoApi = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * 获取微信用户信息
     * @param code 用户同意授权时返回的code
     */
    public static WechatUserInfo getWechatUserInfo(String code){
        WechatUserInfo wechatUserInfo = null;
        try {
            HashMap<String,String> map = new HashMap<>();
            map.put("appid",appID);
            map.put("secret",appSecret);
            map.put("code",code);
            map.put("grant_type","authorization_code");
            WechatAccessToken wechatAccessToken = JSONObject.parseObject(HttpClientUtil.sendGet(accessTokenApi, map)).toJavaObject(WechatAccessToken.class);
            if (wechatAccessToken.getErrcode()!=null){
                logger.warn(wechatAccessToken.superToString());
                return null;
            }
            map = new HashMap<>();
            map.put("access_token",wechatAccessToken.getAccessToken());
            map.put("openid",wechatAccessToken.getOpenid());
            wechatUserInfo = JSONObject.parseObject(HttpClientUtil.sendGet(getUserInfoApi, map)).toJavaObject(WechatUserInfo.class);
            if (wechatUserInfo.getErrcode()!=null){
                logger.error(wechatUserInfo.superToString());
                return null;
            }
        } catch (Exception e) {
            logger.error("请求微信开放平台api失败",e);
        }
        return wechatUserInfo;
    }

}
