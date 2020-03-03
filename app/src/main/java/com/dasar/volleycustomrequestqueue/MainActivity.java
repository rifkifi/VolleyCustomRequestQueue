package com.dasar.volleycustomrequestqueue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.Cache;

import android.net.Network;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

public class MainActivity extends AppCompatActivity {

    private String server_url = "http://www.mocky.io/v2/5de8b4d431000075006b134b";
    Button btnRequest;
    RequestQueue requestQueue;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRequest = (Button) findViewById(R.id.btnRequest);
        DiskBasedCache cache = new DiskBasedCache(getCacheDir(),1024*1024);
        BasicNetwork network = new BasicNetwork(new HurlStack());
        requestQueue = new RequestQueue(cache,network);
        requestQueue.start();

        btnRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){

                StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i(TAG,"Error :" + error.toString());
                    }
                });

                requestQueue.add(stringRequest);
            }
        });
    }

}
