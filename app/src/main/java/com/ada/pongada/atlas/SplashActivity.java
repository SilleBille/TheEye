package com.ada.pongada.atlas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ada.pongada.atlas.tts.TextToSpeechAPI;

import java.util.Locale;

/**
 * Created by mkdin on 05-11-2017.
 */


public class SplashActivity extends AppCompatActivity {
    /**
     * Duration of wait 2 sec *
     */
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    private TextToSpeech introSpeech;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        introSpeech = new TextToSpeech(SplashActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = introSpeech.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    TextToSpeechAPI.speak(introSpeech, "Hi! I am Retina. Your personal digital assistant");

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}

