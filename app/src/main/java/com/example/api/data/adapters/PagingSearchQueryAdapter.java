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
import com.example.api.databinding.CustomSearchViewBinding;
import com.example.api.models.MovieModel;

public class PagingSearchQueryAdapter extends PagedListAdapter<MovieModel.ResultsBean ,PagingSearchQueryAdapter.MovieSearchHolder> {
    OnClickSearchQueryMovie listener;

    public void setListener(OnClickSearchQueryMovie listener) {
        this.listener = listener;
    }

    public PagingSearchQueryAdapter() {
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

    @NonNull
    @Override
    public MovieSearchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieSearchHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.custom_search_view,parent ,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MovieSearchHolder holder, int position) {
        MovieModel.ResultsBean resultsBean = getItem(position);
        Glide.with(holder.itemView.getContext()).
                load(Utils.IMG_PATH+resultsBean.getPoster_path()).placeholder(R.drawable.ic_baseline_error_outline_24).
                into(holder.binding.customImgPosterCategorySearch);
        holder.binding.customSearchRate.setText(resultsBean.getVote_average()+"");
        holder.binding.customSearchTitle.setText(resultsBean.getOriginal_title()+"");
        holder.binding.getRoot().setOnClickListener(view -> {
            listener.onSearchClick(position);
        });
    }
//    public int getItemWithId(){
//
//    }
    public interface OnClickSearchQueryMovie{
        void onSearchClick(int position);
    }

    class MovieSearchHolder extends RecyclerView.ViewHolder {
        CustomSearchViewBinding binding;

        public MovieSearchHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomSearchViewBinding.bind(itemView);
        }
    }
}
