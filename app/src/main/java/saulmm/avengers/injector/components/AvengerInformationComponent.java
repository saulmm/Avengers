/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector.components;

import dagger.Component;
import saulmm.avengers.CharacterDetailsUsecase;
import saulmm.avengers.GetCollectionUsecase;
import saulmm.avengers.injector.Activity;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.views.activities.CharacterDetailActivity;
import saulmm.avengers.views.activities.CollectionActivity;

@Activity
@Component(dependencies = AppComponent.class, modules = {AvengerInformationModule.class, ActivityModule.class})
public interface AvengerInformationComponent extends ActivityComponent {

    void inject (CharacterDetailActivity detailActivity);

    void inject (CollectionActivity detailActivity);

    CharacterDetailsUsecase getCharacerInformationUsecase();
    GetCollectionUsecase getCollectionUsecase();
}
