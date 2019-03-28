package css.unit10participation;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Planet.class}, version = 1)
public abstract class PlanetRoomDatabase extends RoomDatabase {

    public abstract PlanetDAO planetDAO();

    private static volatile PlanetRoomDatabase INSTANCE;

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PlanetDAO mDao;

        PopulateDbAsync(PlanetRoomDatabase db) {
            mDao = db.planetDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Planet planet1 = new Planet("Earth", 9.8f);
            mDao.insert(planet1);
            Planet planet2 = new Planet("Venus", 9.6f);
            mDao.insert(planet2);
            return null;
        }

        static PlanetRoomDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (PlanetRoomDatabase.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                PlanetRoomDatabase.class, "planet_database")
                                .addCallback(sRoomDatabaseCallback)
                                .build();
                    }
                }
            }
            return INSTANCE;
        }
    }
}
