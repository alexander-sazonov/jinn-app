package com.example.jynn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.jynn.model.DatabaseAppRepository;
import com.example.jynn.model.User;
import com.example.jynn.model.Wish;

import java.util.List;

public class WishesViewModel extends AndroidViewModel {

    MutableLiveData<List<Wish>> wishesLiveData;
    DatabaseAppRepository databaseAppRepository;

    public WishesViewModel(@NonNull Application application) {
        super(application);
        databaseAppRepository = new DatabaseAppRepository(application);
        wishesLiveData = databaseAppRepository.getWishesLiveData();
    }


    public MutableLiveData<List<Wish>> getWishesLiveData() {
        return wishesLiveData;
    }

    public String getCurrentUserId(){
        return databaseAppRepository.getCurrentUserId();
    }

}
