package saulmm.avengers.injector.components;

import javax.inject.Singleton;

import dagger.Component;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.injector.AppModule;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {

    AvengersApplication app();
}
