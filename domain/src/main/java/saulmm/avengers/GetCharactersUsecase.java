package saulmm.avengers;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.CharacterRepository;

public class GetCharactersUsecase implements Usecase<List<MarvelCharacter>> {
    public final static int CHARACTERS_LIMIT = 20;

    Scheduler mResultsThread;
    Scheduler mExecutorThread;

    private final CharacterRepository mRepository;
    private int currentOffset;

    @Inject public GetCharactersUsecase(CharacterRepository repository,
        @Named("ui_thread") Scheduler uiThread,
        @Named("executor_thread") Scheduler executorThread) {

        mRepository = repository;
        mResultsThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<List<MarvelCharacter>> execute() {
        return mRepository.getCharacters(currentOffset)
            .observeOn(mExecutorThread).subscribeOn(mResultsThread);
    }

    public Observable<List<MarvelCharacter>> executeIncreasingOffset() {
        currentOffset += CHARACTERS_LIMIT;

        return mRepository.getCharacters(currentOffset).observeOn(mExecutorThread)
            .subscribeOn(mResultsThread)
            .doOnError(new Action1<Throwable>() {
                @Override public void call(Throwable throwable) {
                    currentOffset -= CHARACTERS_LIMIT;
                }
            });
    }
}
