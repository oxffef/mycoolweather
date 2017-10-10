package com.example.free.mycoolweather.model;

/**
 * Created by lenovo on 2017/10/10.
 */

public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCityName(){
        return this.cityName;
    }
    public void setCityName(String CityName)
    {
        this.cityName = CityName;
    }
    public String getCityCode()
    {
        return this.cityCode;
    }
    public void setCityCode(String cityCode)
    {
        this.cityCode = cityCode;
    }
    public int getProvinceId()
    {
        return provinceId;
    }
    public void setProvinceId(int provinceId)
    {
        this.provinceId = provinceId;
    }
}
