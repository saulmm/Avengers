/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector.components;

import dagger.Component;

import javax.inject.Named;
import javax.inject.Singleton;

import rx.Scheduler;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.injector.modules.AppModule;
import saulmm.avengers.repository.CharacterRepository;
import saulmm.avengers.rest.Endpoint;
import saulmm.avengers.rest.MarvelAuthorizer;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {
    AvengersApplication app();
    CharacterRepository dataRepository();
    Endpoint restEndpoint();
    MarvelAuthorizer marvelAuthorizer();

    @Named("ui_thread") Scheduler uiThread();
    @Named("executor_thread") Scheduler executorThread();
}
