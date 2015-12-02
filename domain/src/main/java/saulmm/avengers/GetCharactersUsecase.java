package saulmm.avengers;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Action1;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.Repository;

public class GetCharactersUsecase implements Usecase<List<MarvelCharacter>> {
    public final static int CHARACTERS_LIMIT = 20;

    private final Repository mRepository;
    private int currentOffset;

    @Inject public GetCharactersUsecase(Repository repository) {
        mRepository = repository;
    }

    @Override
    public Observable<List<MarvelCharacter>> execute() {
        return mRepository.getCharacters(currentOffset);
    }

    public Observable<List<MarvelCharacter>> executeIncreasingOffset() {
        currentOffset += CHARACTERS_LIMIT;

        return mRepository.getCharacters(currentOffset)
            .doOnError(new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    currentOffset -= CHARACTERS_LIMIT;
                }
            });
    }
}
