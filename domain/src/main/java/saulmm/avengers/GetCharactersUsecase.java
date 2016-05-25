package saulmm.avengers;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;
import rx.functions.Action1;
import saulmm.avengers.entities.Character;
import saulmm.avengers.specifications.PaginationSpecification;

public class GetCharactersUsecase extends Usecase<List<Character>> {
    public final static int DEFAULT_CHARACTERS_LIMIT = 20;
    private final saulmm.avengers.repository.Repository<Character> mCharacterRepository;

    private final Scheduler mUiThread;
    private final Scheduler mExecutorThread;

    private final PaginationSpecification mPaginationSpecification;

    @Inject public GetCharactersUsecase(
        @Named("ui_thread") Scheduler uiThread,
        @Named("executor_thread") Scheduler executorThread,
        saulmm.avengers.repository.Repository<Character> characterRepository) {

        mUiThread = uiThread;
        mExecutorThread = executorThread;
        mCharacterRepository = characterRepository;
        mPaginationSpecification = new PaginationSpecification(0, DEFAULT_CHARACTERS_LIMIT);
    }

    @Override
    public Observable<List<Character>> buildObservable() {
        return mCharacterRepository.get(mPaginationSpecification)
            .observeOn(mUiThread)
            .subscribeOn(mExecutorThread)
            .doOnError(new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    mPaginationSpecification.decreaseOffset();
                }
            });
    }

    @Override
    public Observable<List<Character>> execute() {
        mPaginationSpecification.increaseOffset();
        return super.execute();
    }

    public int getCurrentOffset() {
        return mPaginationSpecification.getOffset();
    }
}
