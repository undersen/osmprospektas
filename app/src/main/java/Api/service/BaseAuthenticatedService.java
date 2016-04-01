package Api.service;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Bundle;

import Api.ProspektasAPI;
import Api.ServiceGenerator;


/**
 * Created by UnderSen on 26-01-16.
 */
public abstract class BaseAuthenticatedService <ResponseType> {
    private static final String ACCOUNT_TYPE = "com.example.android.osmdroidofflinedemo";
    private Context mcontext;

    public ProspektasAPI getmApiService() {
        return mApiService;
    }

    public void setmApiService(ProspektasAPI mApiService) {
        this.mApiService = mApiService;
    }

    public abstract void loadService(BaseCallback<ResponseType> callback);

    private ProspektasAPI mApiService;
    private String mAuthToken;
    private String mEmail;

    public BaseAuthenticatedService( Context context ) {
        mcontext=context;
        try{

            final AccountManager accountManager = AccountManager.get(mcontext);
            final Account[] accounts = accountManager.getAccountsByType("com.example.android.osmdroidofflinedemo");
            Account account = accounts[0];

            AccountManagerFuture<Bundle> acc = accountManager.getAuthToken(account, ACCOUNT_TYPE, false, null, null);

            mAuthToken = accountManager.peekAuthToken(account, AccountManager.KEY_AUTHTOKEN);
            mEmail = account.name;

            mApiService    = ServiceGenerator.createService(ProspektasAPI.class, mAuthToken, mEmail);
        }catch (ArrayIndexOutOfBoundsException Ex)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }

    public String getmAuthToken() {
        return mAuthToken;
    }

    public void setmAuthToken(String mAuthToken) {
        this.mAuthToken = mAuthToken;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

}
