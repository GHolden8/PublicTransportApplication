package com.example.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;



import com.example.entities.userData;

import java.util.List;

@Dao
public interface userDataDao {
    @Insert
    void insert(userData userData);

    @Query("SELECT * FROM userData")
    List<userData> getAllUserData();

    @Update
    void update(userData userData);

    @Query("SELECT * FROM userData WHERE uID = :uID")
    userData getUserDataByUID(int uID);

    @Query("SELECT * FROM userData WHERE pinnedLocations LIKE '%' || :pinnedLocation || '%'")
    userData getPinnedLocation(String pinnedLocation);



    @Delete
    void deleteUserLocation(userData userLocation);

    //Add more queries here


}
