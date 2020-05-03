package rs.elfak.mosis.lab_3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPlacesList extends AppCompatActivity {

    static int NEW_PLACE = 2;
    ArrayList<String> places;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_places_list, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent i=new Intent(MyPlacesList.this,EditMyPlaceActivity.class);
             startActivityForResult(i, NEW_PLACE);
            }
        });

        final ListView myPlacesL = (ListView) findViewById(R.id.my_places_list);
        myPlacesL.setAdapter(new ArrayAdapter<MyPlace>(this, android.R.layout.simple_list_item_1,
                                                                      MyPlacesData.getInstance().getMyPlaces()));

        myPlacesL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Bundle positionBundle = new Bundle();
                                                    positionBundle.putInt("position", position);
                                                    Intent intent = new Intent(MyPlacesList.this, ViewMyPlacesActivity.class);
                                                    intent.putExtras(positionBundle);
                                                    startActivity(intent);

         myPlacesL.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener()
         {
            @Override
             public void onCreateContextMenu(ContextMenu contextMenu,View view, ContextMenu.ContextMenuInfo contextMenuInfo)
            {
                AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) contextMenuInfo;
                MyPlace myPlace=MyPlacesData.getInstance().getPlace(info.position);
                contextMenu.setHeaderTitle(myPlace.getName());
                contextMenu.add(0,1,1,"View place");
                contextMenu.add(0,2,2,"Edit place");

            }


         });
                                             }

        });

    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Bundle positionBundle=new Bundle();
        positionBundle.putInt("position",info.position);
        Intent i=null;
        if(item.getItemId()==1)
        {
            i=new Intent(this,ViewMyPlacesActivity.class);
            i.putExtras(positionBundle);
            startActivity(i);
        }
        if(item.getItemId()==2)
        {
            i=new Intent(this,EditMyPlaceActivity.class);
            i.putExtras(positionBundle);
            startActivityForResult(i,1);
        }
        return super.onContextItemSelected(item);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.show_map_item) {
            Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show();
        }
        else if (id == R.id.new_place_item) {
            Toast.makeText(this, "New Place!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, EditMyPlaceActivity.class);
            startActivityForResult(intent, NEW_PLACE);
        }
        else if (id == R.id.about_item) {
            Toast.makeText(this, "About!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, About.class);
            startActivity(intent);
        }
        else if (id == R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "New Place Added!", Toast.LENGTH_SHORT).show();

            //radi i bez ovoga??
            ListView myPlacesList = (ListView) findViewById(R.id.my_places_list);
            myPlacesList.setAdapter(new ArrayAdapter<MyPlace>(this, android.R.layout.simple_list_item_1,
                    MyPlacesData.getInstance().getMyPlaces()));
        }
    }
}
