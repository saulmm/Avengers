package saulmm.avengers.domain;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.model.Comic;
import saulmm.avengers.model.ComicDate;
import saulmm.avengers.model.Repository;

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
