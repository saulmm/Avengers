package saulmm.avengers.injector.components;


import android.content.Context;

import dagger.Component;
import saulmm.avengers.injector.Activity;
import saulmm.avengers.injector.modules.ActivityModule;

@Activity
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Context context();
}
