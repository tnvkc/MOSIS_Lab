package rs.elfak.mosis.lab_3;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MyPlace {

    public String name;
    public String description;
    public String latitude;
    public String longitude;
    @Exclude
    public String key;
    public MyPlace(){}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    /*public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }*/

    public MyPlace(String name, String desc){
        this.name = name;
        this.description = desc;
    }

    public MyPlace(String name){
        this(name, "");
    }


    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
