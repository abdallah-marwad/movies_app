package com.example.api.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

public class GenresModel {

    private List<GenresBean> genres;

    public List<GenresBean> getGenres() {
        return genres;
    }

    public void setGenres(List<GenresBean> genres) {
        this.genres = genres;
    }
    @Entity
    public static class GenresBean {
        /**
         * id : 28
         * name : Action
         */
        @PrimaryKey
        @NonNull
        private int id;
        @NonNull
        private String name;

        public GenresBean(String name){
            this.name = name;
        }
        public GenresBean(){
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
