package com.example.api.ui.layout;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.api.R;
import com.example.api.data.adapters.PagingAllTvAdapter;
import com.example.api.data.adapters.PagingMovieAdapter;
import com.example.api.data.adapters.PagingTopMoviesAdapter;
import com.example.api.data.adapters.PagingTopTvAdapter;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.databinding.FragmentMoviesBinding;
import com.example.api.databinding.FragmentTvShowBinding;
import com.example.api.models.MovieModel;
import com.example.api.models.TVModel;
import com.example.api.ui.viewmodel.MainViewModel;


public class TvShowFragment extends Fragment {
    FragmentTvShowBinding binding;
    MainViewModel viewModel;
    PagingAllTvAdapter allTvAdapter;
    PagingAllTvAdapter.OnClickAllTv allTVAdapterListener;
    TvShowFragment.FragmentItemOnClick fragmentListener;
    PagingTopTvAdapter topTvAdapter;
    PagingTopTvAdapter.OnClickTopTv topTVAdapterListener;


    public TvShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof MoviesFragment.FragmentItemOnClick){
            fragmentListener = (TvShowFragment.FragmentItemOnClick) context;
        }
        else {
            throw new ClassCastException("FragmentItemOnClick Isn`t a parent to movie fragment");
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity() ).get(MainViewModel.class);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTvShowBinding.inflate(inflater ,container , false);
        binding.mainTvLinear.setVisibility(View.INVISIBLE);
        getAllTvData();
        getTopTvData();
        checkList();
        refreshOnClick();
        return binding.getRoot();
    }
    // All Tv Recycler
    private void initAllTvRv(){
        allTvAdapter = new PagingAllTvAdapter();
        binding.mainAllTvRv.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.HORIZONTAL , false));
        binding.mainAllTvRv.setAdapter(allTvAdapter);

    }
    public void checkList(){
        viewModel.getIsEmptyAllTV().observe(getViewLifecycleOwner() , aBoolean -> {
            if (!aBoolean) {

                binding.progressBarTV.setVisibility(View.INVISIBLE);
                hideRefresh();
            }
        });
        viewModel.getErrAllTv().observe(getViewLifecycleOwner() , s  -> {
            binding.progressBarTV.setVisibility(View.INVISIBLE);
            showRefresh();
        });

        viewModel.getErrTopTv().observe(getViewLifecycleOwner() , s  -> {
            binding.progressBarTV.setVisibility(View.INVISIBLE);
            showRefresh();
        });

        viewModel.getIsEmptyTopTV().observe(getViewLifecycleOwner() , aBoolean -> {
            if (!aBoolean) {
                binding.progressBarTV.setVisibility(View.INVISIBLE);
                hideRefresh();
            }
        });



    }
    private void refreshOnClick(){
        binding.mainTvRefresh.setOnClickListener(view -> {
            binding.progressBarTV.setVisibility(View.VISIBLE);
        getAllTvData();
        getTopTvData();
        checkList();
    });
    }
    private void showRefresh(){
        binding.mainTvErrMsg.setVisibility(View.VISIBLE);
        binding.mainTvRefresh.setVisibility(View.VISIBLE);
    }private void hideRefresh(){
        binding.mainTvErrMsg.setVisibility(View.INVISIBLE);
        binding.mainTvRefresh.setVisibility(View.INVISIBLE);
    }
    public void getAllTvData() {
        viewModel.intiAllTvPage();
        initAllTvRv();
        hideRefresh();
        binding.progressBarTV.setVisibility(View.VISIBLE);
        viewModel.getAllTvPageList().observe(getViewLifecycleOwner() , resultsBeanPagedList -> {
            allTvAdapter.submitList(resultsBeanPagedList);
            allTvOnClick(resultsBeanPagedList);
            binding.mainTvLinear.setVisibility(View.VISIBLE);
        });
    }
    private void allTvOnClick(PagedList<TVModel.ResultsBean> resultsBean){
        allTVAdapterListener = position -> {
            sendFavoriteModel(resultsBean , position);
        };
        allTvAdapter.setListener(allTVAdapterListener);
    }


    private void sendFavoriteModel(PagedList<TVModel.ResultsBean> resultsBean , int position){
        int id =resultsBean.get(position).getId();
        double vote =resultsBean.get(position).getVote_average();
        String name =resultsBean.get(position).getOriginal_name();
        String path =resultsBean.get(position).getPoster_path();
        fragmentListener.onClickTV(new FavoriteModel(id , vote ,name , path));

    }

    // Top Tv Recycler
    private void initTopTvRv(){
        topTvAdapter = new PagingTopTvAdapter();
        binding.mainTopTvRv.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.HORIZONTAL , false));
        binding.mainTopTvRv.setAdapter(topTvAdapter);

    }
    public void getTopTvData() {
        viewModel.intiTopTvPage();
        initTopTvRv();
        hideRefresh();
        binding.progressBarTV.setVisibility(View.VISIBLE);
        viewModel.getTopTvPageList().observe(getViewLifecycleOwner() , resultsBeanPagedList  -> {
            topTvAdapter.submitList(resultsBeanPagedList);
            topTvOnClick(resultsBeanPagedList);
            binding.mainTvLinear.setVisibility(View.VISIBLE);
        });
    }
    private void topTvOnClick(PagedList<TVModel.ResultsBean> resultsBean){
        topTVAdapterListener = position -> {
            sendFavoriteModel(resultsBean , position);
        };
        topTvAdapter.setListener(topTVAdapterListener);
    }



    // Fragment Items Onclick
    public interface FragmentItemOnClick {
        void onClickTV(FavoriteModel favoriteModel);
    }


}