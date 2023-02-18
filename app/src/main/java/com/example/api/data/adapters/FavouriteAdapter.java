package com.example.api.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.api.R;
import com.example.api.common.helper.Utils;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.databinding.CustomSearchViewBinding;
import com.example.api.models.MovieModel;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteHolder> {
    OnClickFavourite listener;
    List<FavoriteModel> favoriteModels;

    public void setFavoriteModels(List<FavoriteModel> favoriteModels) {
        this.favoriteModels = favoriteModels;
        notifyDataSetChanged();
    }

    public List<FavoriteModel> getFavoriteModels() {
        return favoriteModels;
    }

    @NonNull
    @Override
    public FavouriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.custom_search_view,parent ,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteHolder holder, int position) {
        FavoriteModel favorite = favoriteModels.get(position);
        Glide.with(holder.itemView.getContext()).
                load(Utils.IMG_PATH+favorite.getPosterPath()).placeholder(R.drawable.ic_baseline_error_outline_24).
                into(holder.binding.customImgPosterCategorySearch);
        holder.binding.customSearchRate.setText(favorite.getMovieRate()+"");
        holder.binding.customSearchTitle.setText(favorite.getMovieName());
        holder.binding.getRoot().setOnClickListener(view -> listener.onClick(favorite));
    }

    @Override
    public int getItemCount() {
        return favoriteModels == null ? 0 :favoriteModels.size();
    }


    public interface OnClickFavourite{
        void onClick(FavoriteModel favorite );
    }
    public void setListener(OnClickFavourite listener) {
        this.listener = listener;
    }
    class FavouriteHolder extends RecyclerView.ViewHolder {
        CustomSearchViewBinding binding;

        public FavouriteHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomSearchViewBinding.bind(itemView);

        }
    }
}
