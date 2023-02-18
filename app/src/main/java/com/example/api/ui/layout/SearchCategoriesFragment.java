package com.example.api.ui.layout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.example.api.R;
import com.example.api.data.adapters.PagingSearch;
import com.example.api.data.adapters.RVGenresAdapter;
import com.example.api.data.adapters.RvCategorySearch;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.databinding.ActivitySearchBinding;
import com.example.api.databinding.FragmentSearchCategoriesBinding;
import com.example.api.models.GenresModel;
import com.example.api.models.MovieModel;
import com.example.api.ui.viewmodel.DetailsViewModel;
import com.example.api.ui.viewmodel.SearchViewModel;

import java.util.List;
public class SearchCategoriesFragment extends Fragment {

    FragmentSearchCategoriesBinding binding;
    SearchViewModel viewModel;
    RVGenresAdapter genresAdapter;
    RVGenresAdapter.OnClickGenres clickGenres;
    FragmentCategoryOnClick listener;
    PagingSearch pagingSearch;
    RvCategorySearch categorySearch;
    RvCategorySearch.OnClickSearchCategory onClickSearchCategory;
    int itemSelected;
    SearchFragOnClick fragOnClick;


    public void setListener(FragmentCategoryOnClick listener) {
        this.listener = listener;
    }

    // Fragment Items Onclick
    public interface FragmentCategoryOnClick {
        void onClickTV(int id);
    }

    public SearchCategoriesFragment() {
    }


    public void setFragOnClick(SearchFragOnClick fragOnClick) {
        this.fragOnClick = fragOnClick;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchCategoriesBinding.inflate(inflater, container, false);
        intiRvSearch();
        getAllGenres();
        refreshBtnOnClick();
        return binding.getRoot();
    }

    private void intiRv() {
        genresAdapter = new RVGenresAdapter();
        binding.searchFragRv.setAdapter(genresAdapter);
        binding.searchFragRv.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.HORIZONTAL));
        binding.searchFragRv.setHasFixedSize(true);
    }

    public void getAllGenres() {
        binding.searchCategoryProgress.setVisibility(View.VISIBLE);
        binding.btnRefresh.setVisibility(View.INVISIBLE);
        binding.fragSearchTxt.setVisibility(View.INVISIBLE);
        viewModel.intiGenres(getActivity().getApplication());
        viewModel.doAllGenres();
        intiRv();
        viewModel.getAllGenres().observe(getViewLifecycleOwner(), genresBeanList -> {
            binding.searchCategoryProgress.setVisibility(View.INVISIBLE);
            binding.btnRefresh.setVisibility(View.INVISIBLE);
            binding.fragSearchTxt.setVisibility(View.INVISIBLE);
            genresAdapter.setGenresBeanList(genresBeanList);
            listenerGeners(genresBeanList);

        });
        viewModel.getAllGenresErrMsg().observe(getViewLifecycleOwner(), s -> {

            showErrMsg();
        });

    }

    // RV for category search
    private void intiRvSearch() {
        categorySearch = new RvCategorySearch();
        binding.RVResults.setAdapter(categorySearch);
        binding.RVResults.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.RVResults.setHasFixedSize(true);
    }


    private void getRepositorySearch() {
        viewModel.getLiveDataSearch().observe(getViewLifecycleOwner(), resultsBeans -> {
            binding.searchCategoryProgress.setVisibility(View.INVISIBLE);
            binding.btnRefresh.setVisibility(View.INVISIBLE);
            binding.RVResults.setVisibility(View.VISIBLE);
            categorySearch.setResultsBeans(resultsBeans);

            setCategorySearchOnclick(resultsBeans);
        });
        viewModel.getErrMsg().observe(getViewLifecycleOwner(), s -> {

            showErrMsg();

        });
    }
    private void refreshBtnOnClick(){
        binding.btnRefresh.setOnClickListener(view -> {
           getAllGenres();
            binding.btnRefresh.setVisibility(View.INVISIBLE);
            binding.fragSearchTxt.setVisibility(View.INVISIBLE);
            binding.searchCategoryProgress.setVisibility(View.VISIBLE);
        });
    }
    @SuppressLint("ResourceAsColor")
    private void showErrMsg(){
        binding.searchCategoryProgress.setVisibility(View.INVISIBLE);
        binding.fragSearchTxt.setVisibility(View.VISIBLE);
        binding.btnRefresh.setVisibility(View.VISIBLE);
        binding.RVResults.setVisibility(View.INVISIBLE);
        binding.fragSearchTxt.setText("Check Internet Connection.");
        binding.fragSearchTxt.setTextColor(R.color.hint_color);
    }
    private void setId(int id) {
        //Call first
        viewModel.setId(id);
    }

    private void sendFavoriteModel(List<MovieModel.ResultsBean> resultsBean, int position) {
        int id = resultsBean.get(position).getId();
        double vote = resultsBean.get(position).getVote_average();
        String name = resultsBean.get(position).getOriginal_title();
        String path = resultsBean.get(position).getPoster_path();
        fragOnClick.searchOnClick(new FavoriteModel(id, vote, name, path));

    }

    private void listenerGeners(List<GenresModel.GenresBean> genresBean) {
        clickGenres = (position, view) -> {
            setId(genresBean.get(position).getId());
            viewModel.intiRepositorySearch();
            getRepositorySearch();
            if (genresBean.get(position).getName().equals("Documentary")) {
                binding.btnRefresh.setVisibility(View.INVISIBLE);
                binding.fragSearchTxt.setVisibility(View.VISIBLE);
                binding.fragSearchTxt.setText("No Result Found");
            } else {
                binding.searchCategoryProgress.setVisibility(View.VISIBLE);
                binding.fragSearchTxt.setVisibility(View.INVISIBLE);
                binding.btnRefresh.setVisibility(View.INVISIBLE);
                binding.RVResults.setVisibility(View.INVISIBLE);
            }

        };
        genresAdapter.setListener(clickGenres);
    }

    private void setCategorySearchOnclick(List<MovieModel.ResultsBean> resultsBeans) {
        categorySearch.setListener(position -> {
            sendFavoriteModel(resultsBeans, position);
        });
    }

    public interface SearchFragOnClick {
        void searchOnClick(FavoriteModel favoriteModel);
    }
}