package com.yellowcode.tournote.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.yellowcode.tournote.R;
import com.yellowcode.tournote.adapter.LoaispAdapter;
import com.yellowcode.tournote.model.Loaisp;
import com.yellowcode.tournote.ultil.Server;
import com.yellowcode.tournote.ultil.checkConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity<JsonArrayRequest> extends AppCompatActivity {
    RecyclerView recyclerViewmanhinhchinh;
    ViewFlipper viewFlipper;
    int[] arrayHinh={R.drawable.anhnen1,R.drawable.anhnen2,R.drawable.anhnen3,
            R.drawable.anhnen4};
    NavigationView navigationView;
    ListView listViewManHinhChinh;

    ArrayList<Loaisp> mangloaisp;
    LoaispAdapter loaispAdapter;
    int id=0;
    String tenloaisp= "";
    String hinhanhloaisp ="";

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        Anhxa();
        getDuLieuLoaiSanPham();

        for (int i  = 0 ; i< arrayHinh.length;i++) {
            ImageView imageView= new ImageView(this);
            imageView.setImageResource(arrayHinh[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);


        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slideout);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setInAnimation(animation_slide_out);
    }

    private void getDuLieuLoaiSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Server.duongDanLoaiSanPham, new Response.Listener<JSONArray>(){

            @Override

            public void onResponse(JSONArray response) {
                if(response != null){

                    for(int i = 0;i < response.length();i++){
                        try {
                            JSONObject jsonObject =response.getJSONObject(i);
                            id =jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("tenloaisp");
                            hinhanhloaisp =jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add( 3,new Loaisp(0,"Liên hệ","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpCeVuVWGdIbeB88vj6moAw0vSEt5dPuRFN0FZqy5UdS5wN1wp"));
                    mangloaisp.add(4,new Loaisp(0,"Thông tin","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSynBh4ywTCWrkfmGUUKvbhd7ahU-tm2iyTQPnLQixfOr7q-9QzKQ"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkConnection.ShowToast_Short(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add( jsonArrayRequest);
    }


    private void Anhxa() {
        recyclerViewmanhinhchinh= (RecyclerView) findViewById(R.id.recyclerview);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewlipper);
        navigationView = findViewById(R.id.navigationView);
        listViewManHinhChinh = findViewById(R.id.listViewManHinhChinh);

        mangloaisp =new ArrayList<>();
        loaispAdapter=new LoaispAdapter(mangloaisp,getApplicationContext());
        listViewManHinhChinh.setAdapter(loaispAdapter);


    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.about:
                Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.help:
                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
