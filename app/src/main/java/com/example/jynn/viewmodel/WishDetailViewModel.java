package com.example.jynn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.jynn.model.DatabaseAppRepository;
import com.example.jynn.model.Wish;

public class WishDetailViewModel extends AndroidViewModel {

    DatabaseAppRepository databaseAppRepository;
    LiveData<Boolean> wishUpdatedLiveData;

    public WishDetailViewModel(@NonNull Application application) {
        super(application);
        databaseAppRepository = new DatabaseAppRepository(application);
        wishUpdatedLiveData = databaseAppRepository.getWishUpdatedLiveData();
    }

    public void updateWish(Wish wish){
        databaseAppRepository.updateWish(wish);
    }

    public String getCurrentUserId(){
        return databaseAppRepository.getCurrentUserId();
    }

    public LiveData<Boolean> getWishUpdatedLiveData() {
        return wishUpdatedLiveData;
    }
}
