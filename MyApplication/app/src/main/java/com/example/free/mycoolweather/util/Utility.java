package com.example.free.mycoolweather.util;

import android.text.TextUtils;

import com.example.free.mycoolweather.model.City;
import com.example.free.mycoolweather.model.CoolWeatherDB;
import com.example.free.mycoolweather.model.County;
import com.example.free.mycoolweather.model.Province;

/**
 * Created by lenovo on 2017/10/11.
 */

public class Utility {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public  synchronized  static boolean handleProvincesPesponse(CoolWeatherDB coolWeatherDB, String response)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allProvinces= response.split(",");
            if(allProvinces != null && allProvinces.length > 0)
            {
                for(String p : allProvinces)
                {
                    String[] array = p.split("\\|");
                    Province province = new Province();
                    province.setProvincecode(array[0]);
                    province.setProvinceName(array[1]);
                    //将解析出来的数据存储到Province表
                    coolWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
                                               String response, int provinceId)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allCities = response.split(',');
            if(allCities != null && allCities.length > 0)
            {
                for(String c: allCities)
                {
                    String[] arrary= c.split("\\|");
                    City city = new City();
                    city.setCityCode(arrary[0]);
                    city.setCityName(arrary[1]);
                    city.setProvinceId(provinceId);
                    //将解析出的数据存储到City表
                    coolWeatherDB.saveCity(city);
                }
                return true;
            }
        }
        return false;
    }
    /**
     * 解析和处理服务器返回的县级数据
     */
    public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId)
    {
        if(!TextUtils.isEmpty(response))
        {
            String[] allCounties = response.split(",");
            if(allCounties != null && allCounties.length > 0)
            {
                for(String c: allCounties)
                {
                    String[] arrary = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(arrary[0]);
                    county.setCountyName(arrary[1]);
                    county.setCityId(cityId);
                    //将解析的数据存入数据库County表中
                    coolWeatherDB.saveCounty(county);
                }
                return true;
            }
        }
        return false;
    }
}
