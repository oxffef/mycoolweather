package com.example.free.mycoolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.free.mycoolweather.model.City;
import com.example.free.mycoolweather.model.CoolWeatherDB;
import com.example.free.mycoolweather.model.County;
import com.example.free.mycoolweather.model.Province;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/10/11.
 */

public class ChooseAreaActivity extends Activity {
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private CoolWeatherDB coolWeatherDB;
    private List<String> dataList = new ArrayList<String>();
    /**
     * 省列表
     */
    private  List<Province> provinceList;
    /**
     * 市列表
     */
    private List<City> cityList;
    /**
     * 县列表
     */
    private List<County> countyList;
    /**
     * 选中的城市
     */
    private City selectdCity;
    /**
     * 选中的省份
     */
    private Province selectedProvince;
    /**
     * 当前选中的级别
     */
    private int currentLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate();
    }

}

