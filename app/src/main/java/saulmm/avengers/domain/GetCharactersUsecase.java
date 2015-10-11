package saulmm.avengers.domain;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.model.repository.Repository;

public class GetCharactersUsecase implements Usecase<List<Character>> {
    public final static int CHARACTERS_LIMIT = 20;

    private final Repository mRepository;
    private int currentOffset;

    @Inject public GetCharactersUsecase(Repository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<List<Character>> execute() {
        return mRepository.getCharacters(currentOffset)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Character>> executeIncreasingOffset() {
        currentOffset += CHARACTERS_LIMIT;

        return mRepository.getCharacters(currentOffset)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(throwable -> {
                currentOffset -= CHARACTERS_LIMIT;
            });
    }
}
