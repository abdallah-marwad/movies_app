package com.example.api.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.api.R;
import com.example.api.common.helper.Utils;
import com.example.api.databinding.CustomRvBinding;
import com.example.api.databinding.CustomRvTvBinding;
import com.example.api.models.MovieModel;

public class PagingTopMoviesAdapter extends PagedListAdapter<MovieModel.ResultsBean , PagingTopMoviesAdapter.TopMovieHolder> {
    OnClickPaging listener;

    public PagingTopMoviesAdapter() {
        super(diffCallback);

    }
    public interface OnClickPaging{
        void onClick(int position);
    }
    @NonNull
    @Override
    public TopMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopMovieHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_tv,parent ,false));

    }

    @Override
    public void onBindViewHolder(@NonNull TopMovieHolder holder, int position) {
        MovieModel.ResultsBean resultsBean = getItem(position);
        Glide.with(holder.itemView.getContext()).
                load(Utils.IMG_PATH+resultsBean.getBackdrop_path()).placeholder(R.drawable.ic_baseline_error_outline_24).
                into(holder.binding.customImgTVPoster);
        holder.binding.customTvRate.setText(resultsBean.getVote_average()+"");
        holder.binding.getRoot().setOnClickListener(view ->listener.onClick(holder.getAdapterPosition()));

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
    public void setListener(OnClickPaging listener) {
        this.listener = listener;
    }





    class TopMovieHolder extends RecyclerView.ViewHolder {
        CustomRvTvBinding binding;

        public TopMovieHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomRvTvBinding.bind(itemView);
        }
    }
}
