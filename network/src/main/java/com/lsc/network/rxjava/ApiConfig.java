package com.lsc.network.rxjava;

import com.lsc.network.bean.MainModel;

import java.util.Observable;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by lsc on 2020-03-03 13:35.
 * E-Mail:2965219926@qq.com
 *
 */
public interface ApiConfig {

    //baseUrl
    String API_SERVER_URL = "http://www.weather.com.cn/";
    //加载天气
    //@GET("adat/sk/{cityId}.html")
    //Observable<MainModel> loadDataByRetrofitRxJava(@Path("cityId") String cityId);
}
