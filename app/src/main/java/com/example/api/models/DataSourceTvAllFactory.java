package com.example.api.models;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class DataSourceTvAllFactory extends DataSource.Factory {
    MutableLiveData <PageKeyedDataSource<Integer , TVModel.ResultsBean>> allTVLiveData =
            new MutableLiveData<>();
    DataSourceTvAll tvAll = new DataSourceTvAll();
    @Override
    public DataSource create() {

        allTVLiveData.postValue(tvAll);
        return tvAll;
    }

    public DataSourceTvAll getTvAll() {
        return tvAll;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, TVModel.ResultsBean>> getAllTVLiveData() {
        return allTVLiveData;
    }
}
