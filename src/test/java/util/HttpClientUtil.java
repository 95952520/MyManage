package util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000)
            .setConnectionRequestTimeout(5000).build();
    private static SSLConnectionSocketFactory sslsf;
    private static CloseableHttpClient httpClient;

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
        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(requestConfig).build();
    }

    public static String sendGet(String url, Map<String, String> param) throws Exception{
        URIBuilder builder = new URIBuilder(url);
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> obj : param.entrySet()) {
                builder.addParameter(obj.getKey(), obj.getValue());
            }
        }
        HttpGet httpGet = new HttpGet(builder.build());
        return doRequest(httpGet);
    }

    public static String sendPost(String url, Map<String, String> param) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (param != null && !param.isEmpty()) {
            List<NameValuePair> list = new ArrayList<>();
            for (Map.Entry<String, String> obj : param.entrySet()) {
                list.add(new BasicNameValuePair(obj.getKey(), obj.getValue()));
            }
            HttpEntity entity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(entity);
        }
        return doRequest(httpPost);
    }


    private static String doRequest(HttpRequestBase requestBase) throws IOException {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(requestBase);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                return EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
            }
            return null;
        } finally {
            if (response!=null){
                try {
                    response.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
