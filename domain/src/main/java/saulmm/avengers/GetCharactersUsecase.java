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
    private final CharacterRepository mRepository;
    private int mCurrentOffset;

     Scheduler uiThread, executorThread;

    @Inject public GetCharactersUsecase(CharacterRepository repository,
        @Named("ui_thread") Scheduler uiThread,
        @Named("executor_thread") Scheduler executorThread) {

        mRepository = repository;
        this.uiThread = uiThread;
        this.executorThread = executorThread;
    }

    @Override
    public Observable<List<MarvelCharacter>> buildObservable() {
        return mRepository.getCharacters(mCurrentOffset)
            .observeOn(uiThread)
            .subscribeOn(executorThread)
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    mCurrentOffset -= mCharactersLimit;
                }
            });
    }

    @Override
    public Observable<List<MarvelCharacter>> execute() {
        increaseOffset();
        return super.execute();
    }

    public void increaseOffset() {
        mCurrentOffset += mCharactersLimit;
    }

    public int getCurrentOffset() {
        return mCurrentOffset;
    }
}
