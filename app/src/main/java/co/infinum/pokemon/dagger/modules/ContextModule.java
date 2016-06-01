package co.infinum.pokemon.dagger.modules;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Željko Plesac on 01/06/16.
 */
@Module
public class ContextModule {

    private Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    public Context provideContext() {
        return context;
    }
}
