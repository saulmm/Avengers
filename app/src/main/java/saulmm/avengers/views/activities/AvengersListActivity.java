/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.views.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.injector.components.DaggerAvengersComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.mvp.presenters.AvengersListPresenter;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.views.adapter.AvengersListAdapter;
import saulmm.avengers.views.views.RecyclerInsetsDecoration;


public class AvengersListActivity extends AppCompatActivity
    implements AvengersView {

    public final static String EXTRA_CHARACTER_ID       = "character_id";

    @InjectView(R.id.activity_avengers_recycler)        RecyclerView mAvengersRecycler;
    @InjectView(R.id.activity_avengers_toolbar)         Toolbar mAvengersToolbar;
    @InjectView(R.id.activity_avengers_progress)        ProgressBar mAvengersProgress;
    @InjectView(R.id.activity_avengers_empty_indicator) View mEmptyIndicator;
    @InjectView(R.id.activity_avengers_error_view)      View mErrorView;
    @InjectView(R.id.activity_avenger_title)            TextView mAvengersActivityTitle;
    @Inject AvengersListPresenter mAvengersListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avengers_list);
        ButterKnife.inject(this);

        initializeToolbar();
        initializeRecyclerView();
        initializeDependencyInjector();
        initializePresenter();
    }

    private void initializeToolbar() {

        Typeface bangersFont = Typeface.createFromAsset(getAssets(), "fonts/Bangers.ttf");
        mAvengersActivityTitle.setTypeface(bangersFont);
        setSupportActionBar(mAvengersToolbar);
    }

    @Override
    protected void onStart() {

        super.onStart();
        mAvengersListPresenter.onStart();
    }

    private void initializePresenter() {

        mAvengersListPresenter.attachView(this);
    }

    private void initializeDependencyInjector() {

        AvengersApplication avengersApplication = (AvengersApplication) getApplication();

        DaggerAvengersComponent.builder()
            .activityModule(new ActivityModule(this))
            .appComponent(avengersApplication.getAppComponent())
            .build().inject(this);
    }

    private void initializeRecyclerView() {

        mAvengersRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        mAvengersRecycler.addItemDecoration(new RecyclerInsetsDecoration(this));
        mAvengersRecycler.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void showAvengersList(List<Character> avengers) {

        AvengersListAdapter avengersListAdapter = new AvengersListAdapter(avengers,
            this, mAvengersListPresenter);

        mAvengersRecycler.setAdapter(avengersListAdapter);
        mAvengersRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAvengersList() {

        mAvengersRecycler.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingIndicator() {

        mAvengersProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingIndicator() {

        mAvengersProgress.setVisibility(View.GONE);
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

        Snackbar.make(mAvengersRecycler, "An error has occurred loading characters", Snackbar.LENGTH_LONG)
            .setAction("Try again", v -> mAvengersListPresenter.onErrorRetryRequest())
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
    public void showAvengersList() {

        mAvengersRecycler.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStop() {

        super.onStop();
        mAvengersListPresenter.onStop();
    }

    private OnScrollListener mOnScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            int visibleItemsCount   = layoutManager.getChildCount();
            int totalItemsCount     = layoutManager.getItemCount();
            int firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition();

            if (visibleItemsCount + firstVisibleItemPos >= totalItemsCount) {

                mAvengersListPresenter.onListEndReached();
            }
        }
    };
}
