package com.example.android.osmdroidofflinedemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.kml.KmlDocument;
import org.osmdroid.bonuspack.overlays.FolderOverlay;
import org.osmdroid.bonuspack.overlays.GroundOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polygon;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import Api.model.ProspektumKml;
import Api.model.ProspektumKmlFile;
import RecyclerView.Adapters.KmlViewAdapter;
import RecyclerView.Adapters.ProspektumAdapter;

public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MapEventsReceiver {


    public static final GeoPoint Chile = new GeoPoint(-33.516667, -60.383333);
    private static final int YOUR_RESULT_CODE = 2;
    private MapView mapView;
    private CompassOverlay compassOverlay;
    private static MyLocationNewOverlay locationOverlay;
    private static int ACTION_GET_CONTENT = 1;
    private ProgressBar progressBar;
    private static String TAG = MapActivity.class.getSimpleName();
    private String downloadedFile;
    private CoordinatorLayout coordinatorLayout = null;
    private List<GeoPoint> geoPoints = new ArrayList<>();
    private Context context;
    private Menu menu;
    private String mode = "navigation";
    private List<Marker> markers = new ArrayList<>();
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    NavigationView navigationView;
    private List<File> files;
    private List<ProspektumKmlFile> prospektumKmlFiles = new ArrayList<>() ;
    RecyclerView recyclerViewFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadFeatures();
        loadMenuItems();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            long prospektas_id = extras.getLong("id_prospektum");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Loading Gps providers . . . ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, this);
        mapView.getOverlays().add(0, mapEventsOverlay);


        //TODO : make it Async
        getKmlFiles();

    }

    private void LoadKmlAlertDialog(View view) {
    }


    private List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.getName().endsWith(".kml") || file.getName().endsWith(".kmz")) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }

    private void loadFeatures() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        context = getApplicationContext();
        mapView = (MapView) findViewById(R.id.mapview);
        progressBar = (ProgressBar) findViewById(R.id.progress_file);
        compassOverlay = new CompassOverlay(context, new InternalCompassOrientationProvider(context), mapView);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.app_bar_principal);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
    }

    private void loadMenuItems() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadMap();
    }

    private void loadMap() {
        mapView.setClickable(true);
        mapView.setBuiltInZoomControls(true);
        mapView.setMultiTouchControls(true);
        mapView.setUseDataConnection(true);
        mapView.setTileSource(TileSourceFactory.MAPQUESTOSM);

        IMapController mapViewController = mapView.getController();
        mapViewController.setZoom(4);
        mapViewController.setCenter(Chile);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.action_compass) {

            Intent intent = new Intent(context, ProspektumActivity.class);
            startActivity(intent);
//            if(isEnableCompass)
//            {
//                compassOverlay.disableCompass();
//                isEnableCompass=false;
//                Toast.makeText(getApplicationContext(),"Compass is disabled",Toast.LENGTH_SHORT).show();
//            }else {
//                compassOverlay.enableCompass();
//                isEnableCompass=true;
//                Toast.makeText(getApplicationContext(),"Compass is enabled",Toast.LENGTH_SHORT).show();
//            }
//
//            mapView.getOverlays().add(compassOverlay);
//            return true;

//            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//            startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_polygon) {
            mode = "polygon";
            verifyPolygon();

        } else if (id == R.id.nav_add_prospektum_image) {


        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
            View view = (View) LayoutInflater.from(MapActivity.this).inflate(R.layout.dialog_geo_form, null);
            final EditText latitude = (EditText) view.findViewById(R.id.text_lat);
            final EditText longitude = (EditText) view.findViewById(R.id.text_long);


            builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GeoPoint geoPoint = new GeoPoint(Double.parseDouble(latitude.getText().toString()), Double.parseDouble(longitude.getText().toString()));
                    addMarker(geoPoint);
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    latitude.setText("");
                    longitude.setText("");
                    dialog.dismiss();
                }
            });

            builder.setView(view);
            builder.setCancelable(true);
            builder.setTitle("Add marker from form");
            final Dialog dialog = builder.create();
            dialog.show();

        } else if (id == R.id.nav_overlay) {
            mode = "groundOverlay";
            checkGroundOverlayImage();

        } else if (id == R.id.nav_show_kml) {

            View view = LayoutInflater.from(MapActivity.this).inflate(R.layout.dialog_all_kml, null);
            recyclerViewFiles = (RecyclerView) view.findViewById(R.id.recycler_view_all_kml_files);
            recyclerViewFiles.setLayoutManager(new LinearLayoutManager(this));
            LoadKmlAlertDialog(view);
            KmlViewAdapter adapter = new KmlViewAdapter(prospektumKmlFiles, MapActivity.this);
            recyclerViewFiles.setAdapter(adapter);

            final AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
            builder.setView(view);
            builder.setCancelable(true);

            final Dialog dialog = builder.create();
            dialog.show();
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

        } else if (id == R.id.nav_kml_file) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/kmz");
            startActivityForResult(intent, YOUR_RESULT_CODE);

        } else if (id == R.id.nav_kml_url) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
            View view = (View) LayoutInflater.from(MapActivity.this).inflate(R.layout.dialog_kml_url, null);

            final EditText url = (EditText) view.findViewById(R.id.text_url);


            builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    String url = "https://s3-us-west-1.amazonaws.com/prospektas/sernageomin_kmls/limite_comunal_aysen.kmz";
                    new DownloadFileAsync().execute(url);

                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    url.setText("");
                    dialog.dismiss();
                }
            });

            builder.setView(view);
            builder.setCancelable(true);
            builder.setTitle("Load Kml From URL");
            final Dialog dialog = builder.create();
            dialog.show();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void checkGroundOverlayImage() {

        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Ingrese 2 puntos", Snackbar.LENGTH_LONG);

        if (geoPoints.size() == 1) {
            snackbar.setText("Ingrese 1 punto");

        } else if (geoPoints.size() == 2) {
            snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
            snackbar.setText("Ground Overlay ready ");
            snackbar.setAction("Load Image", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addGroundOverlayImage();
                }
            });
        }
        snackbar.show();
    }

    private void verifyPolygon() {

        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Mode on: Polygons", Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Finish", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (geoPoints.size() == 1 || geoPoints.size() >= 3) {
                    if (geoPoints.size() == 1) {
                        addSingleMarker();
                    } else {
                        geoPoints.add(geoPoints.get(0));
                        addPolygon();
                    }
                } else {
                    Toast toast = Toast.makeText(context, "Debe existir 1 marker(punto)  o minimo 3 marker(polygono)", Toast.LENGTH_SHORT);
                    TextView textView = (TextView) toast.getView().findViewById(android.R.id.message);
                    if (textView != null) textView.setGravity(Gravity.CENTER);
                    toast.show();
                    verifyPolygon();
                }
            }
        }).show();
    }

    private void addSingleMarker() {

    }

    private void addPolygon() {
        int color = R.color.circle_green;
        Polygon polygon = new Polygon(context);
        polygon.setPoints(geoPoints);
        polygon.setStrokeWidth((float) 0.5);
        polygon.setFillColor(color);
        mapView.getOverlays().add(polygon);
        mapView.invalidate();
        cleanGeoPoints();
    }

    private void cleanGeoPoints() {
        geoPoints.clear();
    }


    private void addGroundOverlayImage() {
        int color = R.color.blue_alpha;
        Polygon polygon = new Polygon(context);
        polygon.setPoints(geoPoints);
        polygon.setStrokeWidth((float) 0.5);
        polygon.setFillColor(color);
        mapView.getOverlays().add(polygon);
        mapView.invalidate();

        //http://www.programcreek.com/java-api-examples/index.php?api=org.osmdroid.bonuspack.overlays.GroundOverlay

        GroundOverlay groundOverlay = new GroundOverlay(context);
        GeoPoint pNW = geoPoints.get(0);
        GeoPoint pSE = geoPoints.get(1);
        groundOverlay.setPosition(GeoPoint.fromCenterBetween(pNW, pSE));
        GeoPoint pNE = new GeoPoint(pNW.getLatitude(), pSE.getLongitude());
        int width = pNE.distanceTo(pNW);
        GeoPoint pSW = new GeoPoint(pSE.getLatitude(), pNW.getLongitude());
        int height = pSW.distanceTo(pNW);
        groundOverlay.setDimensions((float) width, (float) height);

        //TODO get image
        //Drawable d = new BitmapDrawable(getResources(), bitmap);

        Drawable drawable = getResources().getDrawable(R.drawable.test);

        groundOverlay.setImage(drawable);
        mapView.getOverlays().add(groundOverlay);
        removeAllMarkers();
        mapView.invalidate();

    }


    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            int count;

            try {

                URL url = new URL(params[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                String[] fileName = params[0].split("/");
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/Download/" + fileName[fileName.length - 1]);

                downloadedFile = Environment.getExternalStorageDirectory() + "/Download/" + fileName[fileName.length - 1];

                byte data[] = new byte[1024];
                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

                loadDownloadedFile(downloadedFile);
            } catch (Exception e) {

            }
            return null;
        }

        protected void onProgressUpdate(String... progress) {
            progressBar.setProgress(Integer.parseInt(progress[0]));
            System.out.println(Integer.parseInt(progress[0]));

        }

        @Override
        protected void onPostExecute(String unused) {
//
//
//
//            Snackbar snackbar = Snackbar.make(coordinatorLayout, "File downloaded", Snackbar.LENGTH_INDEFINITE);
//            snackbar.show();
//            snackbar.setAction("load kml", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }
            }, 2000);
        }


        public void loadDownloadedFile(String pathFile) {
            String[] extension = pathFile.split("\\.");
            FolderOverlay overlay = null;

            if ((extension[extension.length - 1].equals("kmz") || (extension[extension.length - 1].equals("KMZ")))) {
                File file = new File(pathFile);
                KmlDocument kmlDocument = new KmlDocument();
                kmlDocument.parseKMZFile(file);
                overlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(mapView, null, null, kmlDocument);

            } else if ((extension[extension.length - 1].equals("kml") || (extension[extension.length - 1].equals("KML")))) {
                File file = new File(pathFile);
                KmlDocument kmlDocument = new KmlDocument();
                kmlDocument.parseKMLFile(file);
                overlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(mapView, null, null, kmlDocument);

            } else {
                Snackbar.make(coordinatorLayout, "El archivo contiene problemas", Snackbar.LENGTH_LONG).show();
                ;
            }

            if (overlay == null) {
                Snackbar.make(coordinatorLayout, "El archivo contiene problemas", Snackbar.LENGTH_LONG).show();
                ;
            } else {
                mapView.getOverlays().add(overlay);
                mapView.invalidate();
            }


        }
    }

    @Override
    public boolean singleTapConfirmedHelper(GeoPoint p) {
        addMarker(p);
        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
    }


    public void addMarker(GeoPoint geoPoint) {
        Marker marker = new Marker(mapView);
        switch (mode) {
            case "navigation":
                break;
            case "polygon":

                marker.setPosition(geoPoint);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setIcon(getResources().getDrawable(R.drawable.ic_add_location_white_24dp));
                markers.add(marker);
                mapView.getOverlays().add(marker);
                mapView.invalidate();
                geoPoints.add(geoPoint);
                break;
            case "groundOverlay":

                marker.setPosition(geoPoint);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setIcon(getResources().getDrawable(R.drawable.ic_add_location_white_24dp));
                markers.add(marker);
                mapView.getOverlays().add(marker);
                mapView.invalidate();
                geoPoints.add(geoPoint);
                checkGroundOverlayImage();
                break;

        }
    }

    public void snackBar(String message) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    public void removeAllMarkers() {
        for (int i = 0; i < markers.size(); i++) {
            mapView.getOverlays().remove(markers);
            mapView.getOverlays().removeAll(markers);

        }
        cleanGeoPoints();
        mapView.invalidate();
    }

    public void loadKmlFromAdapter() {


        List<FolderOverlay> folderOverlayList = new ArrayList<>();

        for(int i =0;i<=prospektumKmlFiles.size()-1;i++)
        {
            KmlDocument kmlDocument = new KmlDocument();
            if (prospektumKmlFiles.get(i).isLoaded())
            {

                File  file = new File(prospektumKmlFiles.get(i).getPath());

                if (prospektumKmlFiles.get(i).getName().endsWith(".kml")) {
                    kmlDocument.parseKMLFile(file);
                } else {
                    kmlDocument.parseKMZFile(file);
            }

                FolderOverlay folderOverlay = new FolderOverlay(context);
                folderOverlay = (FolderOverlay) kmlDocument.mKmlRoot.buildOverlay(mapView,null,null,kmlDocument);
                folderOverlayList.add(folderOverlay);
            }

            mapView.getOverlays().addAll(folderOverlayList);
            mapView.invalidate();
        }
    }


    private void getKmlFiles() {
        files = getListFiles(new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS))));

        for(int i =0;i<=files.size()-1;i++)
        {

            String [] extension =files.get(i).getName().split("\\.");
            ProspektumKmlFile prospektumKmlFile = new ProspektumKmlFile();
            prospektumKmlFile.setName(files.get(i).getName());
            prospektumKmlFile.setPath(files.get(i).getAbsolutePath());
            prospektumKmlFile.setExtension(extension[1]);
            prospektumKmlFile.setIsLoaded(false);

            prospektumKmlFiles.add(prospektumKmlFile);
        }



    }



}
