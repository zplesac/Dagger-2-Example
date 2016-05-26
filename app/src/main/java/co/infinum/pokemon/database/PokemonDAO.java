package co.infinum.pokemon.database;

import java.util.List;

import co.infinum.pokemon.models.Pokemon;

/**
 * Created by Željko Plesac on 25/05/16.
 */
public interface PokemonDAO {

    boolean insert(Pokemon pokemon);

    void insert(Pokemon pokemon, DatabaseActionListener listener);

    boolean insert(List<Pokemon> pokemon);

    void insert(List<Pokemon> pokemon, DatabaseActionListener listener);

    int update(Pokemon pokemon);

    void update(Pokemon pokemon, DatabaseActionListener listener);

    int update(List<Pokemon> pokemon);

    void update(List<Pokemon> pokemon, DatabaseActionListener listener);

    boolean delete(Pokemon pokemon);

    void delete(Pokemon pokemon, DatabaseActionListener listener);

    boolean delete(List<Pokemon> pokemons);

    void delete(List<Pokemon> pokemons, DatabaseActionListener listener);

    Pokemon getByName(String name);

    void getByName(String name, DatabaseModelListener<Pokemon> listener);

    List<Pokemon> getAll();

    void getAll(DatabaseModelListener<List<Pokemon>> listener);

    void deleteAll();
}
