package com.example;
import androidx.databinding.adapters.Converters;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


import com.example.entities.User;
import com.example.dao.userDao;
import com.example.entities.userData;
import com.example.dao.userDataDao;
import com.example.entities.CustomConverters;


import android.content.Context;
import androidx.room.Room;


@Database(entities = {User.class, userData.class}, version = 5, exportSchema = false)
@TypeConverters(CustomConverters.class)
public abstract class appDatabase extends RoomDatabase {
    //Define abstract for each DAO interface
    public abstract userDao userDao();
    public abstract userDataDao userDataDao();

    //Singleton instance of the database
    private static appDatabase instance;

    //Method to get an instance of the database
    public static synchronized appDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            appDatabase.class, "my-database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

