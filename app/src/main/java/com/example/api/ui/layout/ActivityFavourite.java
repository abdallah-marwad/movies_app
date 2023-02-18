package com.example.api.ui.layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.api.R;
import com.example.api.common.app.NetworkStatesReceiver;
import com.example.api.common.helper.LangUtils;
import com.example.api.common.helper.Utils;
import com.example.api.data.adapters.FavouriteAdapter;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.ui.viewmodel.FavouriteViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Locale;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class ActivityFavourite extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BottomNavigationView bottomNavigationView;
    private FavouriteAdapter favouriteAdapter;
    private FavouriteAdapter.OnClickFavourite onClickFavourite;
    private FavouriteViewModel viewModel;
    NetworkStatesReceiver receiver =new NetworkStatesReceiver();
    NetworkStatesReceiver.MainReceiver mainReceiver;
    TextView  textView;
    ImageView backImg;
    ImageView logoutImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLang();
        setContentView(R.layout.activity_favourite);
        recyclerView = findViewById(R.id.favorite_rv);
        textView = findViewById(R.id.favorite_txt);
        backImg = findViewById(R.id.favorite_back);
        logoutImg = findViewById(R.id.favorite_logout);
        bottomNavigationView = findViewById(R.id.favorite_bottom_navigation);
        viewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        getAllFavourite();
        rvDeclaration();
        backButtonOnClick();
        receiverCallBack();
        bottomNavigationItemSelected();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(onSwipeLeft(favouriteAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        logout();
    }

    private void checkLang(){
        if(Locale.getDefault().getDisplayLanguage() !="en"){
            LangUtils langUtils = new LangUtils();
            langUtils.setLocale(this , "en");
        }

    }
    // region Logout
    private void logout(){
        logoutImg.setOnClickListener(view -> {
            showDialogToLogout();
        });
    }
    private void showDialogToLogout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure to logout ?");
        builder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialogInterface, final int i) {

                        logoutState();
                        toLogin();
                    }
                });
        builder.setNegativeButton("No ", null);
        builder.create();
        builder.show();
    }

    private void logoutState(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed = sharedPreferences.edit();
        FirebaseAuth auth =  FirebaseAuth.getInstance();
        auth.signOut();
        ed.putBoolean(LoginActivity.REGISTER_STATE , false);
        ed.commit();
    }
    private void toLogin(){
        Intent intent = new Intent(this , LoginActivity.class);
        startActivity(intent);
    }
    //endregion
    private void backButtonOnClick(){
        backImg.setOnClickListener(view -> {
            finish();
        });
    }
    private void rvDeclaration(){
        favouriteAdapter = new FavouriteAdapter();
        recyclerView.setAdapter(favouriteAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private boolean authenticated(){
        if(MainActivity.USER_ID == null){
            return false;
        }else {
            return true;
        }
    }
    private void checkListFavouriteIfNotNull(List<FavoriteModel>favoriteModels ){
        if (favoriteModels.size() > 0) {
            textView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            favouriteAdapter.setFavoriteModels(favoriteModels);
        } else {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);

        }
    }
    private void getAllFavourite(){
        if(authenticated()){
            viewModel.getFirebaseMovies();
            viewModel.getUserData().observe(this , favoriteModels -> {
                checkListFavouriteIfNotNull(favoriteModels);
                adapterClick();
            });
        }else {
            viewModel.intiRepository(getApplication());
            viewModel.getAllFavourites().observe(this, favoriteModels -> {
                checkListFavouriteIfNotNull(favoriteModels);
                adapterClick();
            });
        }
    }
//    private void getAllFavouriteList(){
//        viewModel.intiRepository(getApplication());
//        Utils.executor.execute(() ->{
//        List<FavoriteModel> favoriteModels = viewModel.getAllFavouritesList();
//            if(favoriteModels.size() <=0 ) {
//                this.runOnUiThread(() -> {textView.setVisibility(View.GONE);
//                favouriteAdapter.setFavoriteModels(favoriteModels);
//                });
//            }
//            else {
//                this.runOnUiThread(() -> textView.setVisibility(View.VISIBLE));
//            }
//            adapterClick();
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.peekAvailableContext().registerReceiver(receiver , filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.peekAvailableContext().unregisterReceiver(receiver );
    }

    private void bottomNavigationItemSelected(){
        bottomNavigationView.setSelectedItemId(R.id.item_myList);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.item_home:
                    startActivity(new Intent(this.peekAvailableContext() , MainActivity.class));
                    break;
                case R.id.item_search:
                    startActivity(new Intent(this.peekAvailableContext() , Search.class));
                    break;
            }
            return false;
        });
    }

private void receiverCallBack(){
    mainReceiver = new NetworkStatesReceiver.MainReceiver() {
        @Override
        public void mainReceiver() {
            getAllFavourite();
        }
    };
    receiver.setMainReceiver(mainReceiver);
}
    private void adapterClick(){
        favouriteAdapter.setListener(favoriteModel -> {
            Intent intent = new Intent(getBaseContext() , MainDetails.class);
            intent.putExtra(Utils.MOVIE_DETAILS_CODE , favoriteModel);
            startActivity(intent);
        });
    }
        private ItemTouchHelper.SimpleCallback onSwipeLeft(FavouriteAdapter adapter) {
            ItemTouchHelper.SimpleCallback itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                    int pos = viewHolder.getAbsoluteAdapterPosition();
                    int id = adapter.getFavoriteModels().get(pos).getMovieId();
                    if(authenticated()){
                        viewModel.deleteFirebaseMovies(new FavoriteModel(id));
                        adapter.notifyItemRemoved(pos);
                    }
                    else {viewModel.deleteFavourite(new FavoriteModel(id));
                        adapter.notifyItemRemoved(pos);}



                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addActionIcon(R.drawable.ic_baseline_delete_24)
                            .create()
                            .decorate();

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            };
            return itemTouchHelper;
        }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                return true;
//
//    }
//        return super.onOptionsItemSelected(item);    }
}

