package com.example.api.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.api.common.helper.Utils;
import com.example.api.data.source.remote.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagingDataSource extends PageKeyedDataSource<Integer , MovieModel.ResultsBean> {

    private MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public MutableLiveData<String> getErrorMsgAllMovies() {
        return errorMsg;
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, MovieModel.ResultsBean> callback) {
        Utils.executor.execute(() -> {
        RetrofitConnection.getInstance().getData(Utils.FIRST_PAGE).enqueue(new Callback<MovieModel>() {

            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if(response.body() != null) {
                    callback.onResult(response.body().getResults(), null , Utils.FIRST_PAGE+1 );
                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                errorMsg.postValue(t.getLocalizedMessage());
            }
        });
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieModel.ResultsBean> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, MovieModel.ResultsBean> callback) {
        Utils.executor.execute(() ->
        {
        RetrofitConnection.getInstance().getData(params.key).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if(response.body() != null) {
                    int key = params.key+1;
                    callback.onResult(response.body().getResults(), key );
                }

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                errorMsg.postValue(t.getLocalizedMessage());

            }
        });
        });
    }
}
