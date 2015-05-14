package saulmm.avengers.domain;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.model.Repository;

public class GetCharacterInformationUsecase implements Usecase {

    private final Repository mRepository;
    private int mCharacterId;

    @Inject public GetCharacterInformationUsecase(int characterId, Repository repository) {

        mCharacterId = characterId;
        mRepository = repository;
    }

    @Override public Subscription execute(Subscriber subscriber) {

        Subscription subscription = mRepository.getCharacter(mCharacterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(subscriber);

        return subscription;
    }
}
