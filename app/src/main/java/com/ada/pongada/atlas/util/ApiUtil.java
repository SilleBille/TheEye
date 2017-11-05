package com.ada.pongada.atlas.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import com.ada.pongada.atlas.client.VisionAPIClient;
import com.ada.pongada.atlas.client.VisionAPIService;
import com.ada.pongada.atlas.pojo.VisionLabelObject;
import com.ada.pongada.atlas.pojo.VisionLogoObject;
import com.ada.pongada.atlas.pojo.VisionRequestWrapper;
import com.ada.pongada.atlas.pojo.VisionResponse;
import com.ada.pongada.atlas.pojo.VisionResponseWrapper;
import com.ada.pongada.atlas.tts.TextToSpeechAPI;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.R.id.edit;
import static android.R.id.message;
import static android.R.id.shareText;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class ApiUtil {

    public static final String VISION_BASE_URI = "https://vision.googleapis.com";
    public static final String GOOGLE_API_KEY = "AIzaSyDQiKa6VOASACrBABdepX8x_aoYn03CPGU";
    public static SharedPreferences sharedpreferences;

    private ApiUtil() {
    }

    public static VisionAPIService getVisionAPIService() {
        return VisionAPIClient.getClient(VISION_BASE_URI).create(VisionAPIService.class);
    }

    public static void sendPostVisionAPI(VisionRequestWrapper request, final Context context, final Activity ac,
                                         final TextToSpeech tts) {
        VisionAPIService vision;
        vision = getVisionAPIService();

        sharedpreferences = context.getSharedPreferences("Retina", Context.MODE_PRIVATE);

        vision.savePost(GOOGLE_API_KEY, request).enqueue(new Callback<VisionResponseWrapper>() {
            @Override
            public void onResponse(Call<VisionResponseWrapper> call, final Response<VisionResponseWrapper> response) {
                Log.i("TAG", "Calling...");
                Log.i("TAG", "Response: " + response.raw().toString());
                if(response.isSuccessful()) {
                    // Display toast on UI thread
                    ac.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // JSONObject jb = response.body().getResponses().get(0).getLabelAnnotations().get(0).getDescription())
                            List<VisionLabelObject> rsL = response.body().getResponses().get(0).getLabelAnnotations();
                            List<VisionLogoObject> rsLogo = response.body().getResponses().get(0).getLogoAnnotations();
                            Set<String> logo = new LinkedHashSet<String>();
                            Set<String> labels = new LinkedHashSet<String>();

                            for(int i=0; rsL!= null && i<rsL.size(); i++) {
                                Log.i("Label", rsL.get(i).getDescription());
                                labels.add(rsL.get(i).getDescription());
                            }

                            for(int i=0; rsLogo!=null && i<rsLogo.size(); i++) {
                                Log.i("LOGO", rsLogo.get(i).getDescription());
                                logo.add(rsLogo.get(i).getDescription());
                            }

                            String brand = "";
                            if (logo.size() > 0) {
                                //brand += "Brand ";
                                brand += logo.toArray()[0];
                            }

                            String label = "";
                            if (labels.size() > 0) {
                                //label += "Labels ";
                                for (String t: labels) {
                                    label += t + ", ";
                                }
                            }

                            String text = "";
                            if (brand.length() > 0) {
                                text += brand;
                                text += ", ";
                            }

                            if (label.length() > 0) {
                                text += label;
                            }

                            if (text.length() > 0) {
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    Set<String> shoppingList = sharedpreferences.getStringSet("sl",new TreeSet<String>());
                                    Set<String> actionList = sharedpreferences.getStringSet("al",new TreeSet<String>());
                                    int exploreMode = sharedpreferences.getInt("em",0);
                                    Iterator iterator = shoppingList.iterator();
                                    String talk = null;
                                    boolean flag = false;
                                    if(actionList.size()==0) {
                                        String removeString = null;
                                        while (iterator.hasNext()) {
                                            String currentString = (String) iterator.next();
                                            String text1 = text.replace(",", "");
                                            String words[] = text1.split(" ");
                                            for (int i = 0; i < words.length; ++i) {
                                                if (words[i].equalsIgnoreCase(currentString)) {
                                                    flag = true;
                                                    removeString = currentString;
                                                    talk = currentString;
                                                    break;
                                                }
                                            }
                                        }
                                        if (flag && talk != null && exploreMode == 0) {
                                            shoppingList.remove(removeString);
                                            editor.putStringSet("sl", shoppingList);
                                            editor.commit();
                                            TextToSpeechAPI.speak(tts, "Found item " + talk);
                                        }
                                        else if(exploreMode == 1) {
                                            TextToSpeechAPI.speak(tts, text);
                                        }
                                    }
                                    else {
                                        String speech = "";
                                        while(iterator.hasNext()) {
                                            String currentString = (String) iterator.next();
                                            speech += currentString;
                                            speech += ", ";
                                        }
                                        if(!speech.equalsIgnoreCase("")) {
                                            TextToSpeechAPI.speak(tts, speech);
                                        }
                                        else {
                                            TextToSpeechAPI.speak(tts, "Shopping list is empty");
                                        }
                                        actionList = new TreeSet<String>();
                                        editor.putStringSet("al",actionList);
                                        editor.commit();
                                    }
                            }

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<VisionResponseWrapper> call, Throwable t) {
                Log.i("TAG", "Post Action failed with response: " + t.toString());
            }
        });
    }

}
