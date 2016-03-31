package Api.service;

import android.support.annotation.CallSuper;
import android.util.Log;


import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by fabian on 07-02-16.
 */
public class BaseCallback<T> implements Callback<T> {
    private static final String TAG = BaseCallback.class.getSimpleName();
    private boolean responseOK  = false;

    @CallSuper
    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
//
    }

    @CallSuper
    @Override
    public void onFailure(Throwable t) {
        Log.e(TAG, "==========Connection failure==========");
        Log.e(TAG, "Exception : ");
        for( StackTraceElement elem : t.getStackTrace() ){
            Log.e(TAG, ">"+elem.toString());
        }
        Log.e(TAG, t.getMessage());

    }

    public boolean isResponseOK() {
        return responseOK;
    }
}
