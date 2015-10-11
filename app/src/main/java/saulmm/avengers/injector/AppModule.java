/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.model.repository.Repository;
import saulmm.avengers.model.rest.RestRepository;

@Module
public class AppModule {

    private final AvengersApplication mAvengersApplication;

    public AppModule(AvengersApplication avengersApplication) {

        this.mAvengersApplication = avengersApplication;
    }

    @Provides @Singleton AvengersApplication provideAvengersApplicationContext () { return mAvengersApplication; }

    @Provides @Singleton Repository provideDataRepository (RestRepository restRepository) { return restRepository; }
}
