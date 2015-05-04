package saulmm.avengers.injector.modules;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.R;
import saulmm.avengers.injector.ActivityScope;
import saulmm.avengers.model.Character;

@Module
public class AvengersModule {

    @Provides @ActivityScope
    List<Character> provideAvengers() {

        List<Character> avengers = new ArrayList<>(6);
        avengers.add(new Character("Iron Man", R.drawable.thumb_iron_man, "1009368"));
        avengers.add(new Character("Thor", R.drawable.thumb_thor, "1009664"));
        avengers.add(new Character("Captain America", R.drawable.thumb_cap,"1009220"));
        avengers.add(new Character("Natasha Romanoff", R.drawable.thumb_nat, "1009547"));
        avengers.add(new Character("HawkEye", R.drawable.thumb_hawkeye, "1009338"));
        avengers.add(new Character("Hulk", R.drawable.thumb_hulk, "1009351"));
        return avengers;
    }
}
