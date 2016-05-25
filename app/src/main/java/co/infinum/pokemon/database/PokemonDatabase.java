package co.infinum.pokemon.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Željko Plesac on 25/05/16.
 */
@Database(name = PokemonDatabase.NAME, version = PokemonDatabase.VERSION)
public class PokemonDatabase {

    public static final String NAME = "Pokemon";

    public static final int VERSION = 1;
}
