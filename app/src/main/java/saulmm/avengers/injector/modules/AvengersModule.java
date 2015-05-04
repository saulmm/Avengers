package saulmm.avengers.injector.modules;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.injector.ActivityScope;
import saulmm.avengers.model.Character;

@Module
public class AvengersModule {

    @Provides @ActivityScope
    List<Character> provideAvengers() {

        List<Character> avengers = new ArrayList<>(5);
        avengers.add(new Character());
        avengers.add(new Character());
        avengers.add(new Character());
        avengers.add(new Character());
        avengers.add(new Character());

        return avengers;
    }
}
