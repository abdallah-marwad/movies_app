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
import com.example.api.models.TVModel;

public class PagingAllTvAdapter extends PagedListAdapter<TVModel.ResultsBean , PagingAllTvAdapter.AllTvHolder> {
    OnClickAllTv listener;

    public PagingAllTvAdapter() {
        super(diffCallback1);
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



    @NonNull
    @Override
    public AllTvHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllTvHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_rv,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllTvHolder holder, int position) {
        TVModel.ResultsBean resultsBean = getItem(position);
        Glide.with(holder.itemView.getContext()).
                load(Utils.IMG_PATH+resultsBean.getBackdrop_path()).placeholder(R.drawable.ic_baseline_error_outline_24).
                into(holder.binding.customImgPoster);
        holder.binding.customRate.setText(resultsBean.getVote_average()+"");
        holder.binding.getRoot().setOnClickListener(view ->listener.onClick(holder.getAdapterPosition()));

    }


    public interface OnClickAllTv{
        void onClick(int position);
    }
    public void setListener(OnClickAllTv listener) {
        this.listener = listener;
    }

    class AllTvHolder extends RecyclerView.ViewHolder {
        CustomRvBinding binding;

        public AllTvHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomRvBinding.bind(itemView);
        }
    }
}
