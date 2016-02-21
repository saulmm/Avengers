package saulmm.avengers;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import saulmm.avengers.rest.entities.RestCollectionItem;
import saulmm.avengers.repository.CharacterRepository;

public class GetCollectionUsecase extends Usecase<List<RestCollectionItem>> {
	private final CharacterRepository mRepository;
	private final int mCharacterId;
	private final Scheduler mUIThread;
	private final Scheduler mExecutorThread;
	private String mType;

	@Inject public GetCollectionUsecase(int characterId,
		CharacterRepository repository,
		@Named("ui_thread") Scheduler uiThread,
		@Named("executor_thread") Scheduler executorThread) {

		mRepository = repository;
		mCharacterId = characterId;
		mUIThread = uiThread;
		mExecutorThread = executorThread;
	}

	public void setType(String type) {
		if (!type.equals(RestCollectionItem.COMICS) && !type.equals(RestCollectionItem.EVENTS) && !type.equals(
				RestCollectionItem.SERIES) && !type.equals(RestCollectionItem.STORIES))

			throw new IllegalArgumentException("Collection type must be events|series|comics|stories");

		mType = type;
	}

	@Override
	public Observable<List<RestCollectionItem>> buildObservable() {
		return mRepository.getCharacterCollection(mCharacterId, mType)
			.observeOn(mUIThread).subscribeOn(mExecutorThread);
	}
}
