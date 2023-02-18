package com.example.api.data.source.local.favorite;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insertFavourite(FavoriteModel favoriteModel);

    @Delete
    void deleteFavourite(FavoriteModel favoriteModel);

    @Query("select * from FavoriteModel")
    LiveData<List<FavoriteModel>> getAllFavourites();
    @Query("select * from FavoriteModel")
    List<FavoriteModel> getAllFavouritesList();

    @Query("select movieId from FavoriteModel")
    List<Integer> getAllFavouritesIds();
}
