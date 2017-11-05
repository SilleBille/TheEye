package com.ada.pongada.atlas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ada.pongada.atlas.pojo.VisionFeatureObject;
import com.ada.pongada.atlas.pojo.VisionImageObject;
import com.ada.pongada.atlas.pojo.VisionRequest;
import com.ada.pongada.atlas.pojo.VisionRequestWrapper;
import com.ada.pongada.atlas.tts.TextToSpeechAPI;
import com.ada.pongada.atlas.util.ApiUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    private static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private static final String PERMISSION_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private static final int PERMISSIONS_REQUEST = 32;
    private static int speakingMode;
    private static SharedPreferences sharedpreferences;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private int exploreMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.speakingMode = 1;
        this.sharedpreferences = getSharedPreferences("Retina", Context.MODE_PRIVATE);
        Set<String> shoppingList = new TreeSet<String>();
        Set<String> actionList = new TreeSet<String>();
        this.exploreMode = 0;
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putStringSet("sl",shoppingList);
        editor.putStringSet("al",actionList);
        editor.putInt("em",this.exploreMode);
        editor.commit();

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //TextToSpeechAPI.speak(tts, "In the GitHub project I have included more examples for you to experiment with and build your own Android assistant.");



        if (hasPermission()) {
            if (null == savedInstanceState) {
                setFragment();
            }
        } else {
            requestPermission();
        }



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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)){
            this.askSpeechInput();
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)){
            SharedPreferences.Editor editor = sharedpreferences.edit();
            if(this.exploreMode == 0) this.exploreMode = 1;
            else if(this.exploreMode == 1) this.exploreMode = 0;
            editor.putInt("em", this.exploreMode);
            editor.commit();
        }
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //voiceInput.setText(result.get(0));
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    Set<String> actionList = sharedpreferences.getStringSet("al", new TreeSet<String>());
                    Set<String> shoppingList = sharedpreferences.getStringSet("sl", new TreeSet<String>());
                    String command[] = result.get(0).split(" ");
                    if(command[0].equalsIgnoreCase("add") && command[1].equalsIgnoreCase("item") && command.length == 3){
                        //shoppingList.add(result.get(0));
                        shoppingList.add(command[2]);
                        Log.e("Shopping list", shoppingList.toString());
                        editor.putStringSet("sl",shoppingList);
                    } else if(command[0].equalsIgnoreCase("view") &&
                            (command[1].equalsIgnoreCase("cart") || command[1].equalsIgnoreCase("list"))) {
                        actionList.add("view");
                        editor.putStringSet("al",actionList);
                    } else if(command[0].equalsIgnoreCase("remove") && command[1].equalsIgnoreCase("item") && command.length == 3) {
                        Iterator iterator = actionList.iterator();
                        while(iterator.hasNext()) {
                            String currentElement = (String)iterator.next();
                            if(currentElement.equalsIgnoreCase(command[2])) {
                                shoppingList.remove(currentElement);
                                editor.putStringSet("sl",shoppingList);
                                break;
                            }
                        }
                        Log.e("Removal", shoppingList.toString());
                    } else {
                        editor.putStringSet("al", new TreeSet<String>());
                        editor.commit();
                    }
                }
                break;
            }

        }
    }

    private void askSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Hi speak something");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (Exception a) {
            a.printStackTrace();
        }
    }

}
