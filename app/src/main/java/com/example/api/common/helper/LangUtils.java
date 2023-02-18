package com.example.api.common.helper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.view.ContextThemeWrapper;

import java.util.Locale;

public class LangUtils {

        public  void setLocale(Activity  activity , String lang) {
            Locale locale = new Locale(lang);
            locale.setDefault(locale);
            Resources resources = activity.getResources();
            Configuration configuration = resources.getConfiguration();
            configuration.setLocale(locale);
            resources.updateConfiguration(configuration , resources.getDisplayMetrics());
        }



    }

