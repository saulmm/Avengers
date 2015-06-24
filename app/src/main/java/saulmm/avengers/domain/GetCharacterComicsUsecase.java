/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.domain;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.model.entities.Comic;
import saulmm.avengers.model.entities.ComicDate;
import saulmm.avengers.model.repository.Repository;

public class GetCharacterComicsUsecase implements Usecase<List<Comic>> {

    private final Repository mRepository;
    private int mCharacterId;
    private List<Comic> mComics;

    @Inject public GetCharacterComicsUsecase(int characterId, Repository repository) {

        mCharacterId = characterId;
        mRepository = repository;
    }

    public Observable<Comic> filterByYear(String year) {

        if (mComics != null) {
            return Observable.from(mComics).filter(comic -> {

                for (ComicDate comicDate : comic.getDates())
                    if (comicDate.getDate().startsWith(year))
                        return true;

                return false;
            });
        }

        return null;
    }

    @Override
    public Observable<List<Comic>> execute() {

        return mRepository.getCharacterComics(mCharacterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(comics -> mComics = comics);
    }
}
