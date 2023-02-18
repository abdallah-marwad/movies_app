package com.example.api.ui.layout;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.example.api.data.adapters.PagingMovieAdapter;
import com.example.api.data.adapters.PagingTopMoviesAdapter;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.databinding.FragmentMoviesBinding;
import com.example.api.models.DataSourceTopMovies;
import com.example.api.models.MovieModel;
import com.example.api.ui.viewmodel.DetailsViewModel;
import com.example.api.ui.viewmodel.MainViewModel;


public class MoviesFragment extends Fragment {
    FragmentMoviesBinding binding;
    MainViewModel viewModel;
    PagingMovieAdapter movieAdapter;
    PagingMovieAdapter.OnClickAllMovie allMoviesAdapterListener;
    PagingTopMoviesAdapter topMovieAdapter;
    PagingTopMoviesAdapter topMovieAdapter1;
    PagingTopMoviesAdapter.OnClickPaging topMoviesAdapterListener;
    FragmentItemOnClick fragmentListener;


    public MoviesFragment() {
    }

    public interface FragmentItemOnClick{
        void onClick(FavoriteModel favoriteModel);
    }

    public void setViewModel(MainViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof FragmentItemOnClick){
            fragmentListener = (FragmentItemOnClick) context;

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
        binding = FragmentMoviesBinding.inflate(inflater ,container , false);
        binding.mainMoviesLinear.setVisibility(View.INVISIBLE);


        getAllMovieData();
        getTopMovieData();
        checkList();
        refreshOnClick();
        return binding.getRoot();
    }
    private void showRefresh(){
        binding.mainMovieErrMsg.setVisibility(View.VISIBLE);
        binding.mainMovieRefresh.setVisibility(View.VISIBLE);
    }private void hideRefresh(){
        binding.mainMovieErrMsg.setVisibility(View.INVISIBLE);
        binding.mainMovieRefresh.setVisibility(View.INVISIBLE);
    }
    // AllMovies Recycler
    private void intiRvAllMovies(){
        movieAdapter = new PagingMovieAdapter();
        binding.mainAllMovieRv.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.HORIZONTAL , false));
        binding.mainAllMovieRv.setAdapter(movieAdapter);
        
    }

    public void getAllMovieData() {
        viewModel.intiMoviePage();
        intiRvAllMovies();
        binding.progressBar2.setVisibility(View.VISIBLE);
        hideRefresh();
        viewModel.getMovieResultPageList().observe(getViewLifecycleOwner() , resultsBeanPagedList -> {
            movieAdapter.submitList(resultsBeanPagedList);
            allMovieOnClick(resultsBeanPagedList);
            binding.mainMoviesLinear.setVisibility(View.VISIBLE);

        });}

    public MainViewModel getViewModel() {
        return viewModel;
    }

    private void allMovieOnClick(PagedList<MovieModel.ResultsBean> resultsBean){
        allMoviesAdapterListener = position -> {
            sendFavoriteModel(  resultsBean,position);
        };
        movieAdapter.setListener(allMoviesAdapterListener);
    }
    private void sendFavoriteModel(PagedList<MovieModel.ResultsBean> resultsBean , int position){
        int id =resultsBean.get(position).getId();
        double vote =resultsBean.get(position).getVote_average();
        String name =resultsBean.get(position).getOriginal_title();
        String path =resultsBean.get(position).getPoster_path();
        fragmentListener.onClick(new FavoriteModel(id , vote ,name , path));

    }
    private void refreshOnClick(){
        binding.mainMovieRefresh.setOnClickListener(view -> {
            binding.progressBar2.setVisibility(View.VISIBLE);
            getAllMovieData();
            getTopMovieData();
            checkList();
        });
    }
    public void checkList(){
        viewModel.getIsEmptyAllMovie().observe(getViewLifecycleOwner() , aBoolean -> {
            if (!aBoolean) {

                binding.progressBar2.setVisibility(View.INVISIBLE);
                hideRefresh();
            }
        });
        viewModel.getErrAllMovie().observe(getViewLifecycleOwner() , s  -> {
            binding.progressBar2.setVisibility(View.INVISIBLE);
            showRefresh();
        });

        viewModel.getErrTopMovie().observe(getViewLifecycleOwner() , s  -> {
            binding.progressBar2.setVisibility(View.INVISIBLE);
            showRefresh();
        });

        viewModel.getIsEmptyTopMovie().observe(getViewLifecycleOwner() , aBoolean -> {
            if (!aBoolean) {
                binding.progressBar2.setVisibility(View.INVISIBLE);
                hideRefresh();
            }
        });



    }
    //Top Rated Movies
    private void intiRvTopMovies(){
        topMovieAdapter = new PagingTopMoviesAdapter();
        binding.mainTopMovieRv.setLayoutManager(new LinearLayoutManager(getContext() , RecyclerView.HORIZONTAL , false));
        binding.mainTopMovieRv.setAdapter(topMovieAdapter);

    }
    public void getTopMovieData() {
        intiRvTopMovies();
        viewModel.intiTopMoviePage();
        binding.progressBar2.setVisibility(View.VISIBLE);
        hideRefresh();
        viewModel.getTopMoviePageList().observe(getViewLifecycleOwner() , resultsBeanPagedList -> {
            topMovieAdapter.submitList(resultsBeanPagedList);
            topMovieOnClick(resultsBeanPagedList);
            binding.mainMoviesLinear.setVisibility(View.VISIBLE);

//            movieAdapterOnClick(resultsBeanPagedList,context);
        });

//        viewModel.getMovieErrorMsg().observe(lifecycleOwner ,s -> {
//            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//        });
    }
    private void topMovieOnClick(PagedList<MovieModel.ResultsBean> resultsBean){
        topMoviesAdapterListener = position -> {
            sendFavoriteModel(  resultsBean,position);
        };
        topMovieAdapter.setListener(topMoviesAdapterListener);
    }


}