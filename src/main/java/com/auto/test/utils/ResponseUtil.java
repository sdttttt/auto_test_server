package com.auto.test.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ResponseUtil {
  private static final HttpClientBuilder clientBuilder;
  
  private static final RequestConfig requestConfig;
  
  private static final Integer CONNECT_TIME_OUT = 6000;
  
  private static final Integer SOCKET_TIME_OUT = 30 * 1000;
  
  private static final PoolingHttpClientConnectionManager connectionManager;
  
  static {
    connectionManager = new PoolingHttpClientConnectionManager();
    connectionManager.setMaxTotal(10);
    requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT)
        .setSocketTimeout(SOCKET_TIME_OUT).build();
    clientBuilder = HttpClients.custom().setConnectionTimeToLive(100, TimeUnit.SECONDS)
        .setConnectionManager(connectionManager)
        .setConnectionManagerShared(true)
        .setDefaultRequestConfig(requestConfig);
  }
  
  public static void responseJson(HttpServletResponse response, int status, Object data) {
    try {
//			跨域解决
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "*");
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(status);
      
      response.getWriter().write(JSONObject.toJSONString(data));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static String doPost(String url, String json) {
    log.info("doPost url: {}, username: {}", url);
    String responseEntity = null;
    CloseableHttpClient httpClient = HttpClients.custom().build();
    HttpPost httpPost = new HttpPost();
    try {
      httpPost.setConfig(requestConfig);
      httpPost.setURI(new URI(url));
      httpPost.addHeader("Content-type", "application/json; charset=utf-8");
      // httpPost.addHeader("Authorization", "Basic " +
      //     Base64.getUrlEncoder().encodeToString((username + ":" + password).getBytes()));
      httpPost.setEntity(new StringEntity(json, Charset.forName("UTF-8")));
      HttpResponse httpResponse = httpClient.execute(httpPost);
      HttpEntity entity = httpResponse.getEntity();
      responseEntity = EntityUtils.toString(entity);
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    } finally {
      if (httpClient != null) {
        try {
          httpClient.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return responseEntity;
  }
}
