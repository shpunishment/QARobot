package com.shpun.qarobot.Util;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by shpun on 2018/2/24.
 */

public class HttpUtil {

    // 通过参数得到 utl地址，请求的post的RequestBody , 以及执行完后的 回调
    public static void sendOkHttpRequest(String address, RequestBody body, okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).post(body).build();
        client.newCall(request).enqueue(callback);
    }

}
