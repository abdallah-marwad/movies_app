package com.example.api.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.api.R;
import com.example.api.common.helper.Utils;
import com.example.api.databinding.CustomSearchViewBinding;
import com.example.api.models.MovieModel;
import com.google.android.material.shape.CornerFamily;

import java.util.List;

public class RvCategorySearch extends RecyclerView.Adapter<RvCategorySearch.CategoryHolder> {
    List<MovieModel.ResultsBean> resultsBeans;
    OnClickSearchCategory listener;

    public void setResultsBeans(List<MovieModel.ResultsBean> resultsBeans) {
        this.resultsBeans = resultsBeans;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.custom_search_view,parent ,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        MovieModel.ResultsBean resultsBean = resultsBeans.get(position);
        Glide.with(holder.itemView.getContext()).
                load(Utils.IMG_PATH+resultsBean.getPoster_path()).placeholder(R.drawable.ic_baseline_error_outline_24).
                into(holder.binding.customImgPosterCategorySearch);
        holder.binding.customSearchRate.setText(resultsBean.getVote_average()+"");
        holder.binding.customSearchTitle.setText(resultsBean.getOriginal_title()+"");
        holder.binding.getRoot().setOnClickListener(view -> {
            listener.onClick(position);
        });
    }
    public interface OnClickSearchCategory{
        void onClick(int position );
    }
    public void setListener(OnClickSearchCategory listener) {
        this.listener = listener;
    }
    @Override
    public int getItemCount() {
        return resultsBeans == null ? 0 :resultsBeans.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder {
        CustomSearchViewBinding binding;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomSearchViewBinding.bind(itemView);

        }
}
}
