package com.example

import android.content.Context
import com.example.appDatabase
import com.example.dao.userDao
import com.example.entities.User
import com.example.entities.userData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import com.example.dao.userDataDao


class UserRepository(context: Context) {
    private val userDao: userDao = appDatabase.getInstance(context).userDao()
    private val userDataDao: userDataDao = appDatabase.getInstance(context).userDataDao()


    //inserts user
    suspend fun insertUser(user: User) {
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            userDao.getAllUsers()
        }
    }

    //hashes the users password
    private fun hashPassword(password: String): String {
        //hashes using SHA-256
        val bytes = password.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("", { str, it -> str + "%02x".format(it) })
    }

    //authenticate user function used in LoginActivity
    suspend fun authenticateUser(username: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            val user = userDao.getUserByUsername(username)
            user != null && user.password == hashPassword(password)
        }
    }

    suspend fun getUserIdByUsername(username: String): Int? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByUsername(username)?.uid
        }
    }

    suspend fun deleteUserLocation(userId: Int, locationName: String) {
        withContext(Dispatchers.IO) {
            val userData = userDataDao.getUserDataByUID(userId)
            if (userData != null) {
                val updatedPinnedLocations = userData.pinnedLocations.toMutableList()
                updatedPinnedLocations.remove(locationName)
                userData.setPinnedLocations(updatedPinnedLocations)
                userDataDao.update(userData)
            }
        }
    }

    suspend fun getPinnedLocationCount(userId: Int): Int {
        return withContext(Dispatchers.IO) {
            val userDataInstance = userDataDao.getUserDataByUID(userId)
            userDataInstance?.getPinnedLocations()?.size ?: 0
        }
    }

    suspend fun savePinnedLocation(userId: Int, locationName: String) {
        withContext(Dispatchers.IO) {
            val userDataInstance = userDataDao.getUserDataByUID(userId)
            if (userDataInstance != null) {
                val newPinnedLocations = userDataInstance.getPinnedLocations().toMutableList().apply {
                    add(locationName)
                }
                userDataInstance.setPinnedLocations(newPinnedLocations)
                userDataDao.update(userDataInstance)
            }
        }
    }


    suspend fun getPinnedLocationsForUser(userId: Int): List<String> {
        return withContext(Dispatchers.IO) {
            val userData = userDataDao.getUserDataByUID(userId)
            userData?.pinnedLocations ?: emptyList()
        }
    }





}
