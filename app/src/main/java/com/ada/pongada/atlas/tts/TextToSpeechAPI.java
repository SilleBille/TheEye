package com.ada.pongada.atlas.tts;

import android.os.Build;
import android.speech.tts.TextToSpeech;

/**
 * Created by gsudan92 on 11/4/2017.
 */

public class TextToSpeechAPI {

    public static void speak(TextToSpeech tts, String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

}
