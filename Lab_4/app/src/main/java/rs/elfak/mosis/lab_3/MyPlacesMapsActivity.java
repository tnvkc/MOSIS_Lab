package rs.elfak.mosis.lab_3;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.view.ViewManager;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import rs.elfak.mosis.lab_3.R;

public class MyPlacesMapsActivity extends AppCompatActivity {

    MapView map=null;
    IMapController mapController=null;
    static int NEW_PLACE = 1;
    static final int PERMISSION_ACCESS_FINE_LOCATION=1;
    MyLocationNewOverlay myLocationOverlay;//DK

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places_maps);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MyPlacesMapsActivity.this,EditMyPlaceActivity.class);
                startActivityForResult(i,NEW_PLACE);
            }
        });
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
        else
            setMyLocationOverlay();

        mapController=map.getController();
        if(mapController!=null)
        {
            mapController.setZoom(15.0);
            GeoPoint startPoint=new GeoPoint(43.3209, 21.8958);
            mapController.setCenter(startPoint);
        }
    }

    private void setMyLocationOverlay()
    {
        myLocationOverlay=new MyLocationNewOverlay(new GpsMyLocationProvider(this),map);
        myLocationOverlay.enableMyLocation();
        map.getOverlays().add(this.myLocationOverlay);
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
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    setMyLocationOverlay();
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

}
