package co.infinum.pokemon.database.interfaces;

import java.util.List;

import co.infinum.pokemon.models.Pokemon;

/**
 * Created by Željko Plesac on 25/05/16.
 */
public interface PokemonDAO {

    void insert(Pokemon pokemon);

    void insert(List<Pokemon> pokemon);

    void update(Pokemon pokemon);

    void update(List<Pokemon> pokemon);

    void delete(Pokemon pokemon);

    void delete(List<Pokemon> pokemons);

    Pokemon getByName(String name);

    void getByName(String name, DatabaseModelListener<Pokemon> listener);

    List<Pokemon> getAll();

    void getAll(DatabaseModelListener<Pokemon> listener);
}
