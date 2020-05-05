package rs.elfak.mosis.lab_3;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;

import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;

import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import rs.elfak.mosis.lab_3.R;

public class MyPlacesMapsActivity extends AppCompatActivity {

    MapView map=null;
    IMapController mapController=null;
    static int NEW_PLACE = 1;
    static final int PERMISSION_ACCESS_FINE_LOCATION=1;
    MyLocationNewOverlay myLocationOverlay;//DK

    public static final int SHOW_MAP = 0;
    public static final int CENTER_PLACE_ON_MAP = 1;
    public static final int SELECT_COORDINATE = 2;

    private int state = 0;
    private boolean selCoorsEnabled = false;
    private GeoPoint placeLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            Intent mapIntent = getIntent();
            Bundle mapBundle = mapIntent .getExtras();

            if(mapBundle != null)
            {
                state = mapBundle.getInt("state");
                if(state == CENTER_PLACE_ON_MAP)
                {
                    String placeLat = mapBundle.getString("lat");
                    String placeLon = mapBundle.getString("lon");
                    placeLoc = new GeoPoint(Double.parseDouble(placeLat), Double.parseDouble(placeLon));
                }
            }
        }catch (Exception e)
        {

        }


        setContentView(R.layout.activity_my_places_maps);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);


        if(state != SELECT_COORDINATE)
        {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(MyPlacesMapsActivity.this,EditMyPlaceActivity.class);
                    startActivityForResult(i,NEW_PLACE);
                }
            });
        }
        else
        {
            ViewGroup layout = (ViewGroup) fab.getParent();
            if(null!= layout)
            {
                layout.removeView(fab);
            }
        }





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Context cntx=getApplicationContext();
        Configuration.getInstance().load(cntx, PreferenceManager.getDefaultSharedPreferences(cntx));
        map=(MapView)findViewById(R.id.map);
        map.setMultiTouchControls(true);
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},PERMISSION_ACCESS_FINE_LOCATION);
        }
        else {
           setupMap();
        }


    }

    private void setCenterPlaceOnMap()
    {
        mapController=map.getController();
        if(mapController!=null)
        {
            mapController.setZoom(15.0);
           // GeoPoint startPoint=new GeoPoint(43.3209, 21.8958);
            mapController.setCenter(placeLoc);
        }
    }

    private void setupMap()
    {
        switch (state)
        {
            case SHOW_MAP:
                setMyLocationOverlay();
                break;
            case SELECT_COORDINATE:
                mapController=map.getController();
                if(mapController!=null)
                {
                    mapController.setZoom(15.0);

                    mapController.setCenter(new GeoPoint(43.3209,21.895 ));
                }
                setOnMapClickOverlay();
            case CENTER_PLACE_ON_MAP:
            default:
                setCenterPlaceOnMap();
                break;
        }
    }



    private void setMyLocationOverlay()
    {
        myLocationOverlay=new MyLocationNewOverlay(new GpsMyLocationProvider(this),map);
        myLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.myLocationOverlay);

        mapController=map.getController();
        if(mapController!=null)
        {
            mapController.setZoom(15.0);

            myLocationOverlay.enableFollowLocation();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_my_places_maps,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        int id= menuItem.getItemId();
        if(id==R.id.new_place_item)
        {
            Intent i=new Intent(this,EditMyPlaceActivity.class);
            startActivityForResult(i,1);
        }
        else if(id==R.id.about_item)
        {
            Intent i=new Intent(this,About.class);
            startActivity(i);
        }
        else if(id==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(menuItem );
    }

    @SuppressLint("MissingPermission")
    @Override
    public  void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_ACCESS_FINE_LOCATION:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    setupMap();
                }
                return;
            }
        }
    }

    public void onResume()
    {
        super.onResume();
        map.onResume();
    }

    public void onPause()
    {
        super.onPause();
        map.onPause();
    }

    private void setOnMapClickOverlay()
    {
        MapEventsReceiver mReceive = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                String lon = Double.toString(p.getLongitude());
                String lat = Double.toString(p.getLatitude());

                Intent locationIntent = new Intent();
                locationIntent.putExtra("lon", lon);
                locationIntent.putExtra("lat", lat);
                setResult(Activity.RESULT_OK, locationIntent);
                finish();

                return false;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };

        MapEventsOverlay overlayEvents = new MapEventsOverlay(mReceive);
        map.getOverlays().add(overlayEvents);
    }
}
