package com.example.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.List;

@Entity(tableName = "userData",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "uid",
                childColumns = "uID",
                onDelete = ForeignKey.CASCADE
        )
)
@TypeConverters(CustomConverters.class)
public class userData {
    @PrimaryKey
    private int uID;

    private List<String> pinnedLocations;
    private String recentLocation;

    //Constructor
    public userData(int uID, List<String> pinnedLocations, String recentLocation) {
        this.uID = uID;
        this.pinnedLocations = pinnedLocations;
        this.recentLocation = recentLocation;
    }

    //Getters and setters
    public int getUID() {
        return uID;
    }

    public void setUID(int uID) {
        this.uID = uID;
    }

    public List<String> getPinnedLocations() {
        return pinnedLocations;
    }

    public void setPinnedLocations(List<String> pinnedLocations) {
        this.pinnedLocations = pinnedLocations;
    }

    public String getRecentLocation() {
        return recentLocation;
    }

    public void setRecentLocation(String recentLocation) {
        this.recentLocation = recentLocation;
    }
}

