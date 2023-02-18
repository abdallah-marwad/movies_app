package com.example.api.ui.layout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.api.R;
import com.example.api.common.app.NetworkStatesReceiver;
import com.example.api.common.helper.LangUtils;
import com.example.api.common.helper.Utils;
import com.example.api.data.source.local.favorite.FavoriteModel;
import com.example.api.ui.viewmodel.DetailsViewModel;

import java.util.Locale;

public class MainDetails extends AppCompatActivity {
    private int idTv;
    private int idMovie;
    private Drawable drawable;
    private MoviesDetailsFragment moviesDetailsFragment;
    private TvDetailsFragment tvDetailsFragment;
    private DetailsViewModel detailsViewModel;
    private FavoriteModel favoriteModel;
    private boolean foundId = false;
    private boolean connected = false;
    private NetworkStatesReceiver receiver = new NetworkStatesReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_details);
        checkLang();
        backBtn();
        checkIntentData(getIntent());


    }
    private void checkLang(){
        if(Locale.getDefault().getDisplayLanguage() !="en"){
            LangUtils langUtils = new LangUtils();
            langUtils.setLocale(this , "en");
        }

    }

    private void backBtn() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite, menu);
        drawable = menu.getItem(0).getIcon();
        checkMovieFavourite();
        return super.onCreateOptionsMenu(menu);
    }

    private void insertToFavourite() {
        if (MainActivity.USER_ID == null) {
            detailsViewModel.insertFavourite(favoriteModel);
        } else {
            saveToFireBase();
        }
        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        drawable.mutate();
        drawable.setColorFilter(getResources().getColor(R.color.light_red), PorterDuff.Mode.SRC_IN);
        foundId = true;

    }

    private void checkExtraCode() {
        if (getIntent().hasExtra(Utils.MOVIE_DETAILS_CODE)) {
            detailsViewModel = moviesDetailsFragment.getViewModel();
            detailsViewModel.intiRepository(getApplication());
        } else {
            detailsViewModel = tvDetailsFragment.getViewModel();
            detailsViewModel.intiRepository(getApplication());
        }
        if (MainActivity.USER_ID != null) {
            checkIfMovieIdInFirebase();
        }

    }

    //region firebase
    private void saveToFireBase() {
        detailsViewModel.saveMovieInFirebase(favoriteModel);
    }

    private void checkIfMovieIdInFirebase() {
        detailsViewModel.checkIfMovieIdInFirebase(favoriteModel);
    }

    //endregion
    private void checkMovieFavourite() {
        checkExtraCode();
        if (MainActivity.USER_ID == null) {
            Utils.executor.execute(() -> {
                detailsViewModel.getAllFavouritesIds().forEach(integer -> {
                    if (integer == favoriteModel.getMovieId()) {
                        foundId = true;
                        this.runOnUiThread(() -> {
                            fillFavoriteButton();
                        });
                    }
                });
            });
        } else {
            detailsViewModel.getUserFounded().observe(this, aBoolean -> {
                foundId = aBoolean;
                if (foundId) {
                fillFavoriteButton();}
            });
        }

    }

    private void fillFavoriteButton(){
        drawable.mutate();
        drawable.setColorFilter(getResources().getColor(R.color.light_red), PorterDuff.Mode.SRC_IN);

    }
    private void checkItemAdded() {
        if (foundId) {
            Toast.makeText(this, "Already Added", Toast.LENGTH_SHORT).show();
        } else {
            insertToFavourite();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.favorite_item:
                checkItemAdded();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    private boolean setReceiver() {
        receiver.setMainReceiver(() -> {
            checkIntentData(getIntent());

        });
        return connected;
    }

    private void checkIntentData(Intent intent) {
        if (intent.hasExtra(Utils.MOVIE_DETAILS_CODE)) {
            favoriteModel = (FavoriteModel) getIntent().getSerializableExtra(Utils.MOVIE_DETAILS_CODE);
            moviesDetailsFragment = MoviesDetailsFragment.newInstance(favoriteModel.getMovieId());
            createFragment(R.id.details_fragmentContainer, moviesDetailsFragment);

        } else {
            favoriteModel = (FavoriteModel) getIntent().getSerializableExtra(Utils.TV_DETAILS_CODE);
            tvDetailsFragment = TvDetailsFragment.newInstance(favoriteModel.getMovieId());
            createFragment(R.id.details_fragmentContainer, tvDetailsFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.peekAvailableContext().registerReceiver(receiver, filter);
//        Toast.makeText(this, "resume Activity", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.peekAvailableContext().unregisterReceiver(receiver);

    }

    private void createFragment(int resource, Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(resource, fragment);
        fragmentTransaction.commit();
    }


}