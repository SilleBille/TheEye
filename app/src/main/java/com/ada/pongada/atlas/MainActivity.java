package com.ada.pongada.atlas;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.ada.pongada.atlas.pojo.VisionFeatureObject;
import com.ada.pongada.atlas.pojo.VisionImageObject;
import com.ada.pongada.atlas.pojo.VisionRequest;
import com.ada.pongada.atlas.pojo.VisionRequestWrapper;
import com.ada.pongada.atlas.util.ApiUtil;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        ApiUtil.sendPostVisionAPI(requests);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
}
