package com.example.api.data.source.remote;

import com.example.api.common.helper.Utils;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConnection {
   private static Retrofit retrofit;
   private static Retrofit getConnection(){
       if(retrofit == null) {

                   OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(18, TimeUnit.SECONDS)
                   .connectTimeout(8, TimeUnit.SECONDS)
                   .readTimeout(8, TimeUnit.SECONDS).retryOnConnectionFailure(false)
                   .addInterceptor(chain -> {
                       Request request = chain.request();
                       HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", Utils.API_KEY)
                               .build();
                       request = chain.request().newBuilder().url(url).build();

                       return chain.proceed(request);
                   }).build();
           retrofit = new Retrofit.Builder()
                   .baseUrl("https://api.themoviedb.org/3/")
                   .addConverterFactory(GsonConverterFactory.create())
                   .client(client)
                   .build();
       }
       return retrofit;
   }
   public static ApiRequest getInstance(){
        ApiRequest request = getConnection().create(ApiRequest.class);
       return request;
   }
}
