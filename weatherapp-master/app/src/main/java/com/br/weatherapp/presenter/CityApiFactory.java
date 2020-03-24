package com.br.weatherapp.presenter;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CityApiFactory {
    private static final String BASE_URL = "https://api.openweathermap.org/";
    private static CityApiInterface sApiInterface;

    public static CityApiInterface getCityClient()
    {
        Interceptor interceptor = new Interceptor()
        {
            @Override
            public Response intercept(Chain chain) throws java.io.IOException
            {
                Request newRequest = chain.request().newBuilder().build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.interceptors().add(interceptor);

        if(sApiInterface == null)
        {
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl(BASE_URL);
            builder.client(client.build());
            builder.addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            sApiInterface = retrofit.create(CityApiInterface.class);
        }
        return sApiInterface;
    }
}
