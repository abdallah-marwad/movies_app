package com.example.api.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.api.common.helper.Utils;
import com.example.api.data.source.local.SearchHistoryDao;
import com.example.api.data.source.local.MyRoomDB;
import com.example.api.data.source.local.history.HistoryDao;
import com.example.api.data.source.local.history.SearchHistoryModel;
import com.example.api.data.source.remote.RetrofitConnection;
import com.example.api.models.GenresModel;
import com.example.api.models.MovieModel;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchRepository {
    private MyRoomDB roomDB;
    private SearchHistoryDao searchHistoryDao;
    private MutableLiveData<List<MovieModel.ResultsBean>>liveData ;
    private MutableLiveData<String> errMsg ;
    private MutableLiveData<List<GenresModel.GenresBean>>allGenresLiveData ;
    private MutableLiveData<String> allGenresErrMsg ;
    SearchHistoryDao historyDao1;
    HistoryDao historyDao;
    int id;

    public SearchRepository(Application application) {
        roomDB = MyRoomDB.getDatabaseInstance(application);
        historyDao = roomDB.historyDao();
        liveData = new MutableLiveData();
        allGenresLiveData = new MutableLiveData();
        allGenresErrMsg = new MutableLiveData();
        errMsg = new MutableLiveData();

    }

    public MutableLiveData<String> getErrMsg() {
        return errMsg;
    }

    // History Dao
    public void insertHistory(SearchHistoryModel searchHistoryModel){
        Utils.executor.execute(() -> {
            historyDao.insertHistory(searchHistoryModel);
        });
    }
    public void deleteHistory(int id){
        Utils.executor.execute(() -> {
            historyDao.deleteItem(id);
        });
    }
    public LiveData<List<SearchHistoryModel>> getSearchHistory() {
        return historyDao.getSearchHistory();
    }
    public LiveData<List<SearchHistoryModel>> getSearchHistoryWithQuery(String query) {
        return historyDao.getSearchHistoryWithQuery(query);
    }




    public MutableLiveData<List<MovieModel.ResultsBean>> getLiveData() {
        return liveData;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MutableLiveData<List<GenresModel.GenresBean>> getAllGenresLiveData() {
        return allGenresLiveData;
    }

    public MutableLiveData<String> getAllGenresErrMsg() {
        return allGenresErrMsg;
    }

    public void getAllGenres(){
        Utils.executor.execute(() -> {
            RetrofitConnection.getInstance().getAllGenres().enqueue(new Callback<GenresModel>() {
                @Override
                public void onResponse(Call<GenresModel> call, Response<GenresModel> response) {
                    if(response.body() != null&&response.isSuccessful()) {
                        allGenresLiveData.postValue(response.body().getGenres());
                    }
                    else {
                        allGenresErrMsg.postValue(response.message());
                    }
                }

                @Override
                public void onFailure(Call<GenresModel> call, Throwable t) {
                    allGenresErrMsg.postValue(t.getLocalizedMessage());

                }
            });
        });
    }
    public void getSearchCategory(){
        Utils.executor.execute(() -> {
        RetrofitConnection.getInstance().getMoviesWithCategory(Utils.FIRST_PAGE,id).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if(response.body() != null&&response.isSuccessful()) {
                    liveData.postValue(response.body().getResults());
                }
                else if (!response.isSuccessful()) {
                    errMsg.postValue(response.message());
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                errMsg.postValue(t.getLocalizedMessage());


            }
        });
        });
    }
}



