package co.infinum.pokemon.database;

import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import co.infinum.pokemon.database.interfaces.DatabaseModelListener;
import co.infinum.pokemon.database.interfaces.PokemonDAO;
import co.infinum.pokemon.models.Pokemon;
import co.infinum.pokemon.models.Pokemon_Table;
import timber.log.Timber;

/**
 * Created by Željko Plesac on 25/05/16.
 */
public class DBFlowPokemonDAO implements PokemonDAO {

    @Override
    public void insert(Pokemon pokemon) {

    }

    @Override
    public void insert(List<Pokemon> pokemon) {

    }

    @Override
    public void update(Pokemon pokemon) {

    }

    @Override
    public void update(List<Pokemon> pokemon) {

    }

    @Override
    public void delete(Pokemon pokemon) {
        try{
            pokemon.delete();
        }
        catch (Exception e){
            Timber.e(e, "Exception occurred while deleting Pokemon!");
        }
    }

    @Override
    public void delete(List<Pokemon> pokemons) {
        try {
            for(Pokemon pokemon : pokemons){
                delete(pokemon);
            }
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while deleting Pokemons!");
        }
    }

    @Override
    @Nullable
    public Pokemon getByName(String name) {
        try {
            return SQLite.select().from(Pokemon.class).where(Pokemon_Table.Name.eq(name)).querySingle();
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while fetching pokemon by it's name");
            return null;
        }
    }

    @Override
    public void getByName(String name, final DatabaseModelListener<Pokemon> listener) {
        SQLite.select().from(Pokemon.class).where(Pokemon_Table.Name.eq(name)).async().queryResultCallback(
                new QueryTransaction.QueryResultCallback<Pokemon>() {
                    @Override
                    public void onQueryResult(QueryTransaction transaction, @NonNull CursorResult<Pokemon> tResult) {
                        Pokemon pokemon = tResult.toModelClose();
                        if (pokemon == null) {
                            listener.onNoModelFound();
                        } else {
                            listener.onModelLoaded(pokemon);
                        }
                    }
                }).execute();
    }

    @Override
    public List<Pokemon> getAll() {
        try {
            return SQLite.select().from(Pokemon.class).queryList();
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while fetching list of Pokemons");
            return new ArrayList<>();
        }
    }

    @Override
    public void getAll(final DatabaseModelListener<List<Pokemon>> listener) {
        SQLite.select().from(Pokemon.class).async().queryResultCallback(new QueryTransaction.QueryResultCallback<Pokemon>() {
            @Override
            public void onQueryResult(QueryTransaction transaction, @NonNull CursorResult<Pokemon> tResult) {
                List<Pokemon> results = tResult.toListClose();

                if (results == null || results.isEmpty()) {
                    listener.onNoModelFound();
                } else {
                    listener.onModelLoaded(results);
                }
            }
        }).execute();
    }
}
