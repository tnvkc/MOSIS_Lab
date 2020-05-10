package rs.elfak.mosis.lab_3;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import rs.elfak.mosis.lab_3.R;

public class EditMyPlaceActivity extends AppCompatActivity implements View.OnClickListener {
    boolean editMode=true;
    int position=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_my_place);

       try
       {
           Intent listintent=getIntent();
           Bundle positionBundle=listintent.getExtras();
           if(positionBundle!=null)
               position=positionBundle.getInt("position");
           else
               editMode=false;
       }
       catch(Exception e)
       {
           editMode=false;
       }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Button finishedButton = findViewById(R.id.edit_my_place_finished_button);
        Button cancelButton = findViewById(R.id.edit_my_place_cancel_button);

        final EditText nameEditText  = (EditText) findViewById(R.id.edit_my_place_name_edit);
        if(!editMode)
        {
            finishedButton.setEnabled(false);
            finishedButton.setText("Add");
        }
        else if(position>=0)
        {
            finishedButton.setText("Save");
            MyPlace place=MyPlacesData.getInstance().getPlace(position);
            nameEditText.setText(place.getName());
            EditText descEditText=(EditText)findViewById(R.id.edit_my_place_desc_edit);
            descEditText.setText(place.getDescription());
        }
        finishedButton.setOnClickListener(this);
        finishedButton.setEnabled(false);
        cancelButton.setOnClickListener(this);

        nameEditText.addTextChangedListener(
                new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            finishedButton.setEnabled(s.length() > 0);//!!!!!!!!!!
                        }
                    }
        );

        Button locationButton = (Button)findViewById(R.id.edit_my_place_location_button);
        locationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_my_place_finished_button: {

                EditText nameEditText = findViewById(R.id.edit_my_place_name_edit);
                String name = nameEditText.getText().toString();

                EditText descEditText = findViewById(R.id.edit_my_place_desc_edit);
                String desc = descEditText.getText().toString();

                EditText latEdit = (EditText) findViewById(R.id.edit_my_place_lat_edit);
                String lat = latEdit.getText().toString();

                EditText lonEdit = findViewById(R.id.edit_my_place_lon_edit);
                String lon = lonEdit.getText().toString();

                if(!editMode)
                {
                    MyPlace place=new MyPlace(name, desc);
                    place.setLatitude(lat);
                    place.setLongitude(lon);
                    MyPlacesData.getInstance().addNewPlace(place);
                }
                else
                {
                    MyPlacesData.getInstance().updatePlace(position,name,desc,lon,lat);
                }

                setResult(Activity.RESULT_OK);
                finish();
                break;
            }
            case R.id.edit_my_place_cancel_button: {
                setResult(Activity.RESULT_CANCELED);
                finish();
                break;
            }
            case R.id.edit_my_place_location_button:
            {
                Intent i = new Intent(this, MyPlacesMapsActivity.class);
                i.putExtra("state", MyPlacesMapsActivity.SELECT_COORDINATE);
                startActivityForResult(i, 1);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try
        {
            if(resultCode == Activity.RESULT_OK)
            {
                String lon = data.getExtras().getString("lon");
                EditText lonText = (EditText) findViewById(R.id.edit_my_place_lon_edit);
                lonText.setText(lon);

                String lat = data.getExtras().getString("lat");
                EditText latText = (EditText) findViewById(R.id.edit_my_place_lat_edit);
                latText.setText(lat);
            }
        }
        catch (Exception e)
        {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_my_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.show_map_item) {
            Toast.makeText(this, "Show Map!", Toast.LENGTH_SHORT).show();
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
        else if (id == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED);
            finish();
        }

        return super.onOptionsItemSelected(item);

    }


}
