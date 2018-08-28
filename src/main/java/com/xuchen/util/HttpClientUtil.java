package com.xuchen.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLContext;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 可保存会话的HttpClient
 * 不知道为什么sessionId为"PHPSESSID"时，会话无法保存
 */
public class HttpClientUtil {

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(5000)
            .setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000)
            .build();
    private static SSLConnectionSocketFactory sslsf;
    private static final CloseableHttpClient httpClient;
    private static HttpClientBuilder httpClientBuilder;
    private static ThreadLocal<CookieStore> threadLocalCookieStore = new ThreadLocal<>();

    static {
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sslsf = new SSLConnectionSocketFactory(sslContext);
        httpClientBuilder = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setDefaultRequestConfig(requestConfig)
                .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
        httpClient = httpClientBuilder.build();
    }

    public static String sendGet(String url, Map<String, String> param) throws Exception {
        return sendGet(url, param, false, false);
    }

    public static String sendGet(String url, Map<String, String> param, boolean saveCookie, boolean doGetCookie) throws Exception {
        URIBuilder builder = new URIBuilder(url);
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> obj : param.entrySet()) {
                builder.addParameter(obj.getKey(), obj.getValue());
            }
        }
        HttpGet httpGet = new HttpGet(builder.build());
        return doRequest(httpGet, saveCookie, doGetCookie);
    }

    /**
     * 读取图片默认保存cookie
     */
    public static BufferedImage getBufferedImage(String url) {
        try {
            URIBuilder builder = new URIBuilder(url);
            HttpGet httpGet = new HttpGet(builder.build());
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                saveCookie(response);
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    byte[] bytes = EntityUtils.toByteArray(resEntity);
                    BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes));
                    return bufferedImage;
                }
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String sendPost(String url, Map<String, String> param) throws IOException {
        return sendPost(url, param, false, false);
    }

    public static String sendPost(String url, Map<String, String> param, boolean saveCookie, boolean doGetCookie) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (param != null && !param.isEmpty()) {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> obj : param.entrySet()) {
                list.add(new BasicNameValuePair(obj.getKey(), obj.getValue()));
            }
            HttpEntity entity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(entity);
        }
        return doRequest(httpPost, saveCookie, doGetCookie);
    }

    public static String doRequest(HttpRequestBase requestBase) throws IOException {
        return doRequest(requestBase, false, false);
    }

    /**
     * @param saveCookie  是否保存会话
     * @param doGetCookie 是否读取已保存的会话(cookie)
     */
    public static synchronized String doRequest(HttpRequestBase requestBase, boolean saveCookie, boolean doGetCookie) throws IOException {
        CloseableHttpClient tempClient;
        if (doGetCookie) {
            CookieStore cookieStore = threadLocalCookieStore.get();
            if (cookieStore == null){
                throw new RuntimeException("没有保存的cookie信息");
            }
            System.out.println(cookieStore.getCookies());
            tempClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
        } else {
            tempClient = httpClient;
        }
        try (CloseableHttpResponse response = tempClient.execute(requestBase)) {
            if (saveCookie) {
                saveCookie(response);
            }
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                return EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
            }
            return null;
        } finally {
            if (doGetCookie && tempClient != null) {
                tempClient.close();
            }
        }
    }


    private static void saveCookie(CloseableHttpResponse response) {
        if (response.getFirstHeader("Set-Cookie") == null){//说明已经保存了会话
            return;
        }
        String[] splitCookie = response.getFirstHeader("Set-Cookie").getValue().split("; ");
        BasicClientCookie clientCookie = null;
        for (int i = 0; i < splitCookie.length; i++) {
            String[] split = splitCookie[i].split("=");
            if (i == 0){
                clientCookie = new BasicClientCookie(split[0], split[1]);
            }else {
                if (split[0].contains("domain") || split[0].contains("DOMAIN")) {
                    clientCookie.setDomain(split[1]);
                }
                if (split[0].contains("path") || split[0].contains("PATH")) {
                    clientCookie.setPath(split[1]);
                }
            }
        }
        if (clientCookie == null) {
            return;
        }
        if (clientCookie.getDomain() == null){
            clientCookie.setDomain("localhost");
        }
        clientCookie.setSecure(false);
        CookieStore cookieStore = new BasicCookieStore();
        cookieStore.addCookie(clientCookie);
        threadLocalCookieStore.set(cookieStore);
    }
}
