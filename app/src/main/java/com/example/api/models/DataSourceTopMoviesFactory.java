package com.example.api.models;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class DataSourceTopMoviesFactory extends DataSource.Factory{
    private MutableLiveData<PageKeyedDataSource<Integer , MovieModel.ResultsBean>> topMovieLiveData =
            new MutableLiveData<>();
    DataSourceTopMovies topMovies = new DataSourceTopMovies();
    @Override
    public DataSource create() {
        topMovieLiveData.postValue(topMovies);
        return topMovies;
    }

    public DataSourceTopMovies getTopMovies() {
        return topMovies;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, MovieModel.ResultsBean>> getTopMovieLiveData() {
        return topMovieLiveData;
    }
}
