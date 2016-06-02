package co.infinum.pokemon.database;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.Model;

import android.support.annotation.Nullable;

import javax.inject.Inject;

import co.infinum.pokemon.PokemonApp;
import co.infinum.pokemon.database.interfaces.DatabaseWatcher;
import co.infinum.pokemon.models.Pokemon;
import timber.log.Timber;

/**
 * Created by Željko Plesac on 02/06/16.
 */
public class DBFlowWatcher implements DatabaseWatcher {

    private FlowContentObserver flowContentObserver;

    @Inject
    public DBFlowWatcher(FlowContentObserver flowContentObserver) {
        this.flowContentObserver = flowContentObserver;
    }

    @Override
    public void registerWatcher() {
        flowContentObserver.registerForContentChanges(PokemonApp.getInstance(), Pokemon.class);
        flowContentObserver.addOnTableChangedListener(tableChangedListener);
    }

    @Override
    public void unregisterWatcher() {
        flowContentObserver.unregisterForContentChanges(PokemonApp.getInstance());
    }

    private FlowContentObserver.OnTableChangedListener tableChangedListener = new FlowContentObserver.OnTableChangedListener() {
        @Override
        public void onTableChanged(@Nullable Class<? extends Model> tableChanged, BaseModel.Action action) {
            Timber.d("Action changed!");
        }
    };
}
