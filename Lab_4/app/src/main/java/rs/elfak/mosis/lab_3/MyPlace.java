package rs.elfak.mosis.lab_3;

import androidx.annotation.NonNull;

public class MyPlace {

    private String name;
    private String description;
    private String latitude;
    private String longitude;
    private long ID;

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

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public MyPlace(String name, String desc){
        this.name = name;
        this.description = desc;
    }

    public MyPlace(String name){
        this(name, "");
    }

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

    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
