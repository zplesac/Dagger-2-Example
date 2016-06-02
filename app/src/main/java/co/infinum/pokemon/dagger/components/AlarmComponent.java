package co.infinum.pokemon.dagger.components;

import co.infinum.pokemon.dagger.modules.AlarmModule;
import co.infinum.pokemon.receivers.AlarmReceiver;
import dagger.Subcomponent;

/**
 * Created by Željko Plesac on 02/06/16.
 */
@Subcomponent(modules = AlarmModule.class)
public interface AlarmComponent {

    void inject(AlarmReceiver alarmReceiver);
}
