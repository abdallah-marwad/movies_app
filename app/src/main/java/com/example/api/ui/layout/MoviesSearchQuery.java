package com.example.api.ui.layout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.api.R;
import com.example.api.data.adapters.PagingSearchQueryAdapter;
import com.example.api.data.adapters.RvCategorySearch;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.databinding.FragmentMoviesSearchQueryBinding;
import com.example.api.models.MovieModel;
import com.example.api.ui.viewmodel.SearchViewModel;

public class MoviesSearchQuery extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private String mParam1;
    FragmentMoviesSearchQueryBinding binding;
    SearchViewModel viewModel;
    PagingSearchQueryAdapter searchQueryAdapter;
    SearchQueryOnClick queryOnClick;
    PagingSearchQueryAdapter.OnClickSearchQueryMovie adapterListener;

    public MoviesSearchQuery() {
        // Required empty public constructor
    }


    public static MoviesSearchQuery newInstance(String searchQuery) {
        MoviesSearchQuery fragment = new MoviesSearchQuery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, searchQuery);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        viewModel = new ViewModelProvider(getActivity() ).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoviesSearchQueryBinding.inflate(inflater , container ,false);
        viewModel.setSearchQuery(mParam1);
        showProgressBar();
        getSearchQueryResult();
        refreshClick();
        checkList();

        return binding.getRoot();
    }
    private void intiRvQuerySearch(){
        searchQueryAdapter = new PagingSearchQueryAdapter();
        binding.fragQueryRv.setAdapter(searchQueryAdapter);
        binding.fragQueryRv.setLayoutManager(new LinearLayoutManager(getContext() ));
        binding.fragQueryRv.setHasFixedSize(true);
    }
    public void getSearchQueryResult(){
        intiRvQuerySearch();
        viewModel.intiMovieSearchPage();
        viewModel.getMovieSearchPageList().observe(getViewLifecycleOwner() , resultsBeanPagedList -> {
                searchQueryAdapter.submitList(resultsBeanPagedList);
                searchMovieOnClick(resultsBeanPagedList);

        });
        viewModel.getErrFromDatasource().observe(getViewLifecycleOwner() , s -> {
            showErrMsg();
            binding.fragQueryTxtNoResult.setText("Check Internet Connection.");
        });

    }
    private void refreshClick(){
        binding.txtRefresh.setOnClickListener(view -> {
            showProgressBar();
            getSearchQueryResult();

        });

    }
    private void showProgressBar(){
        binding.fragQueryTxtNoResult.setVisibility(View.INVISIBLE);
        binding.txtRefresh.setVisibility(View.INVISIBLE);
        binding.searchResultProgress.setVisibility(View.VISIBLE);

    } private void showErrMsg(){
        binding.fragQueryTxtNoResult.setVisibility(View.VISIBLE);
        binding.txtRefresh.setVisibility(View.VISIBLE);
        binding.searchResultProgress.setVisibility(View.INVISIBLE);

    }
    private void hideAll(){
        binding.fragQueryTxtNoResult.setVisibility(View.INVISIBLE);
        binding.txtRefresh.setVisibility(View.INVISIBLE);
        binding.searchResultProgress.setVisibility(View.INVISIBLE);
    }
    public void checkList(){
        viewModel.getIsEmpty().observe(getViewLifecycleOwner() , aBoolean -> {
            if (!aBoolean) {
                hideAll();
            }
        });
        viewModel.getNoResult().observe(getViewLifecycleOwner() , aBoolean -> {
            if(aBoolean){
                hideAll();
                binding.fragQueryTxtNoResult.setVisibility(View.VISIBLE);
                binding.fragQueryTxtNoResult.setText("No Result Founded");
            }
        });


    }
    private void sendFavoriteModel(PagedList<MovieModel.ResultsBean> resultsBean , int position){
        int id =resultsBean.get(position).getId();
        double vote =resultsBean.get(position).getVote_average();
        String name =resultsBean.get(position).getOriginal_title();
        String path =resultsBean.get(position).getPoster_path();
        queryOnClick.onClickQuery(new FavoriteModel(id , vote ,name , path));

    }
    public interface SearchQueryOnClick{
        void onClickQuery(FavoriteModel favoriteModel) ;
    }

    public void setQueryOnClick(SearchQueryOnClick queryOnClick) {
        this.queryOnClick = queryOnClick;
    }

    private void searchMovieOnClick(PagedList<MovieModel.ResultsBean> resultsBean){
        adapterListener = position -> {
            sendFavoriteModel(resultsBean , position);    };
        searchQueryAdapter.setListener(adapterListener );
    }
}