package com.example.jynn.model;

import android.app.Application;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseAppRepository {

    private static String TAG = "DatabaseAppRepository";

    private Application application;
    private DatabaseReference databaseReference;
    private DatabaseReference wishesReference;
    private ValueEventListener wishesListener;
    private MutableLiveData<Boolean> wishAddedLiveData;
    private List<Wish> wishes;
    private MutableLiveData<List<Wish>> wishesLiveData;

    public DatabaseAppRepository(Application application){
        this.application = application;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        wishAddedLiveData = new MutableLiveData<>();
        wishes = new ArrayList<>();
        wishesLiveData = new MutableLiveData<>();
        wishAddedLiveData.postValue(false);
        fetchData();
    }

    public MutableLiveData<Boolean> getWishAddedLiveData() {
        return wishAddedLiveData;
    }

    public void sendData(String title, String description){
        if((!TextUtils.isEmpty(title))&&(!TextUtils.isEmpty(description))){
            String key = databaseReference.child("wishes").push().getKey();
            Wish wish = new Wish(key,title,description);
            wish.setCreateUserId(FirebaseAuth.getInstance().getCurrentUser().getUid());
            wish.setCreateUserName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            Uri photoUrl = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();
            if(photoUrl != null){
                wish.setCreateUserPhotoUrl(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString());
            }

            Map<String,Object> wishValues= wish.toMap();
            Map<String,Object> childUpdates = new HashMap<>();
            childUpdates.put("/wishes/"+key,wishValues);
            databaseReference.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        wishAddedLiveData.postValue(true);
                    }
                }
            });
        }

    }

    public void fetchData(){
        wishesReference = databaseReference.child("wishes");
        wishesListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                wishes.clear();
                for(DataSnapshot child : snapshot.getChildren()){
                    Wish wish = child.getValue(Wish.class);
                    wish.setId(child.getKey());
                    wishes.add(wish);
                    Log.d(TAG,wish.getTitle());
                }
                wishesLiveData.setValue(wishes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        wishesReference.addValueEventListener(wishesListener);
    }

    public User findUserById(String uid){
        final User[] user = new User[1];
        Query userByIdQuery = databaseReference.child("users").child(uid);
        userByIdQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user[0] = snapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return user[0];
    }

    public MutableLiveData<List<Wish>> getWishesLiveData() {
        return wishesLiveData;
    }
}
