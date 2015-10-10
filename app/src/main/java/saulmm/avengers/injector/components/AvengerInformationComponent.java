/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector.components;

import dagger.Component;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.injector.Activity;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.views.activities.AvengerDetailActivity;

@Activity
@Component(dependencies = AppComponent.class, modules = {AvengerInformationModule.class, ActivityModule.class})
public interface AvengerInformationComponent extends ActivityComponent {

    void inject (AvengerDetailActivity detailActivity);

    GetCharacterInformationUsecase getCharacerInformationUsecase();
    //GetCharacterComicsUsecase getCharacterComicsUsecase();
}
