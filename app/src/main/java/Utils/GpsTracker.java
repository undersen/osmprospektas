package Utils;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.android.osmdroidofflinedemo.MapActivity;

/**
 * Created by UnderSen on 28-03-16.
 */
public class GpsTracker extends Service implements LocationListener {
    private Context context;
    MapActivity mapActivity;

    public GpsTracker(Context context) {
        this.context = context;
        mapActivity = (MapActivity) context;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

        mapActivity.snackBar("");
    }

    @Override
    public void onProviderEnabled(String provider) {
        mapActivity.snackBar("Gps Enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        mapActivity.snackBar("Gps Disabled");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
