package co.infinum.pokemon.dagger.modules;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;

import javax.inject.Singleton;

import co.infinum.pokemon.database.DBFlowWatcher;
import co.infinum.pokemon.database.interfaces.DatabaseWatcher;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Željko Plesac on 02/06/16.
 */
@Module
public class DatabaseWatcherModule {

    @Provides
    @Singleton
    public DatabaseWatcher provideWatcher(DBFlowWatcher watcher) {
        return watcher;
    }

    @Provides
    @Singleton
    public FlowContentObserver provideObserver() {
        FlowContentObserver flowContentObserver = new FlowContentObserver();
        flowContentObserver.setNotifyAllUris(false);
        return flowContentObserver;
    }
}


