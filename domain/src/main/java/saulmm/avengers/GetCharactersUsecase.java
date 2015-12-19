package saulmm.avengers;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.repository.CharacterRepository;

public class GetCharactersUsecase extends Usecase<List<MarvelCharacter>> {
    public final static int DEFAULT_CHARACTERS_LIMIT = 20;

    private int mCharactersLimit = DEFAULT_CHARACTERS_LIMIT;
    private final Scheduler mResultsThread;
    private final Scheduler mExecutorThread;

    private final CharacterRepository mRepository;
    private int mCurrentOffset;

    @Inject public GetCharactersUsecase(CharacterRepository repository,
        @Named("ui_thread") Scheduler uiThread,
        @Named("executor_thread") Scheduler executorThread) {

        mRepository = repository;
        mResultsThread = uiThread;
        mExecutorThread = executorThread;
    }

    @Override
    public Observable<List<MarvelCharacter>> execute() {
        return mRepository.getCharacters(mCurrentOffset)
            .observeOn(mResultsThread)
            .subscribeOn(mExecutorThread);
    }

    public Observable<List<MarvelCharacter>> executeIncreasingOffset() {
        mCurrentOffset += mCharactersLimit;

        return mRepository.getCharacters(mCurrentOffset)
                .observeOn(mResultsThread)
                .subscribeOn(mExecutorThread)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mCurrentOffset -= mCharactersLimit;
                    }
                });
    }

    public void setCharactersLimit(int charactersLimit) {
        mCharactersLimit = charactersLimit;
    }

    public void setCurrentOffset(int currentOffset) {
        mCurrentOffset = currentOffset;
    }

    public int getCurrentOffset() {
        return mCurrentOffset;
    }
}
