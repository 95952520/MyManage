package com.xuchen.util.tencent;

import com.alibaba.fastjson.JSONObject;
import com.xuchen.util.CutImgUtil;
import com.xuchen.util.HttpClientUtil;
import org.apache.http.Consts;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Base64;
import java.util.Random;

public class OcrApi {

    private static final long appid = 1257229926;
    private static final String SecretID = "SecretID";
    private static final String SecretKey = "SecretKey";
    private static final Logger logger = LoggerFactory.getLogger(OcrApi.class);
    private static final HttpPost httpPost = new HttpPost("https://recognition.image.myqcloud.com/ocr/general");
    static {
        httpPost.addHeader("host", "recognition.image.myqcloud.com");
        httpPost.addHeader("authorization", "JUrmqZ2341SyFPg1516UjxtwKJ1hPTEyNTcyMjk5MjYmYj0maz1BS0lEVXM2QTV0eWNEY3J1QUhyM2UzSEo5cUxncTE0d004UVomdD0xNTMzMTA5NzcwJmU9MTUzODI5Mzc3MCZyPTE4NzkxMTk1NzA=");
    }

    public static String doOcr(BufferedImage bufferedImage){
        String jsonResult;
        String result = null;
        try {
            File file = new File("D://"+System.currentTimeMillis()+".jpg");
            CutImgUtil.zipImg(bufferedImage,"jpg",3,file);//防过小图片无法识别
            MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
            entityBuilder.addPart("image", new FileBody(file));
            entityBuilder.addPart("appid", new StringBody(String.valueOf(appid), ContentType.create("text/plain", Consts.UTF_8)));
            httpPost.setEntity(entityBuilder.build());
            jsonResult = HttpClientUtil.doRequest(httpPost);
            result = getItemString(jsonResult);
        } catch (Exception e) {
            logger.warn("发送识别验证码请求失败",e);
        }
        return result;
    }

    /**
     * 只针对单行验证码的获取
     *
     * @return
     */
    private static String getItemString(String jsonResult) {
        String result = "";
        JSONObject jsonObject = JSONObject.parseObject(jsonResult);
        if (jsonObject.getInteger("code") == 0) {
            try {
                return jsonObject.getJSONObject("data").getJSONArray("items").getJSONObject(0).getString("itemstring").trim();
            }catch (Exception e){
                logger.warn("解析jsonResult[{}]返回失败", jsonResult,e);
            }
        } else {
            logger.warn("第三方Ocr返回[{}]失败",jsonObject);
        }
        return result;
    }


    /**
     * https://cloud.tencent.com/document/product/866/17734
     */
    private static class Sign {

        private static String getSign() throws Exception {
            return appSign(appid, SecretID, SecretKey, "", 60 * 60 * 24 * 60);
        }

        private static String appSign(long appId, String secretId, String secretKey, String bucketName,
                                      long expired) throws Exception {
            long now = System.currentTimeMillis() / 1000;
            int rdm = Math.abs(new Random().nextInt());
            String plainText = String.format("a=%d&b=%s&k=%s&t=%d&e=%d&r=%d", appId, bucketName,
                    secretId, now, now + expired, rdm);
            byte[] hmacDigest = HmacSha1(plainText, secretKey);
            byte[] signContent = new byte[hmacDigest.length + plainText.getBytes().length];
            System.arraycopy(hmacDigest, 0, signContent, 0, hmacDigest.length);
            System.arraycopy(plainText.getBytes(), 0, signContent, hmacDigest.length,
                    plainText.getBytes().length);
            return Base64Encode(signContent);
        }

        /**
         * 生成 base64 编码
         *
         * @param binaryData
         * @return
         */
        private static String Base64Encode(byte[] binaryData) {
            String encodedstr = Base64.getEncoder().encodeToString(binaryData);
            return encodedstr;
        }

        /**
         * 生成 hmacsha1 签名
         *
         * @param binaryData
         * @param key
         * @return
         * @throws Exception
         */
        private static byte[] HmacSha1(byte[] binaryData, String key) throws Exception {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");
            mac.init(secretKey);
            byte[] HmacSha1Digest = mac.doFinal(binaryData);
            return HmacSha1Digest;
        }

        /**
         * 生成 hmacsha1 签名
         *
         * @param plainText
         * @param key
         * @return
         * @throws Exception
         */
        private static byte[] HmacSha1(String plainText, String key) throws Exception {
            return HmacSha1(plainText.getBytes(), key);
        }
    }


}
