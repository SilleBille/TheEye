package com.ada.pongada.atlas;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ada.pongada.atlas.client.VisionAPIClient;
import com.ada.pongada.atlas.client.VisionAPIService;
import com.ada.pongada.atlas.pojo.VisionFeatureObject;
import com.ada.pongada.atlas.pojo.VisionImageObject;
import com.ada.pongada.atlas.pojo.VisionRequest;
import com.ada.pongada.atlas.pojo.VisionRequestWrapper;
import com.ada.pongada.atlas.pojo.VisionResponse;
import com.ada.pongada.atlas.pojo.VisionResponseWrapper;
import com.ada.pongada.atlas.tts.TextToSpeechAPI;
import com.ada.pongada.atlas.util.ApiUtil;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int PERMISSIONS_REQUEST = 32;

    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // Text to Speech Init
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }
                    //TextToSpeechAPI.speak(tts, "In the GitHub project I have included more examples for you to experiment with and build your own Android assistant.");
                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

        if (hasPermission()) {
            if (null == savedInstanceState) {
                setFragment();
            }
        } else {
            requestPermission();
        }

        // Sample Request
        String content = getString(R.string.SampleString);
        VisionImageObject image = new VisionImageObject();
        image.setContent(content);

        VisionFeatureObject feature1 = new VisionFeatureObject();
        feature1.setType("LOGO_DETECTION");
        feature1.setMaxResult(1);

        VisionFeatureObject feature2 = new VisionFeatureObject();
        feature2.setType("LABEL_DETECTION");
        feature2.setMaxResult(1);

        VisionRequest requestData = new VisionRequest();
        requestData.setImage(image);
        requestData.setFeatures(new ArrayList<VisionFeatureObject>());
        requestData.getFeatures().add(feature1);
        requestData.getFeatures().add(feature2);

        VisionRequestWrapper requests = new VisionRequestWrapper();
        requests.setRequests(new ArrayList<VisionRequest>());
        requests.getRequests().add(requestData);

        // ApiUtil.sendPostVisionAPI();
        AsyncTaskRunner runner = new AsyncTaskRunner();
        runner.execute(requests);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    setFragment();
                } else {
                    requestPermission();
                }
            }
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(PERMISSION_CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(PERMISSION_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            return true;
        }
    }

    private void setFragment() {

        CameraFragment cf = new CameraFragment();
        // TempFragment lf = new TempFragment();

        FragmentTransaction transaction =
                getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.frame_camera, cf);
        // transaction.add(R.id.frame_log, lf);

        transaction.commit();
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (shouldShowRequestPermissionRationale(PERMISSION_CAMERA) || shouldShowRequestPermissionRationale(PERMISSION_STORAGE)) {
                Toast.makeText(MainActivity.this, "Camera AND storage permission are required for this demo", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[] {PERMISSION_CAMERA, PERMISSION_STORAGE}, PERMISSIONS_REQUEST);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private class AsyncTaskRunner extends AsyncTask<VisionRequestWrapper, String, VisionResponse> {
        private VisionResponse result;

        public static final String VISION_BASE_URI = "https://vision.googleapis.com";
        public static final String GOOGLE_API_KEY = "AIzaSyDQiKa6VOASACrBABdepX8x_aoYn03CPGU";

        public VisionAPIService getVisionAPIService() {
            return VisionAPIClient.getClient(VISION_BASE_URI).create(VisionAPIService.class);
        }

        @Override
        protected VisionResponse doInBackground(VisionRequestWrapper... data) {
            try {
                ApiUtil.sendPostVisionAPI(data[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(VisionResponse result) {
            Log.i("TAG", "In Post Execute...");
            if (result != null) {
                TextToSpeechAPI.speak(tts, result
                        .getLabelAnnotations().get(0)
                        .getDescription());
            }
        }
    }

}
