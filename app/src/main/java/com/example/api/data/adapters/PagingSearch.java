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
import com.example.api.databinding.CustomRvTvBinding;
import com.example.api.databinding.CustomSearchViewBinding;
import com.example.api.models.MovieModel;

public class PagingSearch extends PagedListAdapter<MovieModel.ResultsBean , PagingSearch.SearchCategoryHolder> {
    OnClickSearchCategory listener;

    public PagingSearch() {
        super(diffCallback);

    }

    @NonNull
    @Override
    public SearchCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchCategoryHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custom_search_view,parent ,false));
    }


    @Override
    public void onBindViewHolder(@NonNull SearchCategoryHolder holder, int position) {
        MovieModel.ResultsBean resultsBean = getItem(position);
        Glide.with(holder.itemView.getContext()).
                load(Utils.IMG_PATH+resultsBean.getPoster_path()).placeholder(R.drawable.ic_baseline_error_outline_24).
                into(holder.binding.customImgPosterCategorySearch);
        holder.binding.customSearchRate.setText(resultsBean.getVote_average()+"");
        holder.binding.customSearchTitle.setText(resultsBean.getOriginal_title()+"");

    }

    public interface OnClickSearchCategory{
        void onClick(int position);
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
    public void setListener(OnClickSearchCategory listener) {
        this.listener = listener;
    }





    class SearchCategoryHolder extends RecyclerView.ViewHolder {
        CustomSearchViewBinding binding;

        public SearchCategoryHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomSearchViewBinding.bind(itemView);
        }
    }
}
