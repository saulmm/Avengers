/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.views.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

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


public class AvengersListActivity extends AppCompatActivity
    implements AvengersView {

    public final static String EXTRA_CHARACTER_ID = "character_id";

    @InjectView(R.id.activity_avengers_recycler)    RecyclerView mAvengersRecycler;
    @InjectView(R.id.activity_avengers_toolbar)     Toolbar mAvengersToolbar;
    @InjectView(R.id.activity_avengers_progress)    ProgressBar mAvengersProgress;
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mAvengersRecycler.setLayoutManager(linearLayoutManager);
        mAvengersRecycler.addOnScrollListener(mOnScrollListener);
    }

    @Override
    public void showAvengersList(List<Character> avengers) {

        AvengersListAdapter avengersListAdapter = new AvengersListAdapter(avengers,
            this, mAvengersListPresenter);

        mAvengersRecycler.setAdapter(avengersListAdapter);
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
    public void showGenericError() {

        Snackbar.make(mAvengersRecycler, "An error has occurred loading more characters", Snackbar.LENGTH_LONG)
            .setAction("Try again", v -> mAvengersListPresenter.onErrorRetryRequest());
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
}
