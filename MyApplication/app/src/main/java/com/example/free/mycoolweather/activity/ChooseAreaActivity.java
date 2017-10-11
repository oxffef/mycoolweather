package com.example.free.mycoolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.free.mycoolweather.R;
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
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_area);
        listView = (ListView) findViewById(R.id.list_view);
        titleText =(TextView) findViewById(R.id.title_text);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        coolWeatherDB = CoolWeatherDB.getInstance(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long ary3) {
                if(currentLevel == LEVEL_PROVINCE)
                {
                    selectedProvince = provinceList.get(index);
                    queryCities();

                }
            }
        });
    }
    /**
     * 查找选中省内所有的市 优先从数据库中查询，如果没有则去服务器上查询。
     */
    private  void queryCities()
    {
        cityList = coolWeatherDB.loadCities(selectedProvince.getId());
        if(cityList.size() > 0)
        {
            dataList.clear();
            for(City city: cityList)
            {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        }else
        {
            
        }
    }
}

