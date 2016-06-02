package co.infinum.pokemon.mvp.presenters.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import co.infinum.pokemon.R;
import co.infinum.pokemon.database.interfaces.DatabaseActionListener;
import co.infinum.pokemon.database.interfaces.DatabaseModelListener;
import co.infinum.pokemon.database.interfaces.PokemonDAO;
import co.infinum.pokemon.models.Pokedex;
import co.infinum.pokemon.models.Pokemon;
import co.infinum.pokemon.mvp.interactors.PokemonListInteractor;
import co.infinum.pokemon.mvp.listeners.PokemonListListener;
import co.infinum.pokemon.mvp.presenters.PokemonListPresenter;
import co.infinum.pokemon.mvp.views.PokemonListView;
import co.infinum.pokemon.utils.AlarmUtils;
import co.infinum.pokemon.utils.StorageUtils;
import co.infinum.pokemon.utils.StringUtils;

/**
 * Created by dino on 21/03/15.
 */
public class PokemonListPresenterImpl implements PokemonListPresenter, PokemonListListener {

    private final PokemonListView pokemonListView;

    private final PokemonListInteractor pokemonListInteractor;

    private final PokemonDAO pokemonDAO;

    private boolean isCancelled = false;

    @Inject
    public PokemonListPresenterImpl(PokemonListView pokemonListView, PokemonListInteractor pokemonListInteractor, PokemonDAO pokemonDAO) {
        this.pokemonListView = pokemonListView;
        this.pokemonListInteractor = pokemonListInteractor;
        this.pokemonDAO = pokemonDAO;
    }

    @Override
    public void loadPokemonList() {
        pokemonListView.showProgress();

        // check if Pokemons are cached
        Date lastCacheDate = StorageUtils.getLastCacheRefreshTime();

        if (lastCacheDate == null) {
            pokemonListInteractor.loadPokemonList(this);
        } else {
            pokemonDAO.getAll(new DatabaseModelListener<List<Pokemon>>() {
                @Override
                public void onModelLoaded(List<Pokemon> model) {
                    if (!isCancelled) {
                        pokemonListView.showPokemons(model);
                        pokemonListView.hideProgress();
                        pokemonListView.showMessage(StringUtils.getString(R.string.caching_fetched_from_database));
                    }
                }

                @Override
                public void onNoModelFound() {
                    if (!isCancelled) {
                        pokemonListView.showPokemons(new ArrayList<Pokemon>());
                        pokemonListView.hideProgress();
                    }
                }
            });
        }

        // schedule database reset after one minute
        AlarmUtils.scheduleDatabaseReset();
    }

    @Override
    public void refreshPokemonList() {
        // Load directly from the web!
        pokemonListView.showProgress();
        pokemonListInteractor.loadPokemonList(this);
    }

    @Override
    public void onPokemonSelected(Pokemon pokemon) {
        pokemonListView.showPokemonDetails(pokemon);
    }

    @Override
    public void cancel() {
        pokemonListView.hideProgress();
        pokemonListInteractor.cancel();
        isCancelled = true;
    }

    @Override
    public void onSuccess(Pokedex pokedex) {
        pokemonListView.hideProgress();
        pokemonListView.showPokemons(pokedex.getPokemons());
        pokemonListView.showMessage(StringUtils.getString(R.string.caching_fetched_from_network));

        // cache all Pokemons in database
        pokemonDAO.deleteAll();
        pokemonDAO.insert(pokedex.getPokemons(), new DatabaseActionListener() {
            @Override
            public void onSuccess() {
                if (!isCancelled) {
                    StorageUtils.setLastCacheRefreshTime(new Date());
                    pokemonListView.showMessage(StringUtils.getString(R.string.caching_success));
                }
            }

            @Override
            public void onError(Throwable e) {
                if (!isCancelled) {
                    pokemonListView.showError(e.getMessage());
                }
            }
        });
    }

    @Override
    public void onFailure(String message) {
        pokemonListView.hideProgress();
        pokemonListView.showError(message);
    }
}
