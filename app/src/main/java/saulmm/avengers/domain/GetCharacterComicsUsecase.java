package saulmm.avengers.domain;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.model.Repository;

public class GetCharacterComicsUsecase implements Usecase {

    private final Repository mRepository;
    private int mCharacterId;

    @Inject public GetCharacterComicsUsecase(int characterId, Repository repository) {

        mCharacterId = characterId;
        mRepository = repository;
    }

    @Override
    public Subscription execute(Subscriber subscriber) {

        return mRepository.getCharacter(mCharacterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);
    }
}
