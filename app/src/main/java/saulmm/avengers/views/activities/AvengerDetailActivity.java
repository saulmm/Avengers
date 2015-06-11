package saulmm.avengers.views.activities;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.injector.components.DaggerAvengerInformationComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.model.Comic;
import saulmm.avengers.mvp.presenters.AvengerDetailPresenter;
import saulmm.avengers.mvp.views.AvengersDetailView;

public class AvengerDetailActivity extends AppCompatActivity implements AvengersDetailView {

    @InjectView(R.id.activity_avenger_detail_progress)      ProgressBar mProgress;
    @InjectView(R.id.activity_avenger_comics_progress)      ProgressBar mComicsProgress;
    @InjectView(R.id.activity_avenger_comics_container)     LinearLayout mDetailContainer;
    @InjectView(R.id.activity_avenger_detail_biography)     TextView mBiographyTextView;
    @InjectView(R.id.activity_avenger_image)                ImageView mAvengerImageView;
    @InjectView(R.id.activity_avenger_detail_toolbar)       Toolbar mDetailToolbar;
    @InjectView(R.id.activity_avenger_detail_colltoolbar)   CollapsingToolbarLayout mDetailCollapsingToolbar;

    @Inject AvengerDetailPresenter avengerDetailPresenter;

    private View comicView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avenger_detail);
        ButterKnife.inject(this);

        initializeToolbar();
        initializeDependencyInjector();
        initializePresenter();
    }

    @Override
    protected void onStart() {

        super.onStart();
        avengerDetailPresenter.onStart();
    }

    private void initializeToolbar() {

        setSupportActionBar(mDetailToolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {

            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

    }

    private void initializePresenter() {

        avengerDetailPresenter.attachView(this);
        avengerDetailPresenter.attachIncomingIntent(getIntent());
        avengerDetailPresenter.initializePresenter();
    }

    private void initializeDependencyInjector() {

        AvengersApplication avengersApplication = (AvengersApplication) getApplication();

        int avengerId = getIntent().getIntExtra(AvengersListActivity.EXTRA_CHARACTER_ID, -1);

        DaggerAvengerInformationComponent.builder()
            .activityModule(new ActivityModule(this))
            .appComponent(avengersApplication.getAppComponent())
            .avengerInformationModule(new AvengerInformationModule(avengerId))
            .build().inject(this);
    }

    @Override
    public void startLoading() {

        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoadingAvengersInformation() {

        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void startLoadingComics() {

        mComicsProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAvengerBio(String text) {

        mBiographyTextView.setVisibility(View.VISIBLE);
        mBiographyTextView.setText(text);
    }

    @Override
    public void showAvengerImage(String url) {

        Glide.with(this).load(url).into(mAvengerImageView);
    }

    @Override
    public void showAvengerName(String name) {

        mDetailCollapsingToolbar.setTitle(name);
    }

    @Override
    public void addComic(Comic comic) {

        View comicView = LayoutInflater.from(this).inflate(
                R.layout.item_comic, null, true);

        TextView comicTitleTextView = ButterKnife.findById(comicView, R.id.item_comic_title);
        TextView comicDescTextView = ButterKnife.findById(comicView, R.id.item_comic_description);
        ImageView comicCoverImageView = ButterKnife.findById(comicView, R.id.item_comic_cover);

        comicTitleTextView.setText(comic.getTitle());

        if (comic.getFirstImageUrl() != null)
            Glide.with(this).load(comic.getFirstImageUrl()).into(comicCoverImageView);

        if (comic.getFirstTextObject() != null)
            comicDescTextView.setText(Html.fromHtml(comic.getFirstTextObject()));

        mDetailContainer.addView(comicView);
    }

    @Override
    public void stopLoadingComicsIfNeeded() {

        if (mComicsProgress.getVisibility() == View.VISIBLE)
            mComicsProgress.setVisibility(View.GONE);
    }

    @Override
    public void clearComicsView() {

        if(mDetailContainer.getChildCount() > 0)
            mDetailContainer.removeAllViews();
    }

    @Override
    public void showError(String errorMessage) {

        stopLoadingAvengersInformation();
        stopLoadingComicsIfNeeded();

        new AlertDialog.Builder(this)
            .setTitle("Error")
            .setPositiveButton("Accept", (dialog, which) -> finish())
            .setMessage(errorMessage)
            .setCancelable(false)
            .show();
    }

    @Override
    protected void onStop() {

        super.onStop();
        avengerDetailPresenter.onStop();
    }

    @OnClick(R.id.activity_avenger_detail_filter_button)
    public void onButtonClicked () {
        showFilterDialog();
    }

    public void showFilterDialog () {

        View filterView = LayoutInflater.from(this)
            .inflate(R.layout.view_filter_dialog, null);

        Spinner yearSpinner = ButterKnife.findById(filterView, R.id.view_filter_dialog_year_spinner);
        yearSpinner.setOnItemSelectedListener(avengerDetailPresenter);

        new AlertDialog.Builder(this)
            .setTitle("Filter")
            .setPositiveButton("Accept", (dialog, which) -> avengerDetailPresenter.onDialogButton(which))
            .setNegativeButton("Cancel", (dialog1, which) -> avengerDetailPresenter.onDialogButton(which))
            .setView(filterView)
            .show();
    }
}
