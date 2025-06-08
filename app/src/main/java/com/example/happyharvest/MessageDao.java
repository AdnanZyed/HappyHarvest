package com.example.happyharvest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    void insertMessage(Message message);

    @Query("SELECT * FROM Message WHERE (senderUsername = :currentUser AND receiverUsername = :otherUser) OR (senderUsername = :otherUser AND receiverUsername = :currentUser) ORDER BY timestamp ASC")
    LiveData<List<Message>> getMessagesBetweenUsers(String currentUser, String otherUser);

    @Query("SELECT * FROM Message WHERE receiverUsername = :username ORDER BY timestamp DESC LIMIT 1")
    LiveData<Message> getLastMessageForUser(String username);
}
