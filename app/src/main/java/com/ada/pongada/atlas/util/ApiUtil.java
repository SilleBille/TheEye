package com.ada.pongada.atlas.util;

import android.util.Log;

import com.ada.pongada.atlas.client.VisionAPIClient;
import com.ada.pongada.atlas.client.VisionAPIService;
import com.ada.pongada.atlas.pojo.VisionLabelObject;
import com.ada.pongada.atlas.pojo.VisionRequestWrapper;
import com.ada.pongada.atlas.pojo.VisionResponseWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class ApiUtil {

    private ApiUtil() {}

    public static final String VISION_BASE_URI = "https://vision.googleapis.com";
    public static final String GOOGLE_API_KEY = "AIzaSyDQiKa6VOASACrBABdepX8x_aoYn03CPGU";

    public static VisionAPIService getVisionAPIService() {
        return VisionAPIClient.getClient(VISION_BASE_URI).create(VisionAPIService.class);
    }

    public static void sendPostVisionAPI(VisionRequestWrapper request) {
        VisionAPIService vision;
        vision = getVisionAPIService();

        vision.savePost(GOOGLE_API_KEY, request).enqueue(new Callback<VisionResponseWrapper>() {
            @Override
            public void onResponse(Call<VisionResponseWrapper> call, Response<VisionResponseWrapper> response) {
                Log.i("TAG", "Calling...");
                Log.i("TAG", "Response: " + response.raw().toString());
                if(response.isSuccessful()) {
                    Log.i("TAG", "Post submitted and successful " + response.body().toString());
                    Log.i("TAG", "VALULEJLFKJSDLKF: " + response.body().getResponses().get(0).getLabelAnnotations().get(0).getDescription());
                }
            }

            @Override
            public void onFailure(Call<VisionResponseWrapper> call, Throwable t) {
                Log.i("TAG", "Post Action failed with response: " + t.toString());
            }
        });
    }

}
