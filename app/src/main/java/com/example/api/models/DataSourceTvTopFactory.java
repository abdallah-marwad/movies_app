package com.example.api.models;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class DataSourceTvTopFactory extends DataSource.Factory {
    MutableLiveData<PageKeyedDataSource<Integer , TVModel.ResultsBean>> topTVLiveData =
            new MutableLiveData<>();
    DataSourceTvTop topTv = new DataSourceTvTop();
    @Override
    public DataSource create() {

        topTVLiveData.postValue(topTv);
        return topTv;
    }

    public DataSourceTvTop getTvTop() {
        return topTv;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, TVModel.ResultsBean>> getTopTVLiveData() {
        return topTVLiveData;
    }
}
