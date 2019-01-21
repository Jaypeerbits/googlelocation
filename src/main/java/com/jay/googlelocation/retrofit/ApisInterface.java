package com.jay.googlelocation.retrofit;

import java.util.HashMap;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApisInterface {
    @GET(API_ENDPOINTS.URL_DIRECTIONS)
    Call<ResponseBody> APICall(@Query("origin") String origin,
                               @Query("destination") String destination, @Query("key") String key);


    @POST
    Call<ResponseBody> APICall(@Url String endPoint, @HeaderMap HashMap<String, String> hashMap, @Body Object requestClass);

    @FormUrlEncoded
    @POST
    Call<ResponseBody> APICall(@Url String endPoint, @HeaderMap HashMap<String, String> hashMap, @FieldMap HashMap<String, String> fields);

    @Multipart
    @POST
    Call<ResponseBody> APIMultipartCall(@Url String endPoint, @HeaderMap HashMap<String, String> hashMap, @PartMap HashMap<String, RequestBody> fields);

    @GET
    Call<ResponseBody> APICall(@Url String endPoint, @HeaderMap HashMap<String, String> hashMap);

    @GET
    Call<ResponseBody> getMethod(@Url String endpoint);


    @Multipart
    @POST
    Call<ResponseBody> APICallMultipart(@Url String endpoint, @HeaderMap HashMap<String, String> hashMap, @PartMap HashMap<String, RequestBody> fields);
}
