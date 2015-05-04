package saulmm.avengers.injector.components;


import android.content.Context;

import dagger.Component;
import saulmm.avengers.injector.ActivityScope;
import saulmm.avengers.injector.modules.ActivityModule;

@ActivityScope @Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Context context();
}
