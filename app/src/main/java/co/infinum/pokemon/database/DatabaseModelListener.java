package co.infinum.pokemon.database;

/**
 * Created by Željko Plesac on 25/05/16.
 */
public interface DatabaseModelListener<T> {

    void onModelLoaded(T model);

    void onNoModelFound();
}
