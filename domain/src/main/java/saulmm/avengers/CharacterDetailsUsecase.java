/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.CharacterRepository;

public class CharacterDetailsUsecase implements Usecase<MarvelCharacter> {
    private final CharacterRepository mRepository;
    private final Scheduler mResultsThread;
    private final Scheduler mExecutorThread;
    private int mCharacterId;

    @Inject public CharacterDetailsUsecase(int characterId,
        CharacterRepository repository,
        @Named("ui_thread") Scheduler uiThread,
        @Named("executor_thread") Scheduler executorThread) {

        mCharacterId = characterId;
        mRepository = repository;
        mResultsThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<MarvelCharacter> execute() {
        return mRepository.getCharacter(mCharacterId)
                .observeOn(mResultsThread)
                .subscribeOn(mExecutorThread);
    }
}
