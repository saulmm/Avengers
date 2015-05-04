package saulmm.avengers.injector.components;

import dagger.Component;
import saulmm.avengers.injector.ActivityScope;
import saulmm.avengers.injector.AppModule;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengersModule;
import saulmm.avengers.views.activities.AvengersListActivity;

@ActivityScope @Component(dependencies = AppModule.class, modules = {AvengersModule.class, ActivityModule.class})
public interface AvengersComponent extends ActivityComponent {

    void inject (AvengersListActivity activity);
}
