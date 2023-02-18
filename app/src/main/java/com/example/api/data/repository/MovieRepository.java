package com.example.api.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.api.data.source.remote.RetrofitConnection;
import com.example.api.models.MovieDetailsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    MutableLiveData<MovieDetailsModel> movieDetails;
    MutableLiveData<String> moviesErrorMSg;

    public MovieRepository() {
        movieDetails = new MutableLiveData<>();
        moviesErrorMSg = new MutableLiveData<>();
    }

    public MutableLiveData<MovieDetailsModel> getMovieDetails() {
        return movieDetails;
    }

    public MutableLiveData<String> getMoviesErrorMSg() {
        return moviesErrorMSg;
    }

    public void getMoviesDetails(int id){
        RetrofitConnection.getInstance().getDetailsData(id , "en").enqueue(new Callback<MovieDetailsModel>() {
            @Override
            public void onResponse(Call<MovieDetailsModel> call, Response<MovieDetailsModel> response) {
                if(response.isSuccessful()&& response!=null) {
                    movieDetails.setValue(response.body());
                }
                else {
                    moviesErrorMSg.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieDetailsModel> call, Throwable t) {
                moviesErrorMSg.setValue(t.getLocalizedMessage());
            }
        });
    }

}
