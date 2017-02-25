package com.yapp.lazitripper.network;

import android.content.Context;
import android.util.Log;

import com.yapp.lazitripper.R;
import com.yapp.lazitripper.service.LaziTripperKoreanTourService;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ohdok on 2017-02-24.
 */

public class LaziTripperKoreanTourClient implements LaziTripperClient<LaziTripperKoreanTourService> {

    private Context context;
    private String BASE_URL;
    private String AUTHKEY;
    private LaziTripperKoreanTourService apiService;

    public LaziTripperKoreanTourClient(Context context){
        this.context = context;
        init();
    }

    @Override
    public void init() {
        BASE_URL = context.getResources().getString(R.string.korea_tour);
        AUTHKEY = context.getResources().getString(R.string.tour_key);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(
                new Interceptor() {
                    @Override
                    public Response intercept(Interceptor.Chain chain) throws IOException {
                        Request request = chain.request();
                        HttpUrl url = request.url().newBuilder().addQueryParameter("_type","json").addEncodedQueryParameter("ServiceKey",AUTHKEY).build();
                        Log.i("ohdoking-url",url.uri().toString());
                        request = request.newBuilder().url(url).build();
                        return chain.proceed(request);
                    }
                })
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        apiService = retrofit.create(LaziTripperKoreanTourService.class);

    }

    public LaziTripperKoreanTourService getLiziTripperService(){
        return apiService;
    }

}
