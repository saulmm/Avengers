package saulmm.avengers;

import android.app.Application;

import saulmm.avengers.injector.components.AppComponent;

public class AvengersApplication extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {

        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {

//        mAppComponent = DaggerAppComponent.builder()
//            .appModule(new AppModule(this))
//            .build();
    }

    public AppComponent getAppComponent() {

        return mAppComponent;
    }
}
