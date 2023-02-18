package com.example.api.models;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class DataSourceFactory extends DataSource.Factory {

    PagingDataSource pagingDataSource =new PagingDataSource();
    @Override
    public DataSource create() {
        return pagingDataSource;
    }


    public PagingDataSource getPagingDataSource() {
        return pagingDataSource;
    }
}
