/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.tests;

import saulmm.avengers.AvengersApplication;
import saulmm.avengers.injector.components.AppComponent;

public class TestAvengersApplication extends AvengersApplication {
    private TestAppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        mAppComponent = DaggerTestAppComponent.builder()
                .testAppModule(new TestAppModule(this))
                .build();
    }

    @Override
    public TestAppComponent getAppComponent() {
        return mAppComponent;
    }
}
