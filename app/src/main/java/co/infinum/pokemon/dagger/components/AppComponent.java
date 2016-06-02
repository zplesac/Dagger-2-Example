package co.infinum.pokemon.dagger.components;

import javax.inject.Singleton;

import co.infinum.pokemon.PokemonApp;
import co.infinum.pokemon.dagger.modules.AlarmModule;
import co.infinum.pokemon.dagger.modules.ContextModule;
import co.infinum.pokemon.dagger.modules.DatabaseWatcherModule;
import co.infinum.pokemon.dagger.modules.DefaultExecutorsModule;
import co.infinum.pokemon.dagger.modules.HostModule;
import co.infinum.pokemon.dagger.modules.NetworkModule;
import co.infinum.pokemon.dagger.modules.PokemonDAOModule;
import co.infinum.pokemon.dagger.modules.PokemonDetailsModule;
import co.infinum.pokemon.dagger.modules.PokemonListModule;
import dagger.Component;

/**
 * Created by dino on 13/10/15.
 */
@Component(modules = {
        NetworkModule.class,
        HostModule.class,
        DefaultExecutorsModule.class,
        PokemonDAOModule.class,
        DatabaseWatcherModule.class
})
@Singleton
public interface AppComponent {

    void inject(PokemonApp app);

    PokemonListComponent plus(PokemonListModule pokemonListModule);

    PokemonDetailsComponent plus(PokemonDetailsModule pokemonDetailsModule, ContextModule contextModule);

    AlarmComponent plus(AlarmModule alarmModule);
}
