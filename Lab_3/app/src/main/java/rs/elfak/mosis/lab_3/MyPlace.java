package rs.elfak.mosis.lab_3;

import androidx.annotation.NonNull;

public class MyPlace {

    private String name;
    private String description;

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
