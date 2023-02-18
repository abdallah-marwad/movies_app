package com.example.api.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.api.common.helper.Utils;
import com.example.api.data.source.remote.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataSourceTvTop extends PageKeyedDataSource<Integer , TVModel.ResultsBean> {
    MutableLiveData<String> errMsg = new MutableLiveData<>();

    public MutableLiveData<String> getErrMsgTop() {
        return errMsg;
    }
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, TVModel.ResultsBean> callback) {

        RetrofitConnection.getInstance().getTvTopRated(Utils.FIRST_PAGE).enqueue(new Callback<TVModel>() {
            @Override
            public void onResponse(Call<TVModel> call, Response<TVModel> response) {
                if(response.body() != null && response.isSuccessful()) {
                    callback.onResult(response.body().getResults(), null , Utils.FIRST_PAGE+1);
                }
            }

            @Override
            public void onFailure(Call<TVModel> call, Throwable t) {
                errMsg.postValue(t.getLocalizedMessage());
            }

        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TVModel.ResultsBean> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, TVModel.ResultsBean> callback) {

        RetrofitConnection.getInstance().getTvTopRated(params.key).enqueue(new Callback<TVModel>() {
            @Override
            public void onResponse(Call<TVModel> call, Response<TVModel> response) {
                if(response.body() != null && response.isSuccessful()) {
                    int key = params.key+1;
                    callback.onResult(response.body().getResults(), key);
                }
            }

            @Override
            public void onFailure(Call<TVModel> call, Throwable t) {
                errMsg.postValue(t.getLocalizedMessage());
            }
        });

    }
}
