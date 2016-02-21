package saulmm.avengers.mvp.views;

import java.util.List;
import saulmm.avengers.rest.entities.RestCollectionItem;

public interface CollectionView extends View {

	void showLoadingIndicator();

	void hideLoadingIndicator();

	void showItems(List<RestCollectionItem> items);
}
