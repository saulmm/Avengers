package saulmm.avengers.mvp.presenters;

import android.content.Context;
import java.util.List;
import javax.inject.Inject;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import saulmm.avengers.GetCollectionUsecase;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.mvp.views.CollectionView;
import saulmm.avengers.mvp.views.View;

public class CollectionPresenter implements Presenter {
	private final GetCollectionUsecase mGetCollectionUsecase;
	private final Context mActivityContext;
	private int mCharacterId;
	private String mCollectionType;
	private CollectionView mCollectionView;

	@Inject
	public CollectionPresenter(GetCollectionUsecase getCollectionUsecase, Context activityContext) {
		mGetCollectionUsecase = getCollectionUsecase;
		mActivityContext = activityContext;
	}

	@Override public void onStart() {}

	@Override public void onStop() {}

	@Override
	public void onPause() {}

	@Override
	public void attachView(View v) {
		mCollectionView = (CollectionView) v;
	}

	@Override
	public void onCreate() {
		mGetCollectionUsecase.setType(mCollectionType);
		mGetCollectionUsecase.execute()
			.subscribe(this::onCollectionItemsReceived);
	}

	public void initialisePresenters(String collectionType, int characterId) {
		mCollectionType = collectionType;
		mCharacterId = characterId;
	}

	private void onCollectionItemsReceived(List<CollectionItem> items) {
		mCollectionView.hideLoadingIndicator();
		mCollectionView.showItems(items);
	}
}
