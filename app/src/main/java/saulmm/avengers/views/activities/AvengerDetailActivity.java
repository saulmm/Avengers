/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.views.activities;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.transition.Transition;
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
import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import java.util.List;
import javax.inject.Inject;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.ButterKnifeUtils;
import saulmm.avengers.R;
import saulmm.avengers.TransitionUtils;
import saulmm.avengers.injector.components.DaggerAvengerInformationComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.model.entities.Comic;
import saulmm.avengers.mvp.presenters.AvengerDetailPresenter;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.views.utils.AnimUtils;

public class AvengerDetailActivity extends AppCompatActivity implements AvengersDetailView {

    private static final String CHRT_NAME_EXTRA = "extra_character_name";
    @Bind(R.id.activity_avenger_detail_progress)      ProgressBar mProgress;
    @Bind(R.id.activity_avenger_comics_progress)      ProgressBar mComicsProgress;
    @Bind(R.id.activity_avenger_comics_container)     LinearLayout mComicsContainer;
    @Bind(R.id.activity_avenger_detail_biography)     TextView mBiographyTextView;
    @Bind(R.id.activity_avenger_detail_name)          TextView mCharacterNameTextView;
    @Bind(R.id.activity_avenger_detail_thumb)         ImageView mAvengerThumb;
    @Bind(R.id.activity_avenger_detail_colltoolbar)   CollapsingToolbarLayout mCollapsingActionBar;
    @Bind(R.id.activity_avenger_detail_appbar)        AppBarLayout mAppbar;
    @Bind(R.id.activity_avenger_reveal_view)          View mRevealView;
    @Bind(R.id.activity_avenger_detail_stats_view)    View mDetailStatsView;

    @Bind(R.id.activity_detail_comics_scroll)           NestedScrollView mComicsNestedScroll;
    @Bind(R.id.activity_avenger_detail_comics_header)   TextView mComicsHeaderTextView;
    @Bind(R.id.activity_avenger_detail_filter_button)   Button mFilterComicsButton;

    @Bind({ R.id.activity_avenger_detail_comics_header,
        R.id.activity_avenger_detail_inf_header})       List<TextView> mHeaderTextViews;

    @BindInt(R.integer.duration_medium)                 int mAnimMediumDuration;
    @BindInt(R.integer.duration_huge)                   int mAnimHugeDuration;
    @BindColor(R.color.brand_primary_dark)              int mColorPrimaryDark;

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
    }

    @Override public void initActivityColors(Bitmap sourceBitmap) {
        Palette.from(sourceBitmap)
            .generate(palette -> {

                int accentColor = getResources().getColor(R.color.brand_accent);
                int darkVibrant = palette.getDarkVibrantColor(accentColor);

                mCollapsingActionBar.setBackgroundColor(darkVibrant);
                mCollapsingActionBar.setStatusBarScrimColor(darkVibrant);
                mCollapsingActionBar.setContentScrimColor(darkVibrant);
                mRevealView.setBackgroundColor(darkVibrant);

                ValueAnimator colorAnimation = ValueAnimator.ofArgb(mColorPrimaryDark, darkVibrant);
                colorAnimation.addUpdateListener(animator -> {
                    mRevealView.setBackgroundColor((Integer) animator.getAnimatedValue());
                });
                colorAnimation.start();

                mDetailStatsView.setBackgroundColor(darkVibrant);
                mDetailStatsView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override public void onGlobalLayout() {
                            mDetailStatsView.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);

                            AnimUtils.showRevealEffect(mDetailStatsView,
                                mDetailStatsView.getWidth() / 2, 0, null);
                        }
                    });



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

        String characterTitle = getIntent().getStringExtra(
            AvengersListActivity.EXTRA_CHARACTER_NAME);
        mCharacterNameTextView.setTransitionName(sharedViewName);
        mCharacterNameTextView.setText(characterTitle);

        Transition enterTransition = TransitionUtils.buildSlideTransition(Gravity.BOTTOM);
        enterTransition.setDuration(mAnimMediumDuration);

        getWindow().setEnterTransition(enterTransition);
        getWindow().setReturnTransition(TransitionUtils.buildSlideTransition(Gravity.BOTTOM));

        mCollapsingActionBar.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override public void onGlobalLayout() {
                    mCollapsingActionBar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int width = mRevealView.getWidth();
                    int height = mRevealView.getHeight();

                    AnimUtils.showRevealEffect(mRevealView, width / 2, height / 2, null);
                }
            });
    }

    @Override
    public void hideRevealViewByAlpha() {
        mRevealView.animate().alpha(0f).setDuration(mAnimHugeDuration).start();
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
        Glide.with(this).load(url)
            .asBitmap().into(new BitmapImageViewTarget(mAvengerThumb) {
            @Override public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                mAvengerThumb.setImageBitmap(resource);
                avengerDetailPresenter.onCharacterBitmapReceived(resource);
            }
        });;
    }

    @Override
    public void showAvengerName(String name) {
        mCollapsingActionBar.setTitle(name);
        mCharacterNameTextView.setText(name);
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
            .setTitle(getString(R.string.dialog_error))
            .setPositiveButton(getString(R.string.action_accept), (dialog, which) -> finish())
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
            .setTitle(getString(R.string.dialog_filter))
            .setPositiveButton(getString(R.string.action_accept), (dialog, which) -> avengerDetailPresenter.onDialogButton(which))
            .setNegativeButton(getString(R.string.action_cancel), (dialog1, which) -> avengerDetailPresenter.onDialogButton(which))
            .setView(filterView)
            .show();
    }
}
