package com.example.api.ui.layout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleService;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.api.R;
import com.example.api.databinding.FragmentMoviesDetailsBinding;
import com.example.api.databinding.FragmentTvDetailsBinding;

import com.example.api.models.TVDetailsModel;
import com.example.api.ui.viewmodel.DetailsViewModel;

public class TvDetailsFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private DetailsViewModel viewModel;
    private FragmentTvDetailsBinding binding;
    private int mParam1;
    private String allCategories = "";
    private int counter =0;
    private boolean scale = true;

    public TvDetailsFragment() {
        // Required empty public constructor
    }

    public static TvDetailsFragment newInstance(int param1) {
        TvDetailsFragment fragment = new TvDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(getActivity() ).get(DetailsViewModel.class);
        viewModel.intiTv();
        binding = FragmentTvDetailsBinding.inflate(inflater,container , false);
        viewModel.receiveTvDetails(mParam1);
        getMovieDetails();
        imgOnClick();
        return binding.getRoot();
    }
    private void getMovieDetails(){
        viewModel.getTvDetailsData().observe(getViewLifecycleOwner() , tvDetailsModel -> {
            binding.setData(tvDetailsModel);
            binding.detailsTvProgressBar.setVisibility(View.INVISIBLE);
            setCategories( tvDetailsModel);
        });
    }
    private void imgOnClick(){
        binding.tvDetailsImg.setOnClickListener(view -> {
            if(scale) {
                binding.tvDetailsImg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                scale = false;
            }
            else {
                binding.tvDetailsImg.setScaleType(ImageView.ScaleType.CENTER);
                scale = true;
            }
        });
    }
    private void setCategories(TVDetailsModel tvDetailsModel) {
        tvDetailsModel.getGenres().forEach(newModel -> {
            String category = tvDetailsModel.getGenres().get(counter).getName();
            allCategories += category + "   ";
            counter++;

        });

        binding.tvDetailsCategory.setText(allCategories);
    }

    public DetailsViewModel getViewModel() {
        return viewModel;
    }
}