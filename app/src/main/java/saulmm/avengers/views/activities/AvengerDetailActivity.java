/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.views.activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import java.util.List;
import javax.inject.Inject;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.ButterKnifeUtils;
import saulmm.avengers.R;
import saulmm.avengers.TransitionUtils;
import saulmm.avengers.Utils;
import saulmm.avengers.injector.components.DaggerAvengerInformationComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.model.entities.Comic;
import saulmm.avengers.mvp.presenters.AvengerDetailPresenter;
import saulmm.avengers.mvp.views.AvengersDetailView;

public class AvengerDetailActivity extends AppCompatActivity implements AvengersDetailView {

    @Bind(R.id.activity_avenger_detail_progress)      ProgressBar mProgress;
    @Bind(R.id.activity_avenger_comics_progress)      ProgressBar mComicsProgress;
    @Bind(R.id.activity_avenger_comics_container)     LinearLayout mComicsContainer;
    @Bind(R.id.activity_avenger_detail_biography)     TextView mBiographyTextView;
    @Bind(R.id.activity_avenger_detail_name)          TextView mCharacterNameTextView;
    @Bind(R.id.activity_avenger_detail_thumb)         ImageView mAvengerThumb;
    @Bind(R.id.activity_avenger_detail_colltoolbar)   CollapsingToolbarLayout mCollapsingActionBar;
    @Bind(R.id.activity_avenger_detail_appbar)        AppBarLayout mAppbar;

    @Bind(R.id.activity_detail_comics_scroll)           NestedScrollView mComicsNestedScroll;
    @Bind(R.id.activity_avenger_detail_comics_header)   TextView mComicsHeaderTextView;
    @Bind(R.id.activity_avenger_detail_filter_button)   Button mFilterComicsButton;
    @Bind({ R.id.activity_avenger_detail_comics_header, R.id.activity_avenger_detail_inf_header})
    List<TextView> mHeaderTextViews;

    @Inject AvengerDetailPresenter avengerDetailPresenter;

    private View comicView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initButterknife();
        initializeDependencyInjector();
        initializePresenter();
        initToolbar();
        initTransitions();
        initActivityColors();
    }

    private void initActivityColors() {

        final Bitmap sourceBitmap = AvengersListActivity.sPhotoCache
            .get(AvengersListActivity.KEY_SHARED_BITMAP);

        Palette.from(sourceBitmap)
            .generate(palette -> {

                int accentColor = getResources().getColor(R.color.brand_accent);
                int darkVibrant = palette.getDarkVibrantColor(accentColor);

                mCollapsingActionBar.setBackgroundColor(darkVibrant);
                mCollapsingActionBar.setStatusBarScrimColor(darkVibrant);
                mCollapsingActionBar.setContentScrimColor(darkVibrant);
                getWindow().setStatusBarColor(darkVibrant);

                ButterKnife.apply(mHeaderTextViews, ButterKnifeUtils.TEXTCOLOR, darkVibrant);
            });
    }

    private void initButterknife() {

        setContentView(R.layout.activity_avenger_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {

        super.onStart();
        avengerDetailPresenter.onStart();
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

    private void initTransitions() {

        final String sharedViewName = getIntent().getStringExtra(
            AvengersListActivity.EXTRA_IMAGE_TRANSITION_NAME);

        final Bitmap characterThumbBitmap = AvengersListActivity.sPhotoCache
            .get(AvengersListActivity.KEY_SHARED_BITMAP);

        mAvengerThumb.setImageBitmap(characterThumbBitmap);
        mAvengerThumb.setTransitionName(sharedViewName);

        getWindow().setEnterTransition(TransitionUtils.buildExplodeTransition());
        getWindow().setReenterTransition(TransitionUtils.buildSlideTransition(
            Gravity.BOTTOM));

        mCollapsingActionBar.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override public void onGlobalLayout() {
                    mCollapsingActionBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    //int width = mAvengerBackground.getWidth();
                    //int height = mAvengerBackground.getHeight();
                    //
                    //AnimUtils.showRevealEffect(mAvengerBackground, width / 2, height / 2, null);
                }
            });
    }

    private void initToolbar() {
        mCollapsingActionBar.setExpandedTitleTextAppearance(R.style.Text_CollapsedExpanded);
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
    public void showAvengerBio(String text) {
        mBiographyTextView.setVisibility(View.VISIBLE);
        mBiographyTextView.setText(text);
    }

    @Override
    public void showAvengerImage(String url) {
        //Glide.with(this).load(url).into(mAvengerImageView);
    }

    @Override
    public void showAvengerName(String name) {

        mCollapsingActionBar.setTitle(name);
        mCharacterNameTextView.setText(name);
        mCharacterNameTextView.setTypeface(Utils.getBangersTypeface(this));
        mCharacterNameTextView.setVisibility(View.VISIBLE);

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

        mComicsContainer.addView(comicView);
    }

    @Override
    public void stopLoadingComicsIfNeeded() {
        if (mComicsProgress.getVisibility() == View.VISIBLE)
            mComicsProgress.setVisibility(View.GONE);
    }

    @Override
    public void clearComicsView() {
        if(mComicsContainer.getChildCount() > 0)
            mComicsContainer.removeAllViews();
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
    public void hideComics() {
        mComicsContainer.setVisibility(View.GONE);
        mComicsHeaderTextView.setVisibility(View.GONE);
        mComicsProgress.setVisibility(View.GONE);
        mFilterComicsButton.setVisibility(View.GONE);
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
