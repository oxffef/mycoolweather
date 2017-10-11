package com.example.free.mycoolweather.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.free.mycoolweather.R;
import com.example.free.mycoolweather.model.City;
import com.example.free.mycoolweather.model.CoolWeatherDB;
import com.example.free.mycoolweather.model.County;
import com.example.free.mycoolweather.model.Province;
import com.example.free.mycoolweather.util.HttpCallbackListener;
import com.example.free.mycoolweather.util.HttpUtil;
import com.example.free.mycoolweather.util.Utility;

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
    private City selectedCity;
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
                }else if(currentLevel == LEVEL_CITY)
                {
                    selectedCity = cityList.get(index);
                    queryCounties();
                }
            }
        });
        queryProvinces();
    }
    /**
     * 查找选中省内所有的市 优先从数据库中查询，如果没有则去服务器上查询。
     */
    private  void queryProvinces()
    {
        provinceList = coolWeatherDB.loadProvinces();
        if(provinceList.size() > 0)
        {
            dataList.clear();
            for(Province province: provinceList)
            {
                dataList.add(province.getProvinceName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText("中国");
            currentLevel = LEVEL_PROVINCE;
        }else
        {
            queryFromserver(null,"province");
        }
    }
    /**
     * 查询选中省内所有的市，优先从数据库查询，如果还没有查询到服务器上查询
     */
    private void queryCities()
    {
        cityList = coolWeatherDB.loadCities(selectedProvince.getId());
        if(cityList.size() > 0) {
            dataList.clear();
            for (City city : cityList) {
                dataList.add(city.getCityName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedProvince.getProvinceName());
            currentLevel = LEVEL_CITY;
        }else
        {
            queryFromserver(selectedProvince.getProvincecode(),"city");
        }
    }
    /**
     * 查询选中的市内所有的县，优先从数据库查询，如果没有则去服务器查询
     */
    private void queryCounties()
    {
        countyList =coolWeatherDB.loadConties(selectedCity.getId());
        if(countyList.size() > 0)
        {
            dataList.clear();
            for(County county: countyList)
            {
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            titleText.setText(selectedCity.getCityName());
            currentLevel = LEVEL_COUNTY;
        }
        else
        {
            queryFromserver(selectedCity.getCityCode(),"county");
        }
    }
    private void queryFromserver(final String code, final String type)
    {
        String address;
        if(!TextUtils.isEmpty(code))
        {
            address = "http://www.weather.com.cn/data/list3/city" + code + ".xml";
        }
        else
        {
            address = "http://www.weather.com.cn/data/list3/city.xml";
        }
        showProgressDialog();
        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result = false;
                if("province".equals(type))
                {
                    result = Utility.handleProvincesPesponse(coolWeatherDB,response);
                }
                else if("city".equals(type))
                {
                    result = Utility.handleCitiesResponse(coolWeatherDB,response,selectedProvince.getId());
                }else if("county".equals(type))
                {
                    result = Utility.handleCountiesResponse(coolWeatherDB,response,selectedCity.getId());
                }
                if(result)
                {
                    //通过runOnUIThread（） 返回到主线程处理逻辑
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDilog();
                            if("province".equals(type))
                            {
                                queryProvinces();
                            }else if ("city".equals(type))
                            {
                                queryCities();
                            }else if("county".equals(type))
                            {
                                queryCounties();
                            }

                        }
                    });
                }
            }

            @Override
            public void onError(Exception e) {
                //通过runOnUIThread（） 返回到主线程处理逻辑
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDilog();
                        Toast.makeText(ChooseAreaActivity.this, "加载失败",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    /**
     * 显示进度对话框
     */
    private void showProgressDialog()
    {
        if(progressDialog == null)
        {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
    /**
     * 关闭进度对话框
     */
    private  void closeProgressDilog()
    {
        if(progressDialog != null)
        {
            progressDialog.dismiss();
        }
    }
    /**
     * 捕获back按键 根据当前的级别来判断 此时应该返回市列表省列表还是退出
     */
    @Override
    public  void onBackPressed()
    {
        if(currentLevel == LEVEL_COUNTY)
        {
            queryCities();
        }else if(currentLevel == LEVEL_CITY)
        {
            queryProvinces();
        }else
        {
            finish();
        }
    }
}






