package css.unit10participation;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class PlanetRepository {

    private PlanetDAO mPlanetDAO;
    private LiveData<List<Planet>> mAllPlanets;

    PlanetRepository(Application application) {
        PlanetRoomDatabase db = PlanetRoomDatabase.getDatabase(application);
        mPlanetDAO = db.planetDAO();
        mAllPlanets = mPlanetDAO.getAllPlanets();
    }

    LiveData<List<Planet>> getAllPlanets() {
        return mAllPlanets;
    }


    public void insert (Planet planet) {
        new insertAsyncTask(mPlanetDAO).execute(planet);
    }

    private static class insertAsyncTask extends AsyncTask<Planet, Void, Void> {

        private PlanetDAO mAsyncTaskDao;

        insertAsyncTask(PlanetDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Planet... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
