package saulmm.avengers.injector.modules;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.injector.Activity;

@Module
public class ActivityModule {

    private final Context mContext;

    public ActivityModule(Context mContext) {

        this.mContext = mContext;
    }

    @Provides @Activity
    Context provideActivityContext() {
        return mContext;
    }
}
