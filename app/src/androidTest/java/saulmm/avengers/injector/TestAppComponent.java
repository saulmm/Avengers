/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector;

import javax.inject.Singleton;

import dagger.Component;
import saulmm.avengers.injector.components.AppComponent;
import saulmm.avengers.repository.CharacterRepository;
import saulmm.avengers.TestAvengersApplication;

@Singleton @Component(modules = TestAppModule.class)
public interface TestAppComponent  extends AppComponent {
    TestAvengersApplication app();
    CharacterRepository dataRepository();
}
