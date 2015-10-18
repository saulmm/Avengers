/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.views.activities;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;
import java.util.List;
import javax.inject.Inject;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.Utils;
import saulmm.avengers.injector.components.DaggerAvengersComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.mvp.presenters.AvengersListPresenter;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.views.adapter.AvengersListAdapter;
import saulmm.avengers.views.views.RecyclerInsetsDecoration;


public class AvengersListActivity extends AppCompatActivity
    implements AvengersView {

    public final static String EXTRA_CHARACTER_ID           = "character_id";
    public final static String EXTRA_CHARACTER_NAME         = "character_name";
    public final static String EXTRA_IMAGE_TRANSITION_NAME  = "transition_name";
    public final static int KEY_SHARED_BITMAP               = 41;

    @Bind(R.id.activity_avengers_recycler)        RecyclerView mAvengersRecycler;
    @Bind(R.id.activity_avengers_toolbar)         Toolbar mAvengersToolbar;
    @Bind(R.id.activity_avengers_progress)        ProgressBar mAvengersProgress;

    @Bind(R.id.activity_avengers_empty_indicator) View mEmptyIndicator;
    @Bind(R.id.activity_avengers_error_view)      View mErrorView;

    @Inject AvengersListPresenter mAvengersListPresenter;

    private AvengersListAdapter mCharacterListAdapter;
    private Snackbar mLoadingMoreCharactersSnack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avengers_list);
        ButterKnife.bind(this);

        initializeToolbar();
        initializeRecyclerView();
        initializeDependencyInjector();
        initializePresenter();
    }

    private void initializeToolbar() {
        setSupportActionBar(mAvengersToolbar);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAvengersListPresenter.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAvengersListPresenter.onPause();
    }

    private void initializePresenter() {
        mAvengersListPresenter.attachView(this);
        mAvengersListPresenter.onCreate();
    }

    private void initializeDependencyInjector() {
        AvengersApplication avengersApplication = (AvengersApplication) getApplication();

        DaggerAvengersComponent.builder()
            .activityModule(new ActivityModule(this))
            .appComponent(avengersApplication.getAppComponent())
            .build().inject(this);
    }

    private void initializeRecyclerView() {
        mAvengersRecycler.setLayoutManager(new LinearLayoutManager(this));
        mAvengersRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        mAvengersRecycler.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void bindCharacterList(List<Character> avengers) {
        mCharacterListAdapter = new AvengersListAdapter(avengers,
            this, mAvengersListPresenter);

        mAvengersRecycler.setAdapter(mCharacterListAdapter);
    }

    @Override
    public void showCharacterList() {
        if (mAvengersRecycler.getVisibility() == View.GONE ||
            mAvengersRecycler.getVisibility() == View.INVISIBLE)

            mAvengersRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void updateCharacterList(int charactersAdded) {
        mCharacterListAdapter.notifyItemRangeInserted(
            mCharacterListAdapter.getItemCount() + charactersAdded, charactersAdded);
    }

    @Override
    public void hideAvengersList() {
        mAvengersRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingMoreCharactersIndicator() {
        mLoadingMoreCharactersSnack = Snackbar.make(mAvengersRecycler,
            getString(R.string.message_loading_more_characters), Snackbar.LENGTH_INDEFINITE);

        mLoadingMoreCharactersSnack.show();
    }

    @Override
    public void hideLoadingMoreCharactersIndicator() {
        if (mLoadingMoreCharactersSnack != null) mLoadingMoreCharactersSnack.dismiss();
    }

    @Override
    public void hideLoadingIndicator() {
        mLoadingMoreCharactersSnack.dismiss();
    }

    @Override
    public void showLoadingView() {
        mEmptyIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        mEmptyIndicator.setVisibility(View.GONE);
    }

    @Override
    public void showLightError() {
        Snackbar.make(mAvengersRecycler, getString(R.string.error_loading_characters), Snackbar.LENGTH_LONG)
            .setAction(R.string.try_again, v -> mAvengersListPresenter.onErrorRetryRequest())
            .show();
    }

    @Override
    public void showErrorView(String errorMessage) {
        TextView errorTextView = ButterKnife.findById(mErrorView, R.id.view_error_message);
        errorTextView.setText(errorMessage);

        Button errorRetryButton = ButterKnife.findById(mErrorView, R.id.view_error_retry_button);
        errorRetryButton.setOnClickListener(v -> mAvengersListPresenter.onErrorRetryRequest());

        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorView() {
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyIndicator() {
        mEmptyIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyIndicator() {
        mEmptyIndicator.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAvengersListPresenter.onStop();
    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemsCount   = layoutManager.getChildCount();
            int totalItemsCount     = layoutManager.getItemCount();
            int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {
                mAvengersListPresenter.onListEndReached();
            }
        }
    };

    @Override
    public ActivityOptions getActivityOptions(int position, View clickedView) {
        String sharedViewName = Utils.getListTransitionName(position);
        return ActivityOptions.makeSceneTransitionAnimation(
            this, clickedView, sharedViewName);
    }
}
