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
import com.example.api.models.TVModel;

public class PagingTopTvAdapter extends PagedListAdapter<TVModel.ResultsBean , PagingTopTvAdapter.TopTvHolder> {
    OnClickTopTv listener;

    public PagingTopTvAdapter() {
        super(diffCallback1);
    }

    @NonNull
    @Override
    public TopTvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TopTvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv_tv,parent ,false));

    }

    @Override
    public void onBindViewHolder(@NonNull TopTvHolder holder, int position) {
        TVModel.ResultsBean resultsBean = getItem(position);
        Glide.with(holder.itemView.getContext()).
                load(Utils.IMG_PATH+resultsBean.getBackdrop_path()).placeholder(R.drawable.ic_baseline_error_outline_24).
                into(holder.binding.customImgTVPoster);
        holder.binding.customTvRate.setText(resultsBean.getVote_average()+"");
        holder.binding.getRoot().setOnClickListener(view ->listener.onClick(holder.getAdapterPosition()));

    }

    private static DiffUtil.ItemCallback<TVModel.ResultsBean> diffCallback1=
            new DiffUtil.ItemCallback<TVModel.ResultsBean>() {
                @Override
                public boolean areItemsTheSame(@NonNull TVModel.ResultsBean oldItem, @NonNull TVModel.ResultsBean newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull TVModel.ResultsBean oldItem, @NonNull TVModel.ResultsBean newItem) {
                    return oldItem.equals(newItem);
                }
            };





    public interface OnClickTopTv{
        void onClick(int position);
    }
    public void setListener(OnClickTopTv listener) {
        this.listener = listener;
    }

    class TopTvHolder extends RecyclerView.ViewHolder {
        CustomRvTvBinding binding;

        public TopTvHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomRvTvBinding.bind(itemView);
        }
    }
}
