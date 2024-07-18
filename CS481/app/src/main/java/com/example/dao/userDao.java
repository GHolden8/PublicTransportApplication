package com.example.dao;
import com.example.entities.User;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;

@Dao
public interface userDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    //get user by username
    @Query("SELECT * FROM users WHERE name = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE uid = :userId")
    User getUserById(int userId);

    //test*************************************************Remove after
    @Query("SELECT * FROM users")
    List<User> getAllUsers();

}
