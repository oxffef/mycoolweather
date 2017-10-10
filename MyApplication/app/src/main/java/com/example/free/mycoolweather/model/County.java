package com.example.free.mycoolweather.model;

/**
 * Created by lenovo on 2017/10/10.
 */

public class County {
    private int id;
    private String countyName;
    private String countyCode;
    private int cityId;

    public int getId(){
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }
    public String getCountyName()
    {
        return countyName;
    }
    public void setCountyName(String countyName){
        this.countyName = countyName;
    }
    public String getContyCode()
    {
        return countyCode;
    }
    public void setCountyCode(String countyCode)
    {
        this.countyCode = countyCode;
    }
    public int getCityId()
    {
        return cityId;
    }
    public void setCityId(int cityId){
        this.cityId = cityId;
    }
}
