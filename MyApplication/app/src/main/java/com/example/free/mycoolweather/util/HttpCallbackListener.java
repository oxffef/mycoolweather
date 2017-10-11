package com.example.free.mycoolweather.util;

/**
 * Created by lenovo on 2017/10/11.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
