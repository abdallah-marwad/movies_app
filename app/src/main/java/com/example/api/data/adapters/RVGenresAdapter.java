package com.example.api.data.adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.R;
import com.example.api.databinding.CustomGenresRvBinding;
import com.example.api.models.GenresModel;

import java.util.List;

public class RVGenresAdapter extends RecyclerView.Adapter<RVGenresAdapter.GenresHolder> {
    OnClickGenres listener;
    List<GenresModel.GenresBean> genresBeanList;
    int lastClickedPosition =-1;

    @NonNull
    @Override
    public GenresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GenresHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.custom_genres_rv ,parent ,false));


    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull GenresHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.binding.customGenresCategory.setText(genresBeanList.get(position).getName());
        holder.binding.customGenresCategory.setBackgroundResource(R.drawable.search_category_background);
        holder.binding.customGenresCategory.setOnClickListener(view -> {
            holder.binding.customGenresCategory.setBackgroundResource(R.drawable.recycler_item_selected);
            listener.onClick(position  , view);
            if (lastClickedPosition != -1){
                if(lastClickedPosition != position)
                     notifyItemChanged(lastClickedPosition);
            }
            lastClickedPosition = position;
        });

    }

    @Override
    public int getItemCount() {
        return genresBeanList == null ?0 :genresBeanList.size();
    }

    public void setGenresBeanList(List<GenresModel.GenresBean> genresBeanList) {
        this.genresBeanList = genresBeanList;
        notifyDataSetChanged();
    }

    public interface OnClickGenres{
        void onClick(int position , View view);
    }
    public void setListener(OnClickGenres listener) {
        this.listener = listener;
    }
    class GenresHolder extends RecyclerView.ViewHolder {
        CustomGenresRvBinding binding;

        public GenresHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomGenresRvBinding.bind(itemView);
        }
    }
}
