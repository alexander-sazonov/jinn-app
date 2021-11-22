package com.example.jynn.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.jynn.model.AuthAppRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoggedInViewModel extends AndroidViewModel {

    AuthAppRepository authAppRepository;
    MutableLiveData<FirebaseUser> userLiveData;
    MutableLiveData<Boolean> loggedOutLiveData;

    public LoggedInViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserLiveData();
        loggedOutLiveData = authAppRepository.getLoggedOutLiveData();
    }

    public void logOut(){
        authAppRepository.logOut();
    }

    public void uploadUserPhoto(Uri photoUri){
        authAppRepository.uploadUserPhoto(photoUri);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public MutableLiveData<Boolean> getLoggedOutLiveData() {
        return loggedOutLiveData;
    }
}
