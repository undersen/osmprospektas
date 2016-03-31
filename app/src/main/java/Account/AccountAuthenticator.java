package Account;

/**
 * Created by fabian on 07-01-16.
 */

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;



import com.example.android.osmdroidofflinedemo.LoginActivity;

public class AccountAuthenticator extends AbstractAccountAuthenticator {
    private String TAG = "AccountAuthenticator";
    private Context context;

    public AccountAuthenticator(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
                             String authTokenType, String[] requiredFeatures,
                             Bundle options) throws NetworkErrorException {
        Log.d(TAG, "> addAccount");
        Log.d(TAG, "> accountType: "+accountType);
        Log.d(TAG, "> authTokenType: "+authTokenType);
        final Intent intent = new Intent(context, LoginActivity.class);

        intent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, accountType);
        intent.putExtra(LoginActivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
//	    intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse arg0,
                                     Account arg1, Bundle arg2) throws NetworkErrorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse arg0, String arg1) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response,
                               Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d(TAG, "> getAuthToken");
        Log.d(TAG, "> authTokenType: "+authTokenType);
        final Bundle result = new Bundle();

        if (!LoginActivity.ACCOUNT_TYPE.equals(authTokenType))
            return result;

        final AccountManager am = AccountManager.get(context);
        String authToken = am.peekAuthToken(account, authTokenType);
        Log.d(TAG, "> peekAuthToken returned - " + authToken);

        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        return result;
    }

    @Override
    public String getAuthTokenLabel(String arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse arg0, Account arg1,
                              String[] arg2) throws NetworkErrorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse arg0,
                                    Account arg1, String arg2, Bundle arg3)
            throws NetworkErrorException {
        // TODO Auto-generated method stub
        return null;
    }

}