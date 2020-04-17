package rs.elfak.mosis.lab_2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPlacesList extends AppCompatActivity {

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        places = new ArrayList<String>();
        places.add("Tvrdjava");
        places.add("Cair");
        places.add("Park Svetog Save");
        places.add("Trg Kralja Milana");

        ListView myPlacesL = (ListView) findViewById(R.id.my_places_list);
        myPlacesL.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, places));


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
        }
        else if (id == R.id.my_places_list_item) {
            Toast.makeText(this, "My Places!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, MyPlacesList.class);
            startActivity(intent);
        }
        else if (id == R.id.about_item) {
            Toast.makeText(this, "About!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
