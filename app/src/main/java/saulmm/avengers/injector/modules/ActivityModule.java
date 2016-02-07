/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector.modules;


import android.content.Context;

import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
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
