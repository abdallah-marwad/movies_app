package com.example.api.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.api.common.helper.Utils;
import com.example.api.data.repository.FavouriteRepository;
import com.example.api.data.repository.SearchRepository;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.data.source.remote.Firebase;
import com.example.api.ui.layout.LoginActivity;
import com.example.api.ui.layout.MainActivity;

import java.util.List;

public class FavouriteViewModel extends ViewModel {
    private FavouriteRepository repository;

    public void intiRepository(Application application){
        repository = new FavouriteRepository(application);
    }

    public void deleteFavourite(FavoriteModel favoriteModel){
        repository.deleteFavourite(favoriteModel);

    }

    public LiveData<List<FavoriteModel>> getAllFavourites(){
        return repository.getAllFavourites();
    }
    public List<FavoriteModel> getAllFavouritesList(){
        return repository.getAllFavouritesList();
    }

    public List<Integer> getAllFavouritesIds(){
        return repository.getAllFavouritesIds();
    }


    //region firebase
    Firebase firebase = new Firebase();
    MutableLiveData<List<FavoriteModel>> userData = firebase.getUserData();

    public MutableLiveData<List<FavoriteModel>> getUserData() {
        return userData;
    }

    public void setFirebaseUserId(String userId){
        firebase.setUserId(userId);

    }
    public void getFirebaseMovies(){
        setFirebaseUserId(MainActivity.USER_ID);
        firebase.subscribeToRealTimeUpdates();
    }
    public void deleteFirebaseMovies(FavoriteModel model){
        setFirebaseUserId(MainActivity.USER_ID);
        firebase.deleteMovie(model);
    }
    //endregion
}
