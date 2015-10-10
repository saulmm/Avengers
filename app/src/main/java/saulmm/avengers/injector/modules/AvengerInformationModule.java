/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.injector.modules;

import dagger.Module;
import dagger.Provides;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.injector.Activity;
import saulmm.avengers.model.repository.Repository;

@Module
public class AvengerInformationModule {
    private final int mCharacterId;

    public AvengerInformationModule(int characterId) {
        mCharacterId = characterId;
    }

    @Provides @Activity GetCharacterInformationUsecase provideGetCharacterInformationUsecase (Repository repository) {
        return new GetCharacterInformationUsecase(mCharacterId, repository);
    }

    @Provides @Activity GetCharacterComicsUsecase provideGetCharacterComicsUsecase (Repository repository) {
        return new GetCharacterComicsUsecase(mCharacterId, repository);
    }
}
