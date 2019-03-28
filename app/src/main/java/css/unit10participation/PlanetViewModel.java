package css.unit10participation;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class PlanetViewModel extends AndroidViewModel {

    private PlanetRepository mRepository;

    private LiveData<List<Planet>> mAllPlanets;

    public PlanetViewModel (Application application) {
        super(application);
        mRepository = new PlanetRepository(application);
        mAllPlanets = mRepository.getAllPlanets();
    }

    LiveData<List<Planet>> getAllPlanets() { return mAllPlanets; }

    public void insert(Planet planet) { mRepository.insert(planet); }
}
