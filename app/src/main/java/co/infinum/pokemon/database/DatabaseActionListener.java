package co.infinum.pokemon.database;

/**
 * Created by Željko Plesac on 25/05/16.
 */
public interface DatabaseActionListener {

    void onSuccess();

    void onError(Throwable e);
}
