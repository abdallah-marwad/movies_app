package com.example.api.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.api.R;
import com.example.api.common.helper.Utils;
import com.example.api.databinding.CustomRvBinding;
import com.example.api.models.MovieModel;

public class PagingMovieAdapter extends PagedListAdapter<MovieModel.ResultsBean , PagingMovieAdapter.MovieHolder> {
    OnClickAllMovie listener;

    public PagingMovieAdapter() {
        super(diffCallback);

    }



    private static DiffUtil.ItemCallback<MovieModel.ResultsBean> diffCallback=
            new DiffUtil.ItemCallback<MovieModel.ResultsBean>() {
                @Override
                public boolean areItemsTheSame(@NonNull MovieModel.ResultsBean oldItem, @NonNull MovieModel.ResultsBean newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull MovieModel.ResultsBean oldItem, @NonNull MovieModel.ResultsBean newItem) {
                    return oldItem.equals(newItem);
                }
            };
    public void setListener(OnClickAllMovie listener) {
        this.listener = listener;
    }



    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        MovieModel.ResultsBean resultsBean = getItem(position);
        Glide.with(holder.itemView.getContext()).
                load(Utils.IMG_PATH+resultsBean.getBackdrop_path()).placeholder(R.drawable.ic_baseline_error_outline_24).
                into(holder.binding.customImgPoster);
        holder.binding.customRate.setText(resultsBean.getVote_average()+"");
        holder.binding.getRoot().setOnClickListener(view ->listener.onClick(holder.getAdapterPosition()));
    }
    public interface OnClickAllMovie{
        void onClick(int position);
    }
    class MovieHolder extends RecyclerView.ViewHolder {
        CustomRvBinding binding;

        public MovieHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomRvBinding.bind(itemView);
        }
    }
}