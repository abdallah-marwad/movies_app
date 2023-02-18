package com.example.api.ui.layout;

import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.api.R;
import com.example.api.data.adapters.SearchHistoryAdapter;
import com.example.api.data.source.local.history.SearchHistoryModel;
import com.example.api.databinding.FragmentSearchHistoryFargmentBinding;
import com.example.api.models.GenresModel;
import com.example.api.ui.viewmodel.SearchViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;


public class SearchHistoryFragment extends Fragment {
    SearchViewModel viewModel;
    FragmentSearchHistoryFargmentBinding binding;
    private static final String ARG_PARAM1 = "param1";
    SearchHistoryAdapter adapter;
    SearchHistoryAdapter.OnClickSearchQuery clickSearchQuery;
    FragHistoryListener listener;
    private MutableLiveData<Boolean> start = new MutableLiveData<>();



    private String mParam1;

    public MutableLiveData<Boolean> getStart() {
        return start;
    }



    public SearchHistoryFragment() {
    }
    public interface FragHistoryListener{
        void historyOnClick(String query);
    }

    public void setListener(FragHistoryListener listener) {
        this.listener = listener;
    }

    public static SearchHistoryFragment newInstance(String query) {
        SearchHistoryFragment fragment = new SearchHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, query);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        viewModel = new ViewModelProvider(getActivity()).get(SearchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchHistoryFargmentBinding.inflate(inflater,container,false);
        initRV();
        viewModel.intiSearchHistory(getActivity().getApplication());
        getSearchHistory();
        start.setValue(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(onSwipeLeft(adapter));
        itemTouchHelper.attachToRecyclerView(binding.historyRv);


        return binding.getRoot();
        }
        private void initRV(){
            adapter = new SearchHistoryAdapter();
            binding.historyRv.setAdapter(adapter);
            binding.historyRv.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.historyRv.setHasFixedSize(true);
        }
    private void getSearchHistory(){
       viewModel.getSearchHistory().observe(getViewLifecycleOwner() ,  searchHistoryModels-> {
           adapter.setHistoryList(searchHistoryModels);
           getSearchQuery(searchHistoryModels);
       });
    }
    public void insertHistory(String query){
       viewModel.insertHistory(new SearchHistoryModel(query));
    }
    public void getSearchHistoryWithQuery(String query){
        viewModel.getSearchHistoryWithQuery(query).observe(getViewLifecycleOwner() ,  searchHistoryModels-> {
            adapter.setHistoryList(searchHistoryModels);
            getSearchQuery(searchHistoryModels);
        });
    }
    private void getSearchQuery(List<SearchHistoryModel> searchHistoryModel){
        adapter.setListener(position -> {
            listener.historyOnClick(searchHistoryModel.get(position).getName());
        });

    }
    private ItemTouchHelper.SimpleCallback onSwipeLeft(SearchHistoryAdapter adapter){
        ItemTouchHelper.SimpleCallback itemTouchHelper= new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos =viewHolder.getAbsoluteAdapterPosition();
                int id = adapter.getHistoryList().get(pos).getId();
                viewModel.deleteHistory(id);
                adapter.notifyItemRemoved(pos);
            }

            @Override
            public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive){
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(ContextCompat.getColor(getContext(), R.color.hint_color))
                        .addActionIcon(R.drawable.ic_baseline_delete_24)
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        return itemTouchHelper;
    }}