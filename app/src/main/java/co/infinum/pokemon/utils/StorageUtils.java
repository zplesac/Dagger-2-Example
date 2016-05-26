package co.infinum.pokemon.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.util.Date;

import co.infinum.pokemon.PokemonApp;

/**
 * Created by Željko Plesac on 26/05/16.
 */
public class StorageUtils {

    public static final long DEFAULT_LONG_VALUE = 0;

    private static final String CACHE_REFRESH_TIME = "CACHE_REFRESH_TIME";

    private StorageUtils() {

    }

    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(PokemonApp.getInstance());
    }

    public static void setLastCacheRefreshTime(Date date) {
        getPreferences().edit().putLong(CACHE_REFRESH_TIME, date.getTime()).apply();
    }

    @Nullable
    public static Date getLastCacheRefreshTime() {
        long value = getPreferences().getLong(CACHE_REFRESH_TIME, DEFAULT_LONG_VALUE);

        if (value != 0) {
            return new Date(value);
        } else {
            return null;
        }
    }
}
