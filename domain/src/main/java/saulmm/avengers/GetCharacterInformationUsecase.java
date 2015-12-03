/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers;

import javax.inject.Inject;
import rx.Observable;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.Repository;

public class GetCharacterInformationUsecase implements Usecase<MarvelCharacter> {

    private final Repository mRepository;
    private int mCharacterId;

    @Inject public GetCharacterInformationUsecase(int characterId, Repository repository) {
        mCharacterId = characterId;
        mRepository = repository;
    }

    @Override
    public Observable<MarvelCharacter> execute() {
        return mRepository.getCharacter(mCharacterId);
    }
}
