package com.example.jynn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.jynn.model.DatabaseAppRepository;
import com.example.jynn.model.Wish;

public class AddWishViewModel extends AndroidViewModel {
    DatabaseAppRepository databaseAppRepository;
    MutableLiveData<Boolean> wishAddedLiveData;

    public AddWishViewModel(@NonNull Application application) {
        super(application);
        databaseAppRepository = new DatabaseAppRepository(application);
        wishAddedLiveData = databaseAppRepository.getWishAddedLiveData();
    }

    public void sendData(String title, String description){
        databaseAppRepository.sendData(title,description);
    }

    public MutableLiveData<Boolean> getWishAddedLiveData() {
        return wishAddedLiveData;
    }
}
