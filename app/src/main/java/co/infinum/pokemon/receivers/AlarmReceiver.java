package co.infinum.pokemon.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import co.infinum.pokemon.PokemonApp;
import co.infinum.pokemon.dagger.modules.AlarmModule;
import co.infinum.pokemon.database.interfaces.PokemonDAO;

/**
 * Created by Željko Plesac on 02/06/16.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Inject
    PokemonDAO pokemonDAO;

    public AlarmReceiver() {
        PokemonApp.getInstance().getApplicationComponent().plus(new AlarmModule()).inject(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        // Delete content of Pokemon table
        pokemonDAO.deleteAll();
    }
}
