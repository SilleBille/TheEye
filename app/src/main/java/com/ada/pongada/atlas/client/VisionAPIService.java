package com.ada.pongada.atlas.client;

import com.ada.pongada.atlas.pojo.VisionRequestWrapper;
import com.ada.pongada.atlas.pojo.VisionResponseWrapper;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public interface VisionAPIService {

    @POST("/v1/images:annotate")
    Call<VisionResponseWrapper> savePost(@Query("key") String api_key, @Body VisionRequestWrapper request);

}