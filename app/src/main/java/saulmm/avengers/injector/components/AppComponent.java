/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector.components;

import dagger.Component;
import javax.inject.Singleton;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.injector.AppModule;
import saulmm.avengers.model.repository.Repository;

@Singleton @Component(modules = AppModule.class)
public interface AppComponent {
    AvengersApplication app();
    Repository dataRepository();
}
