package co.infinum.pokemon.mvp.presenters.impl;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.SQLCondition;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import co.infinum.pokemon.database.interfaces.DatabaseModelListener;
import co.infinum.pokemon.database.interfaces.PokemonDAO;
import co.infinum.pokemon.models.Pokemon;
import co.infinum.pokemon.mvp.interactors.PokemonDetailsInteractor;
import co.infinum.pokemon.mvp.listeners.PokemonDetailsListener;
import co.infinum.pokemon.mvp.presenters.PokemonDetailsPresenter;
import co.infinum.pokemon.mvp.views.PokemonDetailsView;
import timber.log.Timber;

/**
 * Created by dino on 21/03/15.
 */
public class PokemonDetailsPresenterImpl implements PokemonDetailsPresenter, PokemonDetailsListener {

    private final PokemonDetailsView pokemonDetailsView;

    private final PokemonDetailsInteractor pokemonDetailsInteractor;

    private PokemonDAO pokemonDAO;

    private FlowContentObserver flowContentObserver;

    private Pokemon pokemon;

    private Context context;

    @Inject
    public PokemonDetailsPresenterImpl(PokemonDetailsView pokemonDetailsView, PokemonDetailsInteractor pokemonDetailsInteractor,
            PokemonDAO pokemonDAO, Context context) {
        this.pokemonDetailsView = pokemonDetailsView;
        this.pokemonDetailsInteractor = pokemonDetailsInteractor;
        this.pokemonDAO = pokemonDAO;
        this.context = context;
        this.flowContentObserver = new FlowContentObserver();
    }

    @Override
    public void loadDetails(Pokemon pokemon) {
        this.pokemon = pokemon;
        showData(pokemon);

        flowContentObserver.registerForContentChanges(context, Pokemon.class);
        flowContentObserver.addModelChangeListener(modelStateChangedListener);

        pokemonDetailsInteractor.loadPokemonDetails(pokemon.getResourceUri(), this);
    }

    @Override
    public void cancel() {
        pokemonDetailsView.hideProgress();
        pokemonDetailsInteractor.cancel();
        flowContentObserver.unregisterForContentChanges(context);
    }

    @Override
    public void onSuccess(Pokemon updatedPokemon) {
        // APIs are not to be trusted so we wrap interaction with data from API in try/catch
        try {
            pokemonDAO.update(pokemon, updatedPokemon.getResourceUri(), updatedPokemon.getHp(), 300,
                    updatedPokemon.getDefense(),
                    updatedPokemon.getHeight(), updatedPokemon.getWeight());
        } catch (Exception e) {
            e.printStackTrace();
            pokemonDetailsView.showError("Unknown error while using data from API!");
        } finally {
            pokemonDetailsView.hideProgress();
        }
    }

    @Override
    public void onFailure(String message) {
        pokemonDetailsView.hideProgress();
        pokemonDetailsView.showError(message);
    }

    private FlowContentObserver.OnModelStateChangedListener modelStateChangedListener
            = new FlowContentObserver.OnModelStateChangedListener() {
        @Override
        public void onModelStateChanged(@Nullable Class<? extends Model> table, BaseModel.Action action,
                @NonNull SQLCondition[] primaryKeyValues) {

            Timber.d("Pokemon has changed it state, action is:" + action.name());

            if (primaryKeyValues != null) {
                pokemonDAO.getByName(primaryKeyValues[0].value().toString(), new DatabaseModelListener<Pokemon>() {
                    @Override
                    public void onModelLoaded(Pokemon model) {
                        pokemon = model;
                        showData(pokemon);
                        pokemonDetailsView.showMessage("Model has been updated!");
                    }

                    @Override
                    public void onNoModelFound() {

                    }
                });
            }
        }
    };

    private void showData(Pokemon pokemon) {
        // APIs are not to be trusted so we wrap interaction with data from API in try/catch
        try {
            pokemonDetailsView.showName(pokemon.getName());
            pokemonDetailsView.showHp(String.valueOf(pokemon.getHp()));
            pokemonDetailsView.showWeight(pokemon.getWeight());
            pokemonDetailsView.showHeight(pokemon.getHeight());
            pokemonDetailsView.showAttack(String.valueOf(pokemon.getAttack()));
            pokemonDetailsView.showDefense(String.valueOf(pokemon.getDefense()));
        } catch (Exception e) {
            e.printStackTrace();
            pokemonDetailsView.showError("Unknown error while using data from API!");
        } finally {
            pokemonDetailsView.hideProgress();
        }
    }
}
