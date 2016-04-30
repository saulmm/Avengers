package saulmm.avengers;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import saulmm.avengers.entities.Character;
import saulmm.avengers.rest.entities.RestCharacter;

public class GetCharactersUsecase extends Usecase<List<Character>> {
    public final static int DEFAULT_CHARACTERS_LIMIT = 20;
    private final Repository<Character> mCharacterRepository;

    private int mCharactersLimit = DEFAULT_CHARACTERS_LIMIT;
    private int mCurrentOffset;

    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    @Inject public GetCharactersUsecase(
        @Named("ui_thread") Scheduler uiThread,
        @Named("executor_thread") Scheduler executorThread,

        Repository<Character> characterRepository) {

        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mCharacterRepository = characterRepository;
    }

    @Override
    public Observable<List<Character>> buildObservable() {
        System.out.println(mCharacterRepository);
        return null;
    }

    @Override
    public Observable<List<Character>> execute() {
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
