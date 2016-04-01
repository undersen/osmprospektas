package com.example.android.osmdroidofflinedemo;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import Api.model.LoginPost;
import Api.model.ProspektasUserModel;
import Api.service.BaseCallback;
import Api.service.SessionCreate;
import retrofit.Response;
import retrofit.Retrofit;




/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AccountAuthenticatorActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */


    public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";
    public static final String PARAM_AUTH_USER_ID = "authUserID";
    public static final String PARAM_AUTH_USER_NAME = "authUserName";
    public static final String ACCOUNT_TYPE = BuildConfig.APPLICATION_ID;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private AccountManager mAccountManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mAccountManager     = AccountManager.get(getBaseContext());

    }

//    private void populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return;
//        }
//
//        getLoaderManager().initLoader(0, null, this);
//    }

//    private boolean mayRequestContacts() {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true;
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok, new View.OnClickListener() {
//                        @Override
//                        @TargetApi(Build.VERSION_CODES.M)
//                        public void onClick(View v) {
//                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//                        }
//                    });
//        } else {
//            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
//        }
//        return false;
//    }

    /**
     * Callback received when a permissions request has been completed.
     */
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete();
//            }
//        }
//    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            LoginPost loginPost = new LoginPost();
            loginPost.setEmail(mEmail);
            loginPost.setPassword(mPassword);

            SessionCreate sessionCreate = new SessionCreate(loginPost);
            sessionCreate.loadService(new BaseCallback<ProspektasUserModel>()
            {
                @Override
                public void onResponse(Response<ProspektasUserModel> response, Retrofit retrofit) {
                    super.onResponse(response, retrofit);
                    if(!isResponseOK())
                    {

                    }
                    try {
                        Log.d(TAG, "#Api call success : " + response.errorBody());
                        Log.d(TAG, "#Api call success : " + response.code());
                        Log.d(TAG, "#Api call success : " + response.message());


                        String authToken = response.body().getAuthentication_token();

                        Account prospektasAccount = new Account(mEmail, "com.example.android.osmdroidofflinedemo");
                        mAccountManager.addAccountExplicitly(prospektasAccount, authToken, null);
                        mAccountManager.setAuthToken(prospektasAccount, AccountManager.KEY_AUTHTOKEN, authToken);


                        Log.d(TAG, "Account created");

                        final String accountType = getIntent().getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);
                        String mAuthTokenType = getIntent().getStringExtra(PARAM_AUTHTOKEN_TYPE);

                        Bundle data = new Bundle();

                        data.putString(AccountManager.KEY_ACCOUNT_NAME, response.body().getFirst_name() + " " + response.body().getLast_name());
                        data.putString(AccountManager.KEY_ACCOUNT_TYPE, accountType);
                        data.putString(PARAM_AUTHTOKEN_TYPE, mAuthTokenType);
                        data.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                        data.putString(PARAM_AUTH_USER_ID, "" + response.body().getId());
                        data.putString(PARAM_AUTH_USER_NAME, response.body().getEmail());


                        Log.d(TAG, "Calling account auth");
                        setAccountAuthenticatorResult(data);


                        Log.d(TAG, "Finishing login");
                        finish();

                        Log.d(TAG, "Finishing login, account created");
                        //Go to main activity, we are ok with this account now

                        Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                        startActivity(intent);

                    }catch (Exception ex )
                    {
                        Log.e(TAG,ex.getMessage(),ex.getCause());
                    }

                }

                @Override
                public void onFailure(Throwable t) {
                    super.onFailure(t);

                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.login_activity);

                    Log.e(TAG, t.getMessage().toString(), t.getCause());
                    Snackbar.make(linearLayout,"Existen problemas para realizar conexion",Snackbar.LENGTH_LONG);


                }
            });





            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
           // showProgress(false);


        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            //showProgress(false);
        }
    }
}

