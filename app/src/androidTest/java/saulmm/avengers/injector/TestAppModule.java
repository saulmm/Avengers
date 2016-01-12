/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.repository.CharacterRepository;
import saulmm.avengers.MockRestDataSource;
import saulmm.avengers.TestAvengersApplication;
import saulmm.avengers.rest.Endpoint;

@Module
public class TestAppModule {

    private final TestAvengersApplication mTestAvengersApplication;

    public TestAppModule(TestAvengersApplication avengersApplication) {

        this.mTestAvengersApplication = avengersApplication;
    }

    @Provides @Singleton TestAvengersApplication provideAvengersApplicationContext () { return mTestAvengersApplication; }

    @Provides @Singleton CharacterRepository provideDataRepository () { return new MockRestDataSource(); }

    @Provides
    Endpoint provideRestEndpoint() {
        return new Endpoint("http://gateway.marvel.com/");
    }
}
