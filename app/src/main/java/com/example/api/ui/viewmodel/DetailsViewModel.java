package com.example.api.ui.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.api.data.repository.FavouriteRepository;
import com.example.api.data.repository.MovieRepository;
import com.example.api.data.repository.TvRepository;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.data.source.remote.Firebase;
import com.example.api.models.MovieDetailsModel;
import com.example.api.models.TVDetailsModel;
import com.example.api.ui.layout.LoginActivity;
import com.example.api.ui.layout.MainActivity;

import java.util.List;

public class DetailsViewModel extends ViewModel {
    Firebase firebase = new Firebase();
    MovieRepository movieRepository;
    TvRepository tvRepository;

    private MutableLiveData<MovieDetailsModel> detailsData;
    private MutableLiveData<String> errorMsg;

    private MutableLiveData<TVDetailsModel> tvDetailsData;
    private MutableLiveData<String> tvErrorMsg;
    public MutableLiveData<TVDetailsModel> getTvDetailsData() {
        return tvDetailsData;
    }

    public MutableLiveData<String> getTvErrorMsg() {
        return tvErrorMsg;
    }


    public MutableLiveData<MovieDetailsModel> getDetailsData() {
        return detailsData;
    }
    public MutableLiveData<String> getErrorMsg() {
        return errorMsg;
    }

    public void intiMovie(){
        movieRepository = new MovieRepository();
        detailsData = movieRepository.getMovieDetails();
        errorMsg = movieRepository.getMoviesErrorMSg();
    }
    public void receiveMovieDetails(int id){
        movieRepository.getMoviesDetails(id);
    }
    public void intiTv(){
        tvRepository = new TvRepository();
        tvDetailsData = tvRepository.getTvDetails();
        tvErrorMsg = tvRepository.getTvErrorMSg();
    }
//    public void receiveMovieDetails(int id){
//        tvRepository.getTvDetails(id);
//    }
    public void receiveTvDetails(int id){
        tvRepository.getTvDetails(id);
    }
    private FavouriteRepository repository;

    public void intiRepository(Application application){
        repository = new FavouriteRepository(application);
    }
    public void insertFavourite(FavoriteModel favoriteModel){
        repository.insertFavourite(favoriteModel);
    }
    public void deleteFavourite(FavoriteModel favoriteModel){
        repository.deleteFavourite(favoriteModel);

    }



    // region firebase

    public void setFirebaseUserId(String userId){
        firebase.setUserId(userId);

    }
    public void saveMovieInFirebase(FavoriteModel model){
        setFirebaseUserId(MainActivity.USER_ID);
        firebase.saveMovie(model);
    }

    public void checkIfMovieIdInFirebase(FavoriteModel model){
        setFirebaseUserId(MainActivity.USER_ID);
         firebase.checkIfMovieIdInFirebase(model);
    }

    private MutableLiveData<Boolean> userFounded = firebase.getFoundUser();

    public MutableLiveData<Boolean> getUserFounded() {
        return userFounded;
    }


//endregion



    public List<Integer> getAllFavouritesIds(){
        return repository.getAllFavouritesIds();
    }



}
