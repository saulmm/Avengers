package saulmm.avengers.domain;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.model.Character;
import saulmm.avengers.model.Repository;

public class GetCharacterInformationUsecase implements Usecase<saulmm.avengers.model.Character> {

    private final Repository mRepository;
    private int mCharacterId;

    @Inject public GetCharacterInformationUsecase(int characterId, Repository repository) {

        mCharacterId = characterId;
        mRepository = repository;
    }

    @Override
    public Observable<Character> execute() {

        return mRepository.getCharacter(mCharacterId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }
}
