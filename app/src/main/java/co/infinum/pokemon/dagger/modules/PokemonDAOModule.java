package co.infinum.pokemon.dagger.modules;

import co.infinum.pokemon.database.DBFlowPokemonDAO;
import co.infinum.pokemon.database.interfaces.PokemonDAO;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Željko Plesac on 26/05/16.
 */
@Module
public class PokemonDAOModule {

    @Provides
    public PokemonDAO providePokemonDAO() {
        return new DBFlowPokemonDAO();
    }
}
