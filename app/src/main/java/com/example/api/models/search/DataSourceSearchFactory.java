package com.example.api.models.search;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.example.api.models.MovieModel;

public class DataSourceSearchFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer , MovieModel.ResultsBean>> movieSearchCategoryLive =
            new MutableLiveData<>();
    String searchQuery;

    public void setQuery(String searchQuery ) {
        this.searchQuery = searchQuery;

    }
    DataSourceSearch  search  = new DataSourceSearch();;
    
    @Override
    public DataSource create() {

        search.setQuery(searchQuery);
        movieSearchCategoryLive.postValue(search);
        return search;
    }

    public DataSourceSearch getSearch() {
        return search;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, MovieModel.ResultsBean>> getMovieSearchCategoryLive() {
        return movieSearchCategoryLive;
    }


}
