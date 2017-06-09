package br.com.solimar.desafiojogodavelha.util;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Solimar on 09/06/2017.
 */

public class MySharedPreferences {

    private static final String MY_PREFS_NAME = "PLACAR";

    public static void savePlacar(Context ctx, String key, Integer value) {
        SharedPreferences.Editor editor = ctx.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static Integer getPlacar(Context ctx, String key) {
        return ctx.getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).getInt(key, 0);
    }
}
