package co.infinum.pokemon.database.interfaces;

import java.util.List;

import co.infinum.pokemon.models.Pokemon;

/**
 * Created by Željko Plesac on 25/05/16.
 */
public interface PokemonDAO {

    void insert(Pokemon pokemon);

    void insert(List<Pokemon> pokemon);

    int update(Pokemon pokemon);

    int update(List<Pokemon> pokemon);

    boolean delete(Pokemon pokemon);

    void delete(Pokemon pokemon, DatabaseActionListener listener);

    boolean delete(List<Pokemon> pokemons);

    void delete(List<Pokemon> pokemons, DatabaseActionListener listener);

    Pokemon getByName(String name);

    void getByName(String name, DatabaseModelListener<Pokemon> listener);

    List<Pokemon> getAll();

    void getAll(DatabaseModelListener<List<Pokemon>> listener);
}
