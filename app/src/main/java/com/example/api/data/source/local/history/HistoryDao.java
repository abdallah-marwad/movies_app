package com.example.api.data.source.local.history;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.api.models.GenresModel;

import java.util.List;

@Dao
public interface HistoryDao {
    @Query("select * from SearchHistoryModel")
    LiveData<List<SearchHistoryModel>> getAllHistory();

    @Query("Delete from SearchHistoryModel")
    void deleteAll();

    @Query("Delete from SearchHistoryModel where id =:id")
    void deleteItem(int id);

    @Insert
    void insertHistory(SearchHistoryModel searchHistoryModel);

    @Query("select * from SearchHistoryModel")
    LiveData<List<SearchHistoryModel>> getSearchHistory();

    @Query("select * from SearchHistoryModel where name  LIKE '%' ||:query || '%' ")
    LiveData<List<SearchHistoryModel>> getSearchHistoryWithQuery(String query);

}
