package com.example.android.osmdroidofflinedemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Api.model.ProspektumPostModel;
import Api.service.BaseCallback;
import Api.service.ProspektumPostService;
import retrofit.Response;
import retrofit.Retrofit;

public class AddProspektumActivity extends AppCompatActivity {

    private Context context;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private TextInputLayout textInputLayout;
    private Button button;
    private EditText mNameProspektum;
    private int employeeId;
    private int companyId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prospektum);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("New Prospektum");
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            employeeId = extras.getInt("employee_id");
            companyId = extras.getInt("company_id");
        } else {
            Toast.makeText(getApplicationContext(), "no se almacenaron las variables",
                    Toast.LENGTH_LONG).show();
        }

        context= getApplicationContext();
        textInputLayout = (TextInputLayout) findViewById(R.id.background_layout);
        mNameProspektum = (EditText) findViewById(R.id.edit_text_name_prospektum);
        mNameProspektum.setHintTextColor(getResources().getColor(R.color.white));
        button = (Button) findViewById(R.id.button_add_prospektum);
        EditText mFoundMinerals  = (EditText) findViewById(R.id.edit_text_found_minerals);
        mFoundMinerals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"separated by coma",Toast.LENGTH_SHORT).show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText mRegion = (EditText) findViewById(R.id.edit_text_region);
                EditText mCity = (EditText) findViewById(R.id.edit_text_ciudad);
                EditText mFoundMinerals  = (EditText) findViewById(R.id.edit_text_found_minerals);

                if(mNameProspektum.getText().equals(""))
                {
                    mNameProspektum.setError("Ingrese un nombre para el prospekto");
                }

                if(mRegion.getText().equals(""))
                {
                    mNameProspektum.setError("Ingrese una region para el prospekto");
                }

                if(mCity.getText().equals(""))
                {
                    mNameProspektum.setError("Ingrese una ciudad para el prospekto");
                }

                ProspektumPostModel prospektumPostModel = new ProspektumPostModel();
                prospektumPostModel.setName(mNameProspektum.getText().toString());
                prospektumPostModel.setCity(mCity.getText().toString());
                prospektumPostModel.setFinded_minerals(mFoundMinerals.getText().toString());

                ProspektumPostService prospektumPostService = new ProspektumPostService(context,prospektumPostModel);
                prospektumPostService.loadService(new BaseCallback<ProspektumPostModel>()
                {
                    @Override
                    public void onResponse(Response<ProspektumPostModel> response, Retrofit retrofit) {
                        super.onResponse(response, retrofit);
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        super.onFailure(t);
                    }
                });
            }
        });

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickTakeAPicture(view);

            }
        });
    }


    public void onClickTakeAPicture(View view)
    {
        Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photo, REQUEST_IMAGE_CAPTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {

                Bitmap object = (Bitmap) data.getExtras().get("data");
                Drawable d = new BitmapDrawable(getResources(), object);
                ColorFilter colorFilter = new ColorFilter();

                d.setFilterBitmap(true);
                textInputLayout.setBackground(d);


            }
        }
    }

}
