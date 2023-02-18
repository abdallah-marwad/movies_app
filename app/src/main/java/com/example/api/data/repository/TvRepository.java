package com.example.api.data.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.api.data.source.remote.RetrofitConnection;
import com.example.api.models.MovieDetailsModel;
import com.example.api.models.TVDetailsModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvRepository {
    MutableLiveData<TVDetailsModel> tvDetails;
    MutableLiveData<String> tvErrorMSg;

    public TvRepository() {
        tvDetails = new MutableLiveData<>();
        tvErrorMSg = new MutableLiveData<>();
    }

    public MutableLiveData<TVDetailsModel> getTvDetails() {
        return tvDetails;
    }

    public MutableLiveData<String> getTvErrorMSg() {
        return tvErrorMSg;
    }

    public void getTvDetails(int id){
        RetrofitConnection.getInstance().getTvDetailsData(id).enqueue(new Callback<TVDetailsModel>() {
            @Override
            public void onResponse(Call<TVDetailsModel> call, Response<TVDetailsModel> response) {
                if(response.isSuccessful()&& response!=null) {
                    tvDetails.setValue(response.body());
                }
                else {
                    tvErrorMSg.setValue(response.message());
                }
            }



            @Override
            public void onFailure(Call<TVDetailsModel> call, Throwable t) {
                tvErrorMSg.setValue(t.getLocalizedMessage());
            }
        });
    }

}
