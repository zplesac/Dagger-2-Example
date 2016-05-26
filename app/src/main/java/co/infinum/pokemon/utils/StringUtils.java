package co.infinum.pokemon.utils;

import android.support.annotation.StringRes;

import co.infinum.pokemon.PokemonApp;

/**
 * Created by Željko Plesac on 26/05/16.
 */
public class StringUtils {

    private StringUtils() {

    }

    public static String getString(@StringRes int stringResId) {
        return PokemonApp.getInstance().getString(stringResId);
    }
}