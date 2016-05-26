package co.infinum.pokemon.mvp.interactors.impl;

import javax.inject.Inject;

import co.infinum.pokemon.R;
import co.infinum.pokemon.models.Pokedex;
import co.infinum.pokemon.mvp.interactors.PokemonListInteractor;
import co.infinum.pokemon.mvp.listeners.PokemonListListener;
import co.infinum.pokemon.network.PokemonService;
import co.infinum.pokemon.utils.StringUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dino on 21/03/15.
 */
public class PokemonListInteractorImpl implements PokemonListInteractor, Callback<Pokedex> {

    private PokemonListListener pokemonListListener;

    private boolean isCanceled;

    private PokemonService pokemonService;

    @Inject
    public PokemonListInteractorImpl(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Override
    public void loadPokemonList(PokemonListListener pokemonListListener) {
        reset();
        this.pokemonListListener = pokemonListListener;
        pokemonService.getPokedex(this);
    }

    @Override
    public void cancel() {
        isCanceled = true;
    }

    @Override
    public void reset() {
        isCanceled = false;
    }

    @Override
    public void success(Pokedex pokedex, Response response) {
        if (!isCanceled) {
            pokemonListListener.onSuccess(pokedex);
        }
    }

    @Override
    public void failure(RetrofitError error) {
        if (!isCanceled) {
            pokemonListListener.onFailure(StringUtils.getString(R.string.error_network_problem));
        }
    }
}
