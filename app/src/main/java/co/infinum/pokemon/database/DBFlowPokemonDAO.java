package co.infinum.pokemon.database;

import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import co.infinum.pokemon.database.interfaces.DatabaseModelListener;
import co.infinum.pokemon.database.interfaces.PokemonDAO;
import co.infinum.pokemon.models.Pokemon;
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

    }

    @Override
    public void delete(List<Pokemon> pokemons) {

    }

    @Override
    public Pokemon getByName(String name) {
        return null;
    }

    @Override
    public void getByName(String name, DatabaseModelListener<Pokemon> listener) {
        return null;
    }

    @Override
    public List<Pokemon> getAll() {
        try {
            return SQLite.select().from(Pokemon.class).queryList();
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while fetching list of pokemons");
            return new ArrayList<>();
        }
    }

    @Override
    public void getAll(final DatabaseModelListener<Pokemon> listener) {
        SQLite.select().from(Pokemon.class).async().queryResultCallback(new QueryTransaction.QueryResultCallback<Pokemon>() {
            @Override
            public void onQueryResult(QueryTransaction transaction, @NonNull CursorResult<Pokemon> tResult) {
                List<Pokemon> results = tResult.toListClose();

                if(results == null || results.isEmpty()){
                    listener.onNoModelFound();
                }
                else{
                    listener.onModelLoaded();
                }
            }
        }).execute();

    }
}
