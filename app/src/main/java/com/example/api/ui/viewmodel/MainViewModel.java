package com.example.api.ui.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;



import com.example.api.models.DataSourceFactory;
import com.example.api.models.DataSourceTopMoviesFactory;
import com.example.api.models.DataSourceTvAllFactory;
import com.example.api.models.DataSourceTvTopFactory;
import com.example.api.models.MovieModel;
import com.example.api.models.PagingDataSource;
import com.example.api.models.TVModel;

import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<MovieModel.ResultsBean>> movieModel;
    private MutableLiveData<String> errorMsg;
    private MutableLiveData<List<TVModel.ResultsBean>> tvModel;
    private MutableLiveData<String> tvErrorMsg;





    // paging all tv
    MutableLiveData<Boolean> isEmptyAllTV = new MutableLiveData<>();
    MutableLiveData<String> errAllTv = new MutableLiveData<>();
    private LiveData<PagedList<TVModel.ResultsBean>> allTvPageList;
    private LiveData<PageKeyedDataSource<Integer , TVModel.ResultsBean>> allTvLiveData;

    public void intiAllTvPage(){
        DataSourceTvAllFactory factory= new DataSourceTvAllFactory();
        errAllTv = factory.getTvAll().getErrMsgAll();
        allTvLiveData = factory.getAllTVLiveData();
        allTvPageList = new LivePagedListBuilder(factory , 2).setBoundaryCallback(new PagedList.BoundaryCallback() {
            @Override
            public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                isEmptyAllTV.setValue(false);
            }
        }).build();

    }
    public MutableLiveData<Boolean> getIsEmptyAllTV() {
        return isEmptyAllTV;
    }
    public LiveData<PagedList<TVModel.ResultsBean>> getAllTvPageList() {
        return allTvPageList;
    }

    public LiveData<PageKeyedDataSource<Integer, TVModel.ResultsBean>> getAllTvLiveData() {
        return allTvLiveData;
    }



    // paging top tv
    private LiveData<PagedList<TVModel.ResultsBean>> topTvPageList;
    private LiveData<PageKeyedDataSource<Integer , TVModel.ResultsBean>> topTvLiveData;
    MutableLiveData<Boolean> isEmptyTopTV = new MutableLiveData<>();
    MutableLiveData<String> errTopTv = new MutableLiveData<>();

    public MutableLiveData<String> getErrAllTv() {
        return errAllTv;
    }

    public MutableLiveData<String> getErrTopTv() {
        return errTopTv;
    }

    public void intiTopTvPage(){
        DataSourceTvTopFactory factory= new DataSourceTvTopFactory();
        errTopTv = factory.getTvTop().getErrMsgTop();
        topTvLiveData = factory.getTopTVLiveData();
        topTvPageList = new LivePagedListBuilder(factory ,2).setBoundaryCallback(new PagedList.BoundaryCallback() {
            @Override
            public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                isEmptyTopTV.setValue(false);
            }
        }).build();
    }

    public MutableLiveData<Boolean> getIsEmptyTopTV() {
        return isEmptyTopTV;
    }

    public LiveData<PagedList<TVModel.ResultsBean>> getTopTvPageList() {
        return topTvPageList;
    }
    public LiveData<PageKeyedDataSource<Integer, TVModel.ResultsBean>> getTopTvLiveData() {
        return topTvLiveData;
    }


    // paging all movies
    private MutableLiveData<String> movieErrorMsg = new MutableLiveData<>();
    private LiveData<PagedList<MovieModel.ResultsBean>> movieResultPageList;
    MutableLiveData<Boolean> isEmptyAllMovie= new MutableLiveData<>();
    MutableLiveData<String> errAllMovie = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsEmptyAllMovie() {
        return isEmptyAllMovie;
    }

    public MutableLiveData<String> getErrAllMovie() {
        return errAllMovie;
    }

    public void intiMoviePage(){

        DataSourceFactory factory= new DataSourceFactory();
        errAllMovie = factory.getPagingDataSource().getErrorMsgAllMovies();


        movieResultPageList = new LivePagedListBuilder(factory ,2 ).setBoundaryCallback(new PagedList.BoundaryCallback() {
            @Override
            public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                isEmptyAllMovie.setValue(false);
            }
        }).build();

    }
    public LiveData<PagedList<MovieModel.ResultsBean>> getMovieResultPageList() {
        return movieResultPageList;
    }



    // paging top movies
    private LiveData<PagedList<MovieModel.ResultsBean>> topMoviePageList;
    private LiveData<PageKeyedDataSource<Integer , MovieModel.ResultsBean>> topMovieLiveData;
    MutableLiveData<Boolean> isEmptyTopMovie= new MutableLiveData<>();
    MutableLiveData<String> errTopMovie = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsEmptyTopMovie() {
        return isEmptyTopMovie;
    }

    public MutableLiveData<String> getErrTopMovie() {
        return errTopMovie;
    }
    public void intiTopMoviePage(){
        DataSourceTopMoviesFactory factory= new DataSourceTopMoviesFactory();
        topMovieLiveData = factory.getTopMovieLiveData();
        topMoviePageList = new LivePagedListBuilder(factory ,2).setBoundaryCallback(new PagedList.BoundaryCallback() {
            @Override
            public void onItemAtFrontLoaded(@NonNull Object itemAtFront) {
                super.onItemAtFrontLoaded(itemAtFront);
                isEmptyTopMovie.setValue(false);
            }
        }).build();
     errTopMovie = factory.getTopMovies().getErrorMsgTopMovies();
    }
    public LiveData<PagedList<MovieModel.ResultsBean>> getTopMoviePageList() {
        return topMoviePageList;
    }




    //paging
    public MutableLiveData<String> getMovieErrorMsg() {
        return movieErrorMsg;
    }


//    public void getTv(){
////        repository.getTv();
//    }

    public MutableLiveData<List<MovieModel.ResultsBean>> getMovieModel() {
        return movieModel;
    }

    public MutableLiveData<String> getErrorMsg() {
        return errorMsg;
    }

    public MutableLiveData<List<TVModel.ResultsBean>> getTvModel() {
        return tvModel;
    }

    public MutableLiveData<String> getTvErrorMsg() {
        return tvErrorMsg;
    }
}
