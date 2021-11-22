package com.example.jynn.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Wish implements Parcelable {
    private String id;
    private String title;
    private String description;
    private String createUserId;
    private String createUserName;
    private String createUserPhotoUrl;
    private String fulfillUserId;
    private String fulfillUserName;
    private String fulfillUserPhotoUrl;
    private boolean isDone = false;
    private String comment;

    public Wish() {

    }

    public Wish(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.description = content;
        createUserId = "";
        createUserName = "";
        createUserPhotoUrl = "";
        fulfillUserId = "";
        fulfillUserName = "";
        fulfillUserPhotoUrl = "";
        comment = "";
    }

    public static Creator<Wish> CREATOR = new Creator<Wish>() {
        @Override
        public Wish createFromParcel(Parcel parcel) {
            String id = parcel.readString();
            String title = parcel.readString();
            String description = parcel.readString();
            String createUserId = parcel.readString();
            String createUserName = parcel.readString();
            String createUserPhotoUrl = parcel.readString();
            String fulfillUserId = parcel.readString();
            String fulfillUserName = parcel.readString();
            String fulfillUserPhotoUrl = parcel.readString();
            String isDoneString = parcel.readString();
            Boolean isDone = Boolean.parseBoolean(isDoneString);
            String comment = parcel.readString();
            Wish wish = new Wish(id,title,description);
            wish.setCreateUserName(createUserName);
            wish.setCreateUserId(createUserId);
            wish.setCreateUserPhotoUrl(createUserPhotoUrl);
            wish.setFulfillUserId(fulfillUserId);
            wish.setFulfillUserName(fulfillUserName);
            wish.setFulfillUserPhotoUrl(fulfillUserPhotoUrl);
            wish.setDone(isDone);
            wish.setComment(comment);
            return wish;
        }

        @Override
        public Wish[] newArray(int size) {
            return new Wish[size];
        }
    };


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getFulfillUserId() {
        return fulfillUserId;
    }

    public void setFulfillUserId(String fulfillUserId) {
        this.fulfillUserId = fulfillUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getFulfillUserName() {
        return fulfillUserName;
    }

    public void setFulfillUserName(String fulfillUserName) {
        this.fulfillUserName = fulfillUserName;
    }

    public String getCreateUserPhotoUrl() {
        return createUserPhotoUrl;
    }

    public void setCreateUserPhotoUrl(String createUserPhotoUrl) {
        this.createUserPhotoUrl = createUserPhotoUrl;
    }

    public String getFulfillUserPhotoUrl() {
        return fulfillUserPhotoUrl;
    }

    public void setFulfillUserPhotoUrl(String fulfillUserPhotoUrl) {
        this.fulfillUserPhotoUrl = fulfillUserPhotoUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Exclude
    Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);
        map.put("createUserId", createUserId);
        map.put("createUserName", createUserName);
        map.put("createUserPhotoUrl", createUserPhotoUrl);
        map.put("fulfillUserId", fulfillUserId);
        map.put("fulfillUserName", fulfillUserName);
        map.put("fulfillUserPhotoUrl", fulfillUserPhotoUrl);
        map.put("isDone", isDone);
        map.put("comment", comment);
        return map;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(createUserId);
        parcel.writeString(createUserName);
        parcel.writeString(createUserPhotoUrl);
        parcel.writeString(fulfillUserId);
        parcel.writeString(fulfillUserName);
        parcel.writeString(fulfillUserPhotoUrl);
        parcel.writeString(new Boolean(isDone).toString());
        parcel.writeString(comment);


    }
}
