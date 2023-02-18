package com.example.api.ui.layout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.api.R;
import com.example.api.common.app.NetworkStatesReceiver;
import com.example.api.common.helper.LangUtils;
import com.example.api.data.adapters.PagingMovieAdapter;

import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.databinding.ActivityMainBinding;
import com.example.api.common.helper.Utils;
import com.example.api.models.DataSourceTopMovies;
import com.example.api.ui.viewmodel.MainViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MoviesFragment.FragmentItemOnClick , TvShowFragment.FragmentItemOnClick , BottomNavigationView.OnNavigationItemSelectedListener{
    public MainActivity() {

    }
    public static boolean logout ;
    public static String USER_ID ;

    MainViewModel viewModel;
    public PagingMovieAdapter movieAdapter;
    TabLayout tabLayout;
    int counter =0;
    boolean refresh =false;
    BroadcastReceiver connectionReceiver;
    NetworkStatesReceiver receiver =new NetworkStatesReceiver();
    NetworkStatesReceiver.MainReceiver mainReceiver;
    TvShowFragment tvShowFragment ;
    MoviesFragment moviesFragment ;
    FragmentManager manager;
    DataSourceTopMovies topMoviesDataSource;
    BottomNavigationView bottomNavigationView;
    SwipeRefreshLayout swipeRefreshLayout;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLang();
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.main_tabLayout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        imageView = findViewById(R.id.main_logout);

        addTabs();
        tabSelected();
        declareFrags();
        checkFragmentsVisibality(moviesFragment, tvShowFragment);
        getApiDataToRefresh();
        reciverCallBack();
        logout();
        bottomNavigationView.setOnNavigationItemSelectedListener(this);


    }
    private void checkLang(){
        if(Locale.getDefault().getDisplayLanguage() !="en"){
            LangUtils langUtils = new LangUtils();
            langUtils.setLocale(this , "en");
        }

    }
    // region Logout
    private void logout(){
        imageView.setOnClickListener(view -> {
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
                        logout = false;
                        Log.d("test" , String.valueOf(logout)+"logout");

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
    private void getApiDataToRefresh(){
        if(tabLayout.getSelectedTabPosition() ==0){
            if(moviesFragment != null){
                if(moviesFragment.getViewModel() != null) {
                    moviesFragment.getTopMovieData();
                    moviesFragment.getAllMovieData();
                    moviesFragment.checkList();
                }
            }
        }
        else {
            if(tvShowFragment != null){
                tvShowFragment.getAllTvData();
                tvShowFragment.getTopTvData();
                tvShowFragment.checkList();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        logout = true;

        Log.d("test" , String.valueOf(logout)+"resume");

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.peekAvailableContext().registerReceiver(receiver , filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.peekAvailableContext().unregisterReceiver(receiver );

    }

    @Override
    protected void onStop() {
        super.onStop();
        logout = false;
        Log.d("test" , String.valueOf(logout)+"stop");


    }

    private void addTabs(){
        tabLayout.addTab(tabLayout.newTab().setText("Movies"));
        tabLayout.addTab(tabLayout.newTab().setText("TV Shows"));
    }

    private void declareFrags(){
        moviesFragment = new MoviesFragment();
        tvShowFragment = new TvShowFragment();
        manager = getSupportFragmentManager();
    }
    private void reciverCallBack(){
        mainReceiver = new NetworkStatesReceiver.MainReceiver() {
            @Override
            public void mainReceiver() {
                checkFragmentsVisibality(moviesFragment, tvShowFragment);
                getApiDataToRefresh();

            }
        };
        receiver.setMainReceiver(mainReceiver);
    }
    private void checkFragmentsVisibality(Fragment moviesFrag , Fragment tvFrag){
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        if(tabLayout.getSelectedTabPosition() ==0){
           if(moviesFrag.isAdded() && moviesFrag.isHidden()){
               if(tvFrag.isAdded() && tvFrag.isVisible()){
                   fragmentTransaction.hide(tvFrag);

            }
               fragmentTransaction.show(moviesFrag);
           }
           else if(!moviesFrag.isAdded())
           {fragmentTransaction.replace(R.id.main_fragmentContainer ,moviesFrag);
           }
        }
        else if (tabLayout.getSelectedTabPosition() ==1) {
            if(tvFrag.isAdded() && tvFrag.isHidden()){
                if(moviesFrag.isAdded()&& moviesFrag.isVisible()){
                    fragmentTransaction.hide(moviesFrag);

                }
                fragmentTransaction.show(tvFrag);
            }
            else if(!tvFrag.isAdded()){
                if(moviesFrag.isAdded()){
                    fragmentTransaction.hide(moviesFrag);
                }
                    fragmentTransaction.add(R.id.main_fragmentContainer, tvFrag);
            }
        }
        fragmentTransaction.commit();
    }

//    private void mainNetworkState(){
//        if(tabLayout.getSelectedTabPosition() ==0){
//            fragmentMovieDeclaration();
//        }
//        else {
//            fragmentTvDeclaration();
//        }
//    }



//        private void networkChangeReciver(){
//        connectionReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (intent != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
//                    if (checkInternetConnection(context , intent)) {
//                        if(tabLayout.getSelectedTabPosition() ==0){
//                            fragmentMovieDeclaration();
//                        }
//                        else {
//                            fragmentTvDeclaration();
//                        }
//                    } else {
//                        showDialog();
//                    }
//                }
//            }
//        };
//    }
    private void tabSelected(){
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                checkFragmentsVisibality(moviesFragment, tvShowFragment);

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private boolean checkInternetConnection(Context context , Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info != null){
            if(info.isConnected()){
                return true;
            }
        }
        return false;
    }
    // Movie on click
    @Override
    public void onClick(FavoriteModel favoriteModel) {
        movieAdapterOnClick(favoriteModel , this.peekAvailableContext());
    }
    private void movieAdapterOnClick(FavoriteModel favoriteModel, Context context){
                    Intent intent = new Intent(context , MainDetails.class);
                    intent.putExtra(Utils.MOVIE_DETAILS_CODE , favoriteModel);
                    startActivity(intent);

    }
    // Tv on click
    @Override
    public void onClickTV(FavoriteModel favoriteModel) {
        tvAdapterOnClick(favoriteModel , this.peekAvailableContext());

    }
    private void tvAdapterOnClick(FavoriteModel favoriteModel, Context context){
        Intent intent = new Intent(context , MainDetails.class);
        intent.putExtra(Utils.TV_DETAILS_CODE ,favoriteModel);
        startActivity(intent);

    }
    
    // region nav
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_search:
                startActivity(new Intent(this.peekAvailableContext() , Search.class));
                break;
            case R.id.item_myList:
                startActivity(new Intent(this.peekAvailableContext() , ActivityFavourite.class));
                break;
        }
        return false;
    }
    //endregion

}