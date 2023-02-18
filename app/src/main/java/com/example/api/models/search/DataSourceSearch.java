package com.example.api.models.search;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.api.common.helper.Utils;
import com.example.api.data.source.remote.RetrofitConnection;
import com.example.api.models.DataSourceTopMovies;
import com.example.api.models.MovieModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSourceSearch extends PageKeyedDataSource<Integer , MovieModel.ResultsBean> {
    String searchQuery;
    public void setQuery(String searchQuery ) {
        this.searchQuery = searchQuery;
    }
    MutableLiveData<String> errMsg = new MutableLiveData<>();
    MutableLiveData<Boolean> noResult = new MutableLiveData<>();

    public MutableLiveData<Boolean> getNoResult() {
        return noResult;
    }

    public MutableLiveData<String> getErrMsg() {
        return errMsg;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieModel.ResultsBean> callback) {
        RetrofitConnection.getInstance().getMoviesWithSearch(Utils.FIRST_PAGE,searchQuery).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.body() != null&&response.isSuccessful()) {
                    callback.onResult(response.body().getResults(), null , Utils.FIRST_PAGE+1 );
                }
                if(response.isSuccessful() && response.body().getResults().size() ==0){
                    noResult.postValue(true);
                }
            }
            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

                errMsg.setValue(t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieModel.ResultsBean> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieModel.ResultsBean> callback) {
        RetrofitConnection.getInstance().getMoviesWithSearch(params.key ,searchQuery).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.body() != null) {
                    int key = params.key+1;
                    callback.onResult(response.body().getResults(), key );
                }
                else {
                    errMsg.setValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

                errMsg.setValue(t.getLocalizedMessage());
            }
        });
    }
}
