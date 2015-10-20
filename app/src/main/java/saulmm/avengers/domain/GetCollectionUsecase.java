package saulmm.avengers.domain;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import saulmm.avengers.model.entities.CollectionItem;
import saulmm.avengers.model.repository.Repository;

public class GetCollectionUsecase implements Usecase<List<CollectionItem>> {
	private final Repository mRepository;

	@Inject
	public GetCollectionUsecase(Repository repository) {
		mRepository = repository;
	}

	@Override
	public Observable<List<CollectionItem>> execute() {
		return null;
	}
}
