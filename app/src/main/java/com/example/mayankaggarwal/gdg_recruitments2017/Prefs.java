package com.example.mayankaggarwal.gdg_recruitments2017;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by mayankaggarwal on 27/01/17.
 */

public class Prefs {

    public static void setPreLoad(Boolean value, Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("PreLoad", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("PreLoad", value);
        editor.apply();
    }

    public static Boolean getPreLoad(Context context){
        SharedPreferences sharedpreferences = context.getSharedPreferences("PreLoad", Context.MODE_PRIVATE);
        return sharedpreferences.getBoolean("PreLoad", false);
    }


}
