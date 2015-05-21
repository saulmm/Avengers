package saulmm.avengers.domain;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.model.Comic;
import saulmm.avengers.model.Repository;

public class GetCharacterComicsUsecase implements Usecase<List<Comic>> {

    private final Repository mRepository;
    private int mCharacterId;

    @Inject public GetCharacterComicsUsecase(int characterId, Repository repository) {

        mCharacterId = characterId;
        mRepository = repository;
    }

    @Override
    public Observable<List<Comic>> execute() {

        return mRepository.getCharacterComics(mCharacterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
