package saulmm.avengers.injector;

import dagger.Component;
import saulmm.avengers.views.activities.AvengersListActivity;

@Component(modules = AppModule.class)
public interface AppComponent {

    void inject (AvengersListActivity avengersListActivity);
}
