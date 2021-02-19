package com.test.agilemusic.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.test.agilemusic.application.AgileMusicApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class UtilityManager {

    public static final String myPreferences = "com.test.agilemusic.LIKES";
    public static final String LIKES = "LIKES";

    public String getYearFromDate(String datetime) {

        if (datetime == null || datetime.isEmpty()) {
            return "";
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(convertStringToDate(datetime));

        return String.valueOf(cal.get(Calendar.YEAR));

    }

    public Date convertStringToDate(String dateString) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
        Date convertedDate = null;

        try {
            convertedDate = formatter.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return convertedDate;
    }


    public String currencyIsoToSymbol(String isoCode) {

        String currencySymbol = "";
        try {
            Locale.setDefault(Locale.UK);
            Currency currency = Currency.getInstance(isoCode);
            currencySymbol = currency.getSymbol();
        } catch (Exception ex) {
            return isoCode;
        }
        return currencySymbol;
    }


    public List<String> getSharedPreference(String preferenceName) {

        SharedPreferences sharedPreferences = AgileMusicApplication.getContext().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        Set<String> set = sharedPreferences.getStringSet(preferenceName, new HashSet<>());

        return new ArrayList<>(set);

    }

    public void setPreferences(String preferenceValue) {

        SharedPreferences sharedPreferences = AgileMusicApplication.getContext().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        List<String> existingSet;
        Set<String> set;
        existingSet = getSharedPreference(LIKES);

        if (existingSet != null)
            set = new HashSet<>(existingSet);
        else
            set = new HashSet<>();

        set.add(preferenceValue);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(LIKES, set);
        editor.apply();

    }

    public void removeSharedPreference(String preferenceValue) throws Exception {

        SharedPreferences sharedPreferences = AgileMusicApplication.getContext().getSharedPreferences(myPreferences, Context.MODE_PRIVATE);
        List<String> existingSet;
        Set<String> set;

        existingSet = getSharedPreference(LIKES);

        if (existingSet != null) {
            existingSet.remove(preferenceValue);
            set = new HashSet<>(existingSet);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putStringSet(LIKES, set);
            editor.apply();
        }

    }


}
