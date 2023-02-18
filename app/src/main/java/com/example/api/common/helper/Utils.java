package com.example.api.common.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Utils {
    public static final String API_KEY="cdf59ce841b5fddc7bf921a2c4992087";
    public static final String IMG_PATH="https://image.tmdb.org/t/p/w500";
    public static final String MOVIE_DETAILS_CODE="movie_id";
    public static final String TV_DETAILS_CODE="tv_id";
    public static final int FIRST_PAGE=1;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService executor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    @BindingAdapter("android:setImgPath")
    public static void setImgPath(ImageView img , String url){
        Glide.with(img.getContext()).load(IMG_PATH+url).into(img);
    }
    @BindingAdapter("android:setBackgroundPath")
    public static void setBackgroundPath(ImageView img , String url){
        Glide.with(img.getContext()).load(IMG_PATH+url).into(img);
    }




}
