package co.infinum.pokemon.database;

import java.util.List;

import co.infinum.pokemon.database.interfaces.DatabaseModelListener;
import co.infinum.pokemon.database.interfaces.PokemonDAO;
import co.infinum.pokemon.models.Pokemon;

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
    public Pokemon getByName(String name, DatabaseModelListener<Pokemon> listener) {
        return null;
    }

    @Override
    public List<Pokemon> getAll() {
        return null;
    }

    @Override
    public List<Pokemon> getAll(DatabaseModelListener<Pokemon> listener) {
        return null;
    }
}
