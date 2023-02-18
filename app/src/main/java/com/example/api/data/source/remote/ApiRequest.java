package com.example.api.data.source.remote;


import com.example.api.models.GenresModel;
import com.example.api.models.MovieDetailsModel;
import com.example.api.models.MovieModel;
import com.example.api.models.TVDetailsModel;
import com.example.api.models.TVModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    //Movies
    @GET("discover/movie")
    Call<MovieModel> getData(@Query(value = "page") int page );
    @GET("genre/movie/list")
    Call<GenresModel> getAllGenres();
    @GET("movie/top_rated")
    Call<MovieModel> getTopRatedMovies(@Query(value = "page") int page);
    @GET("search/movie")
    Call<MovieModel> getMoviesWithSearch(@Query(value = "page") int page ,
                                         @Query(value = "query") String query   );
    @GET("movie/top_rated")
    Call<MovieModel> getMoviesWithCategory(@Query(value = "page")  int page
                                          ,@Query(value = "with_genres") int... genreId );
    @GET("movie/{movie_id}")
    Call<MovieDetailsModel> getDetailsData(@Path(value = "movie_id" , encoded = true  )  int id,
                                           @Query(value = "language")  String language);

    // TV
    @GET("discover/tv")
    Call<TVModel> getTvData(@Query(value = "page") int page);
    @GET("tv/top_rated")
    Call<TVModel> getTvTopRated(@Query(value = "page") int page);
    @GET("tv/{tv_id}")
    Call<TVDetailsModel> getTvDetailsData(@Path(value = "tv_id" , encoded = true)  int id);



}
