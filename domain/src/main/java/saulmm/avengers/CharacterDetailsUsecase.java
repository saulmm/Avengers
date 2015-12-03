/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers;

import javax.inject.Inject;
import rx.Observable;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.CharacterRepository;

public class CharacterDetailsUsecase implements Usecase<MarvelCharacter> {

    private final CharacterRepository mRepository;
    private int mCharacterId;

    @Inject public CharacterDetailsUsecase(int characterId, CharacterRepository repository) {
        mCharacterId = characterId;
        mRepository = repository;
    }

    @Override
    public Observable<MarvelCharacter> execute() {
        return mRepository.getCharacter(mCharacterId);
    }
}
