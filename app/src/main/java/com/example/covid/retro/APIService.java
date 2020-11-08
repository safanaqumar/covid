package com.example.covid.retro;


import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface
APIService {

    @Headers({
            "Content-Type:application/json"
    })

    @GET("maps/api/geocode/json")
    Call<JsonElement> getAddressFromLocation(@Query("latlng") String latlng, @Query("key") String key);
}
