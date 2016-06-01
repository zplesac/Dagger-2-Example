package co.infinum.pokemon.database;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.QueryTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import co.infinum.pokemon.database.interfaces.DatabaseActionListener;
import co.infinum.pokemon.database.interfaces.DatabaseModelListener;
import co.infinum.pokemon.database.interfaces.PokemonDAO;
import co.infinum.pokemon.models.Pokemon;
import co.infinum.pokemon.models.Pokemon_Table;
import timber.log.Timber;

/**
 * Created by Željko Plesac on 25/05/16.
 */
public class DBFlowPokemonDAO implements PokemonDAO {

    @Override
    public boolean insert(Pokemon pokemon) {
        try {
            pokemon.save();
            return true;
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while caching Pokemon!");
            return false;
        }
    }

    @Override
    public boolean insert(List<Pokemon> pokemons) {
        try {
            for (Pokemon pokemon : pokemons) {
                boolean isAdded = insert(pokemon);

                if (!isAdded) {
                    // Something went wrong, break!
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while inserting Pokemons!");
            return false;
        }
    }

    @Override
    public void insert(Pokemon pokemon, final DatabaseActionListener listener) {
        ProcessModelTransaction<Pokemon> deleteTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Pokemon>() {
                    @Override
                    public void processModel(Pokemon model) {
                        model.save();
                    }
                }).add(pokemon).build();

        Transaction transaction = FlowManager.getDatabase(PokemonDatabase.class)
                .beginTransactionAsync(deleteTransaction)
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        listener.onSuccess();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        listener.onError(error);
                    }
                })
                .build();
        transaction.execute();
    }

    @Override
    public void insert(List<Pokemon> pokemons, final DatabaseActionListener listener) {
        ProcessModelTransaction<Pokemon> deleteTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Pokemon>() {
                    @Override
                    public void processModel(Pokemon model) {
                        model.save();
                    }
                }).addAll(pokemons).build();

        Transaction transaction = FlowManager.getDatabase(PokemonDatabase.class)
                .beginTransactionAsync(deleteTransaction)
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        listener.onSuccess();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        listener.onError(error);
                    }
                })
                .build();
        transaction.execute();
    }

    @Override
    public void update(Pokemon pokemon, String resourceUri, int hp, int attack, int defense, String height, String weight) {
        try {
            pokemon.setResourceUri(resourceUri);
            pokemon.setHp(hp);
            pokemon.setAttack(attack);
            pokemon.setDefense(defense);
            pokemon.setHeight(height);
            pokemon.setWeight(weight);
            pokemon.update();
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while updating Pokemon!");
        }
    }

    @Override
    public void update(Pokemon pokemon) {
        try {
            SQLite.update(Pokemon.class)
                    .set(Pokemon_Table.Attack.eq(pokemon.getAttack()),
                            Pokemon_Table.Defense.eq(pokemon.getDefense()),
                            Pokemon_Table.Height.eq(pokemon.getHeight()),
                            Pokemon_Table.Hp.eq(pokemon.getHp()),
                            Pokemon_Table.Weight.eq("100"),
                            Pokemon_Table.ResourceUri.eq(pokemon.getResourceUri())
                    )
                    .where(Pokemon_Table.Name.is(pokemon.getName().toLowerCase())).async().execute();
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while updating Pokemon!");
        }
    }

    @Override
    public void update(@NonNull List<Pokemon> pokemons) {

        for (Pokemon pokemon : pokemons) {
            update(pokemon);
        }
    }

    @Override
    public void update(Pokemon pokemon, final DatabaseActionListener listener) {
        SQLite.update(Pokemon.class)
                .set(Pokemon_Table.Attack.eq(pokemon.getAttack()),
                        Pokemon_Table.Defense.eq(pokemon.getDefense()),
                        Pokemon_Table.Height.eq(pokemon.getHeight()),
                        Pokemon_Table.Hp.eq(pokemon.getHp()),
                        Pokemon_Table.Weight.eq(pokemon.getWeight()),
                        Pokemon_Table.ResourceUri.eq(pokemon.getResourceUri())
                )
                .where(Pokemon_Table.Name.is(pokemon.getName()))
                .async()
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        listener.onSuccess();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        listener.onError(error);
                    }
                })
                .execute();
    }

    @Override
    public void update(List<Pokemon> pokemons, final DatabaseActionListener listener) {
        ProcessModelTransaction<Pokemon> updateTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Pokemon>() {
                    @Override
                    public void processModel(Pokemon pokemon) {
                        SQLite.update(Pokemon.class)
                                .set(Pokemon_Table.Attack.eq(pokemon.getAttack()),
                                        Pokemon_Table.Defense.eq(pokemon.getDefense()),
                                        Pokemon_Table.Height.eq(pokemon.getHeight()),
                                        Pokemon_Table.Hp.eq(pokemon.getHp()),
                                        Pokemon_Table.Weight.eq(pokemon.getWeight()),
                                        Pokemon_Table.ResourceUri.eq(pokemon.getResourceUri())
                                ).execute();
                    }
                }).addAll(pokemons).build();

        Transaction transaction = FlowManager.getDatabase(PokemonDatabase.class)
                .beginTransactionAsync(updateTransaction)
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        listener.onSuccess();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        listener.onError(error);
                    }
                })
                .build();
        transaction.execute();
    }

    @Override
    public boolean delete(Pokemon pokemon) {
        try {
            pokemon.delete();
            return true;
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while deleting Pokemon!");
            return false;
        }
    }

    @Override
    public void delete(Pokemon pokemon, final DatabaseActionListener listener) {
        ProcessModelTransaction<Pokemon> deleteTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Pokemon>() {
                    @Override
                    public void processModel(Pokemon model) {
                        model.delete();
                    }
                }).add(pokemon).build();

        Transaction transaction = FlowManager.getDatabase(PokemonDatabase.class)
                .beginTransactionAsync(deleteTransaction)
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        listener.onSuccess();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        listener.onError(error);
                    }
                })
                .build();
        transaction.execute();
    }

    @Override
    public boolean delete(List<Pokemon> pokemons) {
        try {
            for (Pokemon pokemon : pokemons) {
                boolean isDeleted = delete(pokemon);

                if (!isDeleted) {
                    // Something went wrong, break!
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while deleting Pokemons!");
            return false;
        }
    }

    @Override
    public void delete(List<Pokemon> pokemons, final DatabaseActionListener listener) {
        ProcessModelTransaction<Pokemon> deleteTransaction =
                new ProcessModelTransaction.Builder<>(new ProcessModelTransaction.ProcessModel<Pokemon>() {
                    @Override
                    public void processModel(Pokemon model) {
                        model.delete();
                    }
                }).addAll(pokemons).build();

        Transaction transaction = FlowManager.getDatabase(PokemonDatabase.class)
                .beginTransactionAsync(deleteTransaction)
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(Transaction transaction) {
                        listener.onSuccess();
                    }
                })
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        listener.onError(error);
                    }
                })
                .build();
        transaction.execute();
    }

    @Override
    public void deleteAll() {
        Delete.table(Pokemon.class);
    }

    @Override
    @Nullable
    public Pokemon getByName(String name) {
        try {
            return SQLite.select().from(Pokemon.class).where(Pokemon_Table.Name.eq(name)).querySingle();
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while fetching pokemon by it's name");
            return null;
        }
    }

    @Override
    public void getByName(String name, final DatabaseModelListener<Pokemon> listener) {
        SQLite.select().from(Pokemon.class).where(Pokemon_Table.Name.eq(name)).async().queryResultCallback(
                new QueryTransaction.QueryResultCallback<Pokemon>() {
                    @Override
                    public void onQueryResult(QueryTransaction transaction, @NonNull CursorResult<Pokemon> tResult) {
                        Pokemon pokemon = tResult.toModelClose();
                        if (pokemon == null) {
                            listener.onNoModelFound();
                        } else {
                            listener.onModelLoaded(pokemon);
                        }
                    }
                }).execute();
    }

    @Override
    public List<Pokemon> getAll() {
        try {
            return SQLite.select().from(Pokemon.class).queryList();
        } catch (Exception e) {
            Timber.e(e, "Exception occurred while fetching list of Pokemons");
            return new ArrayList<>();
        }
    }

    @Override
    public void getAll(final DatabaseModelListener<List<Pokemon>> listener) {
        SQLite.select().from(Pokemon.class).async().queryResultCallback(new QueryTransaction.QueryResultCallback<Pokemon>() {
            @Override
            public void onQueryResult(QueryTransaction transaction, @NonNull CursorResult<Pokemon> tResult) {
                List<Pokemon> results = tResult.toListClose();

                if (results == null || results.isEmpty()) {
                    listener.onNoModelFound();
                } else {
                    listener.onModelLoaded(results);
                }
            }
        }).execute();
    }
}
