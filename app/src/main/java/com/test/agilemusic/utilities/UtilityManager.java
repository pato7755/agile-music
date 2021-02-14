package com.test.agilemusic.utilities;

import org.w3c.dom.CDATASection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class UtilityManager {

    public String getYearFromDate(String datetime) {

        Calendar cal = Calendar.getInstance();
        cal.setTime(convertStringToDate(datetime));

        return String.valueOf(cal.get(Calendar.YEAR));

    }

    public Date convertStringToDate(String dateString){

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
        } catch (Exception ex){
            return isoCode;
        }
        return currencySymbol;
    }


}
