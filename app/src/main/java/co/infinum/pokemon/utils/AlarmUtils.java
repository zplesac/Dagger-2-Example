package co.infinum.pokemon.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import co.infinum.pokemon.PokemonApp;
import co.infinum.pokemon.receivers.AlarmReceiver;

/**
 * Created by Željko Plesac on 02/06/16.
 */
public class AlarmUtils {

    private AlarmUtils() {
    }

    public static void scheduleDatabaseReset() {
        final AlarmManager alarmManager = (AlarmManager) PokemonApp.getInstance().getSystemService(Context.ALARM_SERVICE);

        final Intent intent = new Intent(PokemonApp.getInstance(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(PokemonApp.getInstance(), 0, intent, 0);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() +
                        30 * 1000, alarmIntent);
    }
}
