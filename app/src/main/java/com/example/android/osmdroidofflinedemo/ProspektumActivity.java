package com.example.android.osmdroidofflinedemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import Api.model.ProspektumGetModel;
import Api.model.ProspektumList;
import Api.service.BaseCallback;
import Api.service.ProspektumListService;
import RecyclerView.Adapters.ProspektumAdapter;
import retrofit.Response;
import retrofit.Retrofit;

public class ProspektumActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerViewProspektums;
    private static String TAG = ProspektumActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prospektum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=getApplicationContext();



        recyclerViewProspektums  = (RecyclerView) findViewById(R.id.recyclerView_prospektum);
        recyclerViewProspektums.setLayoutManager(new LinearLayoutManager(this));

        ProspektumListService prospektumListService = new ProspektumListService(context,1);
        prospektumListService.loadService(new BaseCallback<ProspektumList>() {

            @Override
            public void onResponse(Response<ProspektumList> response, Retrofit retrofit) {
                super.onResponse(response, retrofit);

                List<ProspektumGetModel> prospektumModels = response.body().getProspektas();
                Log.d(TAG,"si cargo los prospektos");

                //TODO:implement Realm and date to update the prospektums
                ProspektumAdapter adapter = new ProspektumAdapter(prospektumModels, ProspektumActivity.this);
                recyclerViewProspektums.setAdapter(adapter);
            }

            @Override
            public void onFailure(Throwable t) {
                super.onFailure(t);
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                //TODO:implement Realm
            }
        });




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddProspektumActivity.class);
                intent.putExtra("employee_id", 1);
                intent.putExtra("company_id", 1);
                startActivity(intent);
            }
        });


    }



    public void initMap(int prospektum_id)
    {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("id_prospektum", prospektum_id);
        this.startActivity(intent);
    }

}
