package com.qcloud.cmq;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class HttpUtil {
    public static int timeout = 1000;
    public static String request(String method, String url, String data, int userTimeout){
        String result = "";
        if (method.equals("POST")) {
            result = httpPost(url,data,userTimeout);
        }
        else{
            result = httpGet(url, userTimeout);
        }
        return result;
    }


    private static String httpGet(String url, int userTimeout) {
        String result = null;
        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(userTimeout + timeout, TimeUnit.MILLISECONDS).readTimeout(userTimeout + timeout, TimeUnit.MILLISECONDS).build();
        Request request = new Request.Builder().url(url).build();
        try {
            Response response = client.newCall(request).execute();
            result = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送httppost请求
     *
     * @param url
     * @param data  提交的参数为key=value&key1=value1的形式
     * @return
     */
    private static String httpPost(String url, String data, int userTimeout) {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient().newBuilder().connectTimeout(userTimeout + timeout, TimeUnit.MILLISECONDS).readTimeout(userTimeout + timeout, TimeUnit.MILLISECONDS).build();
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*;charset=utf-8"), data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}