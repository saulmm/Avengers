package saulmm.avengers.views.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.List;
import javax.inject.Inject;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.injector.components.DaggerAvengerInformationComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.mvp.presenters.CollectionPresenter;
import saulmm.avengers.mvp.views.CollectionView;

public class CollectionActivity extends AppCompatActivity implements CollectionView {
	private final static String EXTRA_CHARACTER_ID 		= "character_id";
	private final static String EXTRA_COLLECTION_TYPE 	= "collection_type";

	@Bind(R.id.collection_list) RecyclerView mCollectionRecycler;
	@Bind(R.id.collection_loading) ProgressBar mLoadingIndicator;
	@Inject CollectionPresenter mCollectionPresenter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_character_collection);
		ButterKnife.bind(this);

		initDependencyInjector();
		initializePresenter();
	}

	private void initDependencyInjector() {
		int avengerId = getIntent().getIntExtra(EXTRA_CHARACTER_ID, -1);

		DaggerAvengerInformationComponent.builder()
			.activityModule(new ActivityModule(this))
			.appComponent(((AvengersApplication) getApplication()).getAppComponent())
			.avengerInformationModule(new AvengerInformationModule(avengerId))
			.build().inject(this);
	}

	private void initializePresenter() {
		int characterId = getIntent().getIntExtra(EXTRA_CHARACTER_ID, -1);
		String collectionType = getIntent().getStringExtra(EXTRA_COLLECTION_TYPE);
		mCollectionPresenter.attachView(this);
		mCollectionPresenter.initialisePresenters(collectionType, characterId);
		mCollectionPresenter.onCreate();
	}

	@Override
	public void showLoadingIndicator() {
		mLoadingIndicator.setVisibility(View.VISIBLE);
	}

	@Override
	public void hideLoadingIndicator() {
		mLoadingIndicator.setVisibility(View.GONE);
	}

	@Override
	public void showItems(List<CollectionItem> items) {
		mCollectionRecycler.setAdapter(new CollectionAdapter(items));
	}

	public static void start(Context context, int characterId, String type) {
		Intent collectionIntent = new Intent(context, CollectionActivity.class);
		collectionIntent.putExtra(EXTRA_CHARACTER_ID, characterId);
		collectionIntent.putExtra(EXTRA_COLLECTION_TYPE, type);
		context.startActivity(collectionIntent);
	}


	private class CollectionAdapter extends RecyclerView.Adapter<CollectionItemViewHolder> {
		private List<CollectionItem> mCollectionItems;

		public CollectionAdapter(List<CollectionItem> collectionItems) {
			mCollectionItems = collectionItems;
		}

		@Override
		public CollectionItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View rootView = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.item_comic, parent, false);

			return new CollectionItemViewHolder(rootView);
		}

		@Override
		public void onBindViewHolder(CollectionItemViewHolder holder, int position) {
			holder.bindItem(mCollectionItems.get(position));
		}

		@Override public int getItemCount() {
			return mCollectionItems.size();
		}
	}

	class CollectionItemViewHolder extends RecyclerView.ViewHolder {
		@Bind(R.id.item_title) TextView itemTitleTextView;
		@Bind(R.id.item_image) ImageView itemImageView;
		@Bind(R.id.item_text) TextView itemTextTextView;

		public CollectionItemViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}

		public void bindItem(CollectionItem collectionItem) {
			itemTitleTextView.setText(collectionItem.getTitle());
			itemTextTextView.setText(collectionItem.getDescription());

			if (collectionItem.getThumbnail() != null) {
				Glide.with(CollectionActivity.this)
					.load(collectionItem.getThumbnail().getImageUrl())
					.error(R.drawable.error_placeholder)
					.into(itemImageView);

			} else {
				itemTextTextView.setVisibility(View.GONE);
			}
		}
	}
}
