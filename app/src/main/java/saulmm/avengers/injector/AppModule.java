package saulmm.avengers.injector;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.model.Repository;
import saulmm.avengers.model.rest.RestRepository;

@Module
public class AppModule {

    private final AvengersApplication mAvengersApplication;

    public AppModule(AvengersApplication avengersApplication) {

        this.mAvengersApplication = avengersApplication;
    }

    @Provides @Singleton AvengersApplication provideAvengersApplicationContext () { return mAvengersApplication; }

    @Provides @Singleton Repository provideDataRepository (RestRepository restRepository) { return restRepository; }
}
