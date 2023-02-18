package com.example.api.ui.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.contentcapture.ContentCaptureCondition;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.example.api.common.helper.Utils;
import com.example.api.data.repository.SearchRepository;
import com.example.api.data.source.local.history.SearchHistoryModel;
import com.example.api.models.DataSourceFactory;
import com.example.api.models.GenresModel;
import com.example.api.models.MovieModel;
import com.example.api.models.search.DataSourceSearchFactory;

import java.util.List;

public class SearchViewModel extends ViewModel {
    private SearchRepository repository;
    int id;
    private String searchQuery;

    public void intiGenres(Application application){
        repository = new SearchRepository(application);

    }
    public void intiSearchHistory(Application application){
        repository = new SearchRepository(application);

    }

    public void doAllGenres() {
        repository.getAllGenres();
    }
    public LiveData<List<GenresModel.GenresBean>> getAllGenres() {

        return repository.getAllGenresLiveData();
    }

    // paging search with Category
    LiveData movieSearchCategoryPageList;
    public void intiMoviePage(){
        DataSourceSearchFactory factory = new DataSourceSearchFactory();

//        factory.setId(id);
        PagedList.Config config = (new PagedList.Config.Builder()).setPageSize(2).setEnablePlaceholders(false)
                .build();
        movieSearchCategoryPageList = new LivePagedListBuilder(factory ,config )

                .build();

    }

    public MutableLiveData<Boolean> getIsEmpty() {
        return isEmpty;
    }

    public LiveData<PagedList<MovieModel.ResultsBean>> getMovieSearchCategoryPageList() {
        return movieSearchCategoryPageList;
    }

    // paging with search
    MutableLiveData<Boolean> isEmpty = new MutableLiveData<>();
    MutableLiveData<String> err = new MutableLiveData<>();
    MutableLiveData<String> errFromDatasource = new MutableLiveData<>();
    MutableLiveData<Boolean> noResult = new MutableLiveData<>();
    LiveData<PagedList<MovieModel.ResultsBean>> movieSearchPageList;


    public MutableLiveData<Boolean> getNoResult() {
        return noResult;
    }

    public MutableLiveData<String> getErrFromDatasource() {
        return errFromDatasource;
    }

    public void intiMovieSearchPage(){

        DataSourceSearchFactory factory = new DataSourceSearchFactory();
        factory.create().addInvalidatedCallback(new DataSource.InvalidatedCallback() {
            @Override
            public void onInvalidated() {
                err.postValue("err");
            }
        });

        errFromDatasource = factory.getSearch().getErrMsg();
        noResult = factory.getSearch().getNoResult();
        factory.setQuery(searchQuery);
        PagedList.Config config = (new PagedList.Config.Builder()).setPageSize(2).setEnablePlaceholders(false)
                .build();
        movieSearchPageList = new LivePagedListBuilder(factory ,config )
                .setBoundaryCallback(new PagedList.BoundaryCallback() {
                    @Override
                    public void onZeroItemsLoaded() {
                        super.onZeroItemsLoaded();
                        isEmpty.setValue(true);
                    }

                    @Override
                    public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                        super.onItemAtFrontLoaded(itemAtFront);
                        isEmpty.setValue(false);
                    }
                })
                .build();

    }
    public LiveData<PagedList<MovieModel.ResultsBean>> getMovieSearchPageList() {
        return movieSearchPageList;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    // geners Repository
    public void intiRepositorySearch(){
       repository.setId(id);
        repository.getSearchCategory();
    }

    public MutableLiveData<String> getErrMsg(){
        return repository.getErrMsg();
    }
    public MutableLiveData<String> getAllGenresErrMsg(){
        return repository.getAllGenresErrMsg();
    }


    public MutableLiveData<List<MovieModel.ResultsBean>>getLiveDataSearch() {
        return repository.getLiveData();
    }
    public void setId(int id) {
       this.id = id;
    }

    // History Dao
    public void insertHistory(SearchHistoryModel searchHistoryModel){
            repository.insertHistory(searchHistoryModel);
    }
    public LiveData<List<SearchHistoryModel>> getSearchHistory() {
        return repository.getSearchHistory();
    }
    public LiveData<List<SearchHistoryModel>> getSearchHistoryWithQuery(String query) {
        return repository.getSearchHistoryWithQuery(query);
    }
    public void deleteHistory(int id){
        repository.deleteHistory(id);

    }
}
