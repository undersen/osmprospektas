package Account;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by fabian on 07-01-16.
 */
public class AccountAuthenticatorService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        AccountAuthenticator authenticator = new AccountAuthenticator(this);
        return authenticator.getIBinder();
    }

}