package com.example.api.data.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.api.R;
import com.example.api.data.source.local.history.SearchHistoryModel;
import com.example.api.databinding.CustomGenresRvBinding;
import com.example.api.databinding.CustomSearchHistoryBinding;
import com.example.api.models.GenresModel;

import java.util.List;


public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.HistoryHolder> {
    OnClickSearchQuery listener;
    private List<SearchHistoryModel> historyList;

    public void setHistoryList(List<SearchHistoryModel> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    public List<SearchHistoryModel> getHistoryList() {
        return historyList;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.custom_search_history ,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        holder.binding.historyTxt.setText(historyList.get(position).getName());
        holder.binding.getRoot().setOnClickListener(view -> {
            listener.onClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return  historyList == null ?0 :historyList.size();
    }

    public interface OnClickSearchQuery{
        void onClick(int position );
    }
    public void setListener(OnClickSearchQuery listener) {
        this.listener = listener;
    }
    class HistoryHolder extends RecyclerView.ViewHolder {
        CustomSearchHistoryBinding binding;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomSearchHistoryBinding.bind(itemView);
        }
    }
}
