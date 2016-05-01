/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import saulmm.avengers.entities.Character;
import saulmm.avengers.repository.Repository;
import saulmm.avengers.rest.entities.RestCharacter;
import saulmm.avengers.specifications.IdSpecification;

public class CharacterDetailsUsecase extends Usecase<Character> {
    private Repository<Character> mRepository;
    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private final IdSpecification mIdSpecification;

    @Inject public CharacterDetailsUsecase(int characterId,
        Repository<Character> repository,
        @Named("ui_thread") Scheduler uiThread,
        @Named("executor_thread") Scheduler executorThread) {

        mRepository = repository;
        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mIdSpecification = new IdSpecification(characterId);
    }

    @Override
    public Observable<Character> buildObservable() {
        return mRepository.get(mIdSpecification)
            .observeOn(mUiThread)
            .subscribeOn(mExecutorThread)
            .flatMap(new Func1<List<Character>, Observable<Character>>() {
                @Override
                public Observable<Character> call(List<Character> characters) {
                    if (!characters.isEmpty())
                        return Observable.just(characters.get(0));

                    else return Observable.empty();
                }
            });
    }
}
