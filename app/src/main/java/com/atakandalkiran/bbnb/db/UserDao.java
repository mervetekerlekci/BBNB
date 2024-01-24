package com.atakandalkiran.bbnb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Insert;



import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM  user")
    List<User> getAllUsers();

    @Insert
    void InsertUser(User users);

    @Delete
    void DeleteUser(User user);

    @Insert
    void InsertCardInformations(CardDetailsModel cardDetails);

    @Query("SELECT * FROM carddetailsmodel")
    List<CardDetailsModel> getAllCardInformations();

    @Delete
    void DeleteCardInformations(CardDetailsModel cardDetails);

    @Query("SELECT * FROM user WHERE email = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM user WHERE citizenship_no = :citizenshipNo AND password = :password")
    User getUserLoginInformations(String citizenshipNo, String password);

    @Query("SELECT * FROM user WHERE citizenship_no =:citizenshipNo AND email =:email")
    User validateUsersExistence(String citizenshipNo, String email);

    @Query("SELECT * FROM carddetailsmodel WHERE card_no = :cardNo")
    CardDetailsModel validateCardExistence(String cardNo);

    @Query("UPDATE user SET password = :newPassword WHERE email = :email AND citizenship_no = :citizenshipNo")
    void updatePassword(String email, String citizenshipNo, String newPassword);

    @Query("SELECT * FROM carddetailsmodel WHERE userId = :userId")
    LiveData<List<CardDetailsModel>> getCardDetailsByUserId(int userId);
}
