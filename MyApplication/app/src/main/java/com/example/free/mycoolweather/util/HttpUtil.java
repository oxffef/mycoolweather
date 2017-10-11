package com.example.free.mycoolweather.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 2017/10/11.
 */

public class HttpUtil {
    public static void  sendHttpRequest(final String address,
                                        final HttpCallbackListener lister)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if(lister != null)
                    {
                        //onFinish()回调
                        lister.onFinish(response.toString());
                    }
                }catch (Exception e)
                {
                    if(lister != null)
                    {
                        //回调onError方法
                        lister.onError(e);
                    }
                }finally {
                    if(connection != null)
                    {
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }
}
