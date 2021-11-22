package com.example.jynn.model;

import android.app.Application;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AuthAppRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private MutableLiveData<FirebaseUser> userLiveData;
    private MutableLiveData<Boolean> loggedOutLiveData;

    public AuthAppRepository(Application application){
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        this.userLiveData = new MutableLiveData<>();
        this.loggedOutLiveData = new MutableLiveData<>();
        if(firebaseAuth.getCurrentUser() != null){
            userLiveData.postValue(firebaseAuth.getCurrentUser());
            loggedOutLiveData.postValue(false);
        }
    }

    public void writeNewUser(String uid, String name, String email, String photoURL){

        User user = new User(email,name,photoURL);
        databaseReference.child("users").child(uid).setValue(user);

    }

    public void uploadUserPhoto(Uri file){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
       final StorageReference userPhotosRef = firebaseStorage.getReference().child("userPhotos/"
                +FirebaseAuth.getInstance().getCurrentUser().getUid()+"/"+file.getLastPathSegment());
        UploadTask uploadTask = userPhotosRef.putFile(file);
        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return userPhotosRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Uri userPhotoUri = task.getResult();
                    updateUserPhoto(userPhotoUri);
                }
            }
        });
    }

    private void updateUserPhoto(Uri photoUri){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(photoUri)
                .build();
        firebaseAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    userLiveData.postValue(firebaseAuth.getCurrentUser());
                    databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).child("photoURL").setValue(firebaseAuth.getCurrentUser().getPhotoUrl().toString());
                }

            }
        });

    }


    public void login(String email, String password){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(ContextCompat.getMainExecutor(application.getApplicationContext()), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userLiveData.postValue(firebaseAuth.getCurrentUser());
                }else{
                    Toast.makeText(application.getApplicationContext(), "Login Failure:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void register(String name, String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(ContextCompat.getMainExecutor(application.getApplicationContext()), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name).build();
                    firebaseAuth.getCurrentUser().updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                               Log.d("UserName",firebaseAuth.getCurrentUser().getDisplayName());
                            }

                        }
                    });
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String userId = firebaseUser.getUid();
                    //String userName = firebaseUser.getDisplayName();
                    //String email = firebaseUser.getEmail();
                    //Uri photoURL = firebaseUser.getPhotoUrl();
                    writeNewUser(userId,name,email,"");
                    userLiveData.postValue(firebaseAuth.getCurrentUser());
                }else{
                    Toast.makeText(application.getApplicationContext(), "Registration Failure:"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void logOut(){
        firebaseAuth.signOut();
        loggedOutLiveData.postValue(true);
    }


    public MutableLiveData<FirebaseUser> getUserLiveData(){
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData(){
        return loggedOutLiveData;
    }

}
