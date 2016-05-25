package saulmm.avengers;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.Scheduler;
import saulmm.avengers.entities.Character;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.repository.Repository;
import saulmm.avengers.rest.entities.RestCollectionItem;
import saulmm.avengers.specifications.CollectionSpecificationFactory;

public class GetCollectionUsecase extends Usecase<List<CollectionItem>> {
	private final Repository<CollectionItem> mRepository;
	private final int mCharacterId;
	private final Scheduler mUIThread;
	private final Scheduler mExecutorThread;
	private CollectionItem.Type mType;

	@Inject
	public GetCollectionUsecase(
		int characterId,
		Repository<CollectionItem> repository,
		@Named("ui_thread") Scheduler uiThread,
		@Named("executor_thread") Scheduler executorThread) {

		mRepository = repository;
		mCharacterId = characterId;
		mUIThread = uiThread;
		mExecutorThread = executorThread;
	}

	public void setType(CollectionItem.Type type) {
		mType = type;
	}

	@Override
	public Observable<List<CollectionItem>> buildObservable() {
		return mRepository.get(CollectionSpecificationFactory.get(mType, mCharacterId))
			.observeOn(mUIThread).subscribeOn(mExecutorThread);
	}
}
