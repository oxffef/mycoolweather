package com.example.free.mycoolweather.model;

/**
 * Created by lenovo on 2017/10/10.
 */

public class Province {
    private  int id;
    private String provinceName;
    private String provincecode;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getProvinceName()
    {
        return provinceName;
    }
    public void setProvinceName(String provinceName)
    {
        this.provinceName = provinceName;
    }
    public String getProvincecode()
    {
        return provincecode;
    }
    public void setProvincecode(String ProvinceCode){ this.provincecode = ProvinceCode;}
}
