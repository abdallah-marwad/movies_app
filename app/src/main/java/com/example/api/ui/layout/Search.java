package com.example.api.ui.layout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.api.R;
import com.example.api.common.app.NetworkStatesReceiver;
import com.example.api.common.helper.LangUtils;
import com.example.api.common.helper.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class Search extends AppCompatActivity {
    private EditText editText;
    private SearchCategoriesFragment fragment1;
    private MoviesSearchQuery resultFragment;
    private SearchHistoryFragment historyFragment;
    private FragmentManager manager;
    private BottomNavigationView bottomNavigationView;
    int counter =0;
    NetworkStatesReceiver receiver =new NetworkStatesReceiver();
    NetworkStatesReceiver.MainReceiver mainReceiver;
    ImageView imageView;
    ImageView backBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLang();
        setContentView(R.layout.activity_search);

        editText = findViewById(R.id.search_edittext);
        imageView = findViewById(R.id.search_logout);
        backBtn = findViewById(R.id.login_back);
        bottomNavigationView = findViewById(R.id.search_bottom_navigation);
//        swipeRefreshLayout = findViewById(R.id.search_swipe);
        checkFragmentsVisibilityToRefresh();
        receiverCallBack();
        bottomNavigationItemSelected();
        showHistoryStates();
        searchClick();
        textChange();
        logout();
        backButtonOnClick();
    }
    private void checkLang(){
        if(Locale.getDefault().getDisplayLanguage() !="en"){
            LangUtils langUtils = new LangUtils();
            langUtils.setLocale(this , "en");
        }

    }

// back Button
    private void backButtonOnClick(){
        backBtn.setOnClickListener(view -> {
            finish();
        });
    }
    private void receiverCallBack(){
        mainReceiver = new NetworkStatesReceiver.MainReceiver() {
            @Override
            public void mainReceiver() {
                checkFragmentsVisibilityToRefresh();
            }
        };
        receiver.setMainReceiver(mainReceiver);
    }
    private void checkFragmentsVisibilityToRefresh() {
        if(fragment1 == null){
            intiFirstFrag();
        }
        else {
            if (fragment1.isVisible()) {
                fragment1.getAllGenres();
            }
        }
        if (resultFragment != null) {
            if (resultFragment.isVisible()) {
                resultFragment.getSearchQueryResult();
                resultFragment.checkList();
            }
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
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver , filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.peekAvailableContext().unregisterReceiver(receiver );
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

    private void bottomNavigationItemSelected() {
        bottomNavigationView.setSelectedItemId(R.id.item_search);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.item_home:
                    startActivity(new Intent(this.peekAvailableContext(), MainActivity.class));
                    break;
                case R.id.item_myList:
                    startActivity(new Intent(this.peekAvailableContext(), ActivityFavourite.class));
                    break;
            }
            return false;
        });
    }

    private void textChange() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (historyFragment != null && historyFragment.isAdded()) {
                    historyFragment.getSearchHistoryWithQuery(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (historyFragment != null && historyFragment.isAdded()) {
                    historyFragment.getSearchHistoryWithQuery(editable.toString());
                } else if (historyFragment != null && !historyFragment.isVisible()) {
                    historyFragment.getStart().observe(Search.this, aBoolean -> {
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        transaction2.replace(R.id.Search_fragmentContainer, historyFragment);
                        transaction2.commit();
                    });

                }

            }
        });
    }

    //Search Result Fra
    private void intiQueryFrag(String query) {
//        setBottomNavigationVisible();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction1 = manager.beginTransaction();
        resultFragment = MoviesSearchQuery.newInstance(query);
        transaction1.replace(R.id.Search_fragmentContainer, resultFragment);
//        transaction1.addToBackStack(null);    // Back Stack
        transaction1.commit();
        resultFragment.setQueryOnClick(id -> {
            Intent intent = new Intent(getBaseContext(), MainDetails.class);
            intent.putExtra(Utils.MOVIE_DETAILS_CODE, id);
            startActivity(intent);
        });
    }

    //First Frag
    private void intiFirstFrag() {
//        setBottomNavigationVisible();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        fragment1 = new SearchCategoriesFragment();
        transaction.replace(R.id.Search_fragmentContainer, fragment1);
        transaction.commit();
        fragment1.setFragOnClick(id ->
        {
            Intent intent = new Intent(this, MainDetails.class);
            intent.putExtra(Utils.MOVIE_DETAILS_CODE, id);
            startActivity(intent);
        });
    }

    // history fra
    private void intiHistoryFrag() {
//        bottomNavigationView.setVisibility(View.GONE);
        manager = getSupportFragmentManager();
        FragmentTransaction transaction2 = manager.beginTransaction();
        if (historyFragment == null) {
            historyFragment = new SearchHistoryFragment();
            transaction2.replace(R.id.Search_fragmentContainer, historyFragment);

        } else if (!historyFragment.isAdded()) {
//            bottomNavigationView.setVisibility(View.GONE);
            transaction2.replace(R.id.Search_fragmentContainer, historyFragment);

        }
//        transaction2.addToBackStack(null);    // Back Stack
        transaction2.commit();

        historyFragment.setListener(query1 -> {
            intiQueryFrag(query1);
            editText.setText(query1);
        });

    }

    private void showHistoryStates() {
        editText.setOnFocusChangeListener((view, b) -> {
            if (resultFragment == null) {
                intiHistoryFrag();
            }
        });
        editText.setOnClickListener((view) -> {
            intiHistoryFrag();
        });
    }

    private void searchClick() {
        editText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                intiQueryFrag(textView.getText().toString());
                if (!textView.equals("") || textView != null) {
                    if (historyFragment != null) {
                        historyFragment.insertHistory(textView.getText().toString());
                    }
                }
                return true;
            }
            return false;
        });
    }

    private void setBottomNavigationVisible() {
        bottomNavigationView.setVisibility(View.VISIBLE);
        bottomNavigationView.setSelectedItemId(R.id.item_search);
    }


    @Override
    public void onBackPressed() {
        FragmentTransaction transaction = manager.beginTransaction();
        if(historyFragment != null){
            if(historyFragment.isVisible()) {
                transaction.replace(R.id.Search_fragmentContainer , fragment1);
                transaction.commit();

            }
        }

        if(resultFragment!=null) {
            if(resultFragment.isVisible()) {
                transaction.replace(R.id.Search_fragmentContainer , fragment1);
                transaction.commit();
            }

        }if(fragment1!=null) {
            if(fragment1.isVisible()) {
               finish();
            }

        }


    }

    }
//        FragmentTransaction transaction = manager.beginTransaction();
//        if (historyFragment != null) {
//            if (historyFragment.isVisible()) {
//
//            }
//        }
//        if (resultFragment != null) {
//            resultFragment.getStart().observe(Search.this ,aBoolean -> {
//                manager.popBackStack();
//                historyFragment.getBack().observe(Search.this ,aBoolean1  -> {
//                    if(manager.getBackStackEntryCount() == 1){
//                        transaction.remove(historyFragment);
//                        transaction.replace(R.id.Search_fragmentContainer, fragment1);
//                        transaction.commit();
//                    }
//                    else {
//                        manager.popBackStack();
//                    }
//                });
//                });
//            }
//
//        }
//    }



