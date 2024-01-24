package com.atakandalkiran.bbnb.db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

import kotlinx.parcelize.Parcelize;

@Entity
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int userId;
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    @ColumnInfo(name = "citizenship_no")
    public String citizenshipNo;
    @ColumnInfo(name = "email")
    public String email;
    @ColumnInfo(name = "phone_number")
    public String phoneNumber;
    @ColumnInfo(name = "password")
    public String password;
    @ColumnInfo(name = "confirm_password")
    public String confirmPassword;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCitizenshipNo() {
        return citizenshipNo;
    }

    public void setCitizenshipNo(String citizenshipNo) {
        this.citizenshipNo = citizenshipNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public User() {
        // Your constructor logic here...
    }

    protected User(Parcel in) {
        userId = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        citizenshipNo = in.readString();
        email = in.readString();
        phoneNumber = in.readString();
        password = in.readString();
        confirmPassword = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userId);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(citizenshipNo);
        dest.writeString(email);
        dest.writeString(phoneNumber);
        dest.writeString(password);
        dest.writeString(confirmPassword);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
