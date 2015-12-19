package saulmm.avengers;

import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.repository.CharacterRepository;

public class GetCollectionUsecase extends Usecase<List<CollectionItem>> {
	private final CharacterRepository mRepository;
	private final int mCharacterId;
	private String mType;

	@Inject public GetCollectionUsecase(int characterId, CharacterRepository repository) {
		mRepository = repository;
		mCharacterId = characterId;
	}

	public void setType(String type) {
		if (!type.equals(CollectionItem.COMICS) && !type.equals(CollectionItem.EVENTS) && !type.equals(
				CollectionItem.SERIES) && !type.equals(CollectionItem.STORIES))

			throw new IllegalArgumentException("Collection type must be events|series|comics|stories");

		mType = type;
	}

	@Override
	public Observable<List<CollectionItem>> buildObservable() {
		return mRepository.getCharacterCollection(mCharacterId, mType);
	}
}
