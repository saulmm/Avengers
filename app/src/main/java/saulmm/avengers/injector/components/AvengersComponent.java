package saulmm.avengers.injector.components;

import java.util.List;

import dagger.Component;
import saulmm.avengers.injector.Activity;
import saulmm.avengers.injector.AppModule;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengersModule;
import saulmm.avengers.model.Character;
import saulmm.avengers.views.activities.AvengersListActivity;

@Activity
@Component(dependencies = AppModule.class, modules = {AvengersModule.class, ActivityModule.class})
public interface AvengersComponent extends ActivityComponent {

    void inject (AvengersListActivity activity);

    List<Character> avengers();
}
