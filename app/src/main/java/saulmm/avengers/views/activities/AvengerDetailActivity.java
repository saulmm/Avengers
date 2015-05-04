package saulmm.avengers.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.injector.components.DaggerAvengerInformationComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.mvp.presenters.AvengerDetailPresenter;
import saulmm.avengers.mvp.views.AvengersDetailView;

public class AvengerDetailActivity extends Activity implements AvengersDetailView {

    @InjectView(R.id.activity_avenger_detail_progress)  ProgressBar mProgress;
    @InjectView(R.id.activity_avenger_detail_biography) TextView mBiographyTextView;

    @Inject AvengerDetailPresenter avengerDetailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avenger_detail);
        ButterKnife.inject(this);

        initializeDependencyInjector();
        initializePresenter();
    }

    private void initializePresenter() {

        avengerDetailPresenter.attachView(this);
    }

    private void initializeDependencyInjector() {

        AvengersApplication avengersApplication = (AvengersApplication) getApplication();

        DaggerAvengerInformationComponent.builder()
            .activityModule(new ActivityModule(this))
            .appComponent(avengersApplication.getAppComponent())
            .avengerInformationModule(new AvengerInformationModule("ID"))
            .build().inject(this);
    }

    @Override
    public void startLoading() {

        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {

        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showAvengerBio(String text) {

        mBiographyTextView.setVisibility(View.VISIBLE);
    }
}
