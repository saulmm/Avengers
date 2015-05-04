package saulmm.avengers.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.injector.AppModule;
import saulmm.avengers.injector.components.DaggerAvengersComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengersModule;
import saulmm.avengers.model.Character;
import saulmm.avengers.mvp.presenters.AvengersListPresenter;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.views.adapter.AvengersListAdapter;


public class AvengersListActivity extends Activity implements AvengersView {

    @InjectView(R.id.activity_avengers_recycler) RecyclerView mAvengersRecycler;
    @Inject AvengersListPresenter avengersListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avengers_list);
        ButterKnife.inject(this);

        initializeRecyclerView();
        initializeDependencyInjector();
        initializePresenter();
    }

    private void initializePresenter() {

        avengersListPresenter.attachView(this);
    }

    private void initializeDependencyInjector() {

        AvengersApplication avengersApplication = (AvengersApplication) getApplication();

        DaggerAvengersComponent.builder()
            .avengersModule(new AvengersModule())
            .activityModule(new ActivityModule(this))
            .appModule(new AppModule(avengersApplication))
            .build().inject(this);
    }

    private void initializeRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAvengersRecycler.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void showAvengersList(List<Character> avengers) {

        AvengersListAdapter avengersListAdapter = new AvengersListAdapter(avengers, this);
        mAvengersRecycler.setAdapter(avengersListAdapter);
    }
}
