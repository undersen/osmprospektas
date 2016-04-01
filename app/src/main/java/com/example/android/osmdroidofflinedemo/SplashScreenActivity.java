package com.example.android.osmdroidofflinedemo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by UnderSen on 28-03-16.
 */
public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                task.execute();
            }
        }, 2000);
    }

    private static final String TAG = SplashScreenActivity.class.getSimpleName();

    AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {


        @Override
        protected Void doInBackground(Void... params) {
            try{

                final AccountManager accountManager = AccountManager.get(getApplicationContext());
                final Account[] accounts = accountManager.getAccountsByType("com.example.android.osmdroidofflinedemo");
                Account account = accounts[0];
                Intent intent = new Intent(getApplicationContext(), ProspektumActivity.class);
                startActivity(intent);

            }catch (java.lang.ArrayIndexOutOfBoundsException Ex)
            {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
            return null;
        }
    };
}
