package com.example.api.ui.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.api.databinding.FragmentMoviesDetailsBinding;
import com.example.api.models.MovieDetailsModel;
import com.example.api.ui.viewmodel.DetailsViewModel;


public class MoviesDetailsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";

    private int mParam1;
    private DetailsViewModel viewModel;
    private FragmentMoviesDetailsBinding binding;
    private int counter =0;
    private boolean picCounter = true ;
    private String allCategories= "";
    private String category = "";
    private boolean scale = true;

    public MoviesDetailsFragment() {
        // Required empty public constructor
    }

    public static MoviesDetailsFragment newInstance(int param1) {
        MoviesDetailsFragment fragment = new MoviesDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    public DetailsViewModel getViewModel() {
        return viewModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        Toast.makeText(getContext(), "oncreate view", Toast.LENGTH_SHORT).show();
        viewModel = new ViewModelProvider(getActivity() ).get(DetailsViewModel.class);
        viewModel.intiMovie();
        binding = FragmentMoviesDetailsBinding.inflate(inflater,container , false);

        viewModel.receiveMovieDetails(mParam1);
        getMovieDetails();
        imgOnClick();

        return binding.getRoot();
    }
//    private void goToVideo(){
//        binding.videoPlay.setOnClickListener(view -> {
//            startActivity(new Intent(getActivity() , VideoActivity.class));
//        });
//    }
    private void getMovieDetails(){
        viewModel.getDetailsData().observe(getViewLifecycleOwner(),movieDetailsModel -> {
            binding.setData(movieDetailsModel);
            unVisibleProgress( binding.detailsProgressBar);
            setCategories( movieDetailsModel);
        } );
    }


    private void imgOnClick(){
        binding.detailsImg.setOnClickListener(view -> {

            if(scale) {
                binding.detailsImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                scale = false;
            }
            else {
                binding.detailsImg.setScaleType(ImageView.ScaleType.CENTER);
                scale = true;
            }
        });
    }
    private void setCategories( MovieDetailsModel movieDetailsModel) {
        movieDetailsModel.getGenres().forEach(genresBean -> {
            category = movieDetailsModel.getGenres().get(counter).getName();
            allCategories+= category + "   ";
            counter++;


        });
        binding.detailsCategory.setText(allCategories);
    }
    private void unVisibleProgress( ProgressBar bar){
        bar.setVisibility(View.INVISIBLE);
    }


}