package com.trycatch.laughingcolours.Networks;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API_Client
{

    public static final String BASE_URL = "http://mapi.trycatchtech.com";

    public static Retrofit retrofit = null;

    public static Retrofit getAPI_Client()
    {

        if (retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }

        return retrofit;

    }



}
