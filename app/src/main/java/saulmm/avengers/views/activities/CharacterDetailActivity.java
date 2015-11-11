/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.views.activities;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import javax.inject.Inject;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.TransitionUtils;
import saulmm.avengers.databinding.ActivityAvengerDetailBinding;
import saulmm.avengers.injector.components.DaggerAvengerInformationComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.mvp.presenters.CharacterDetailPresenter;
import saulmm.avengers.mvp.views.CharacterDetailView;
import saulmm.avengers.views.utils.AnimUtils;

public class CharacterDetailActivity extends AppCompatActivity implements CharacterDetailView {

    private static final String EXTRA_CHARACTER_NAME    = "character.name";
    public static final String EXTRA_CHARACTER_ID       = "character.id";

    @Bind(R.id.activity_avenger_detail_progress)        ProgressBar mProgress;
    @Bind(R.id.activity_avenger_detail_biography)       TextView mBiographyTextView;
    @Bind(R.id.character_events) TextView mEventsAmountTextView;
    @Bind(R.id.character_series) TextView mSeriesAmountTextView;
    @Bind(R.id.character_stories) TextView mStoriesAmountTextView;
    @Bind(R.id.character_comics) TextView mComicsAmountTextView;
    @Bind(R.id.character_image)           ImageView mAvengerThumb;
    @Bind(R.id.character_collapsing)     CollapsingToolbarLayout mCollapsingActionBar;
    @Bind(R.id.activity_avenger_detail_appbar)          AppBarLayout mAppbar;
    @Bind(R.id.character_image_reveal)            View mRevealView;
    @Bind(R.id.character_stats)      View mDetailStatsView;

    @Bind(R.id.character_nestedscroll)           NestedScrollView mComicsNestedScroll;

    @BindInt(R.integer.duration_medium)                 int mAnimMediumDuration;
    @BindInt(R.integer.duration_huge)                   int mAnimHugeDuration;
    @BindColor(R.color.brand_primary_dark)              int mColorPrimaryDark;

    @Inject CharacterDetailPresenter mCharacterDetailPresenter;
    private ActivityAvengerDetailBinding mBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeBinding();
        initButterknife();
        initializeDependencyInjector();
        initializePresenter();
        initToolbar();
        initTransitions();
    }

    private void initializeBinding() {
        mBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_avenger_detail);
    }


    public void initActivityColors(Bitmap sourceBitmap) {
        Palette.from(sourceBitmap)
            .generate(palette -> {

                int accentColor = getResources().getColor(R.color.brand_accent);
                int darkVibrant = palette.getDarkVibrantColor(accentColor);
                mBinding.setDarkVibrantColor(darkVibrant);

                mRevealView.setBackgroundColor(darkVibrant);

                ValueAnimator colorAnimation = ValueAnimator.ofArgb(mColorPrimaryDark, darkVibrant);
                colorAnimation.addUpdateListener(animator -> {
                    mRevealView.setBackgroundColor((Integer) animator.getAnimatedValue());
                });
                colorAnimation.start();

                mDetailStatsView.getViewTreeObserver()
                    .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override public void onGlobalLayout() {
                            mDetailStatsView.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);

                            AnimUtils.showRevealEffect(mDetailStatsView,
                                mDetailStatsView.getWidth() / 2, 0, null);
                        }
                    });

                getWindow().setStatusBarColor(darkVibrant);
            });
    }


    private void initButterknife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCharacterDetailPresenter.onStart();
    }

    private void initializePresenter() {
        int characterId = getIntent().getIntExtra(EXTRA_CHARACTER_ID, -1);
        String characterName = getIntent().getStringExtra(EXTRA_CHARACTER_NAME);

        mCharacterDetailPresenter.attachView(this);
        mCharacterDetailPresenter.setCharacterId(characterId);
        mCharacterDetailPresenter.initializePresenter(characterId, characterName);
        mCharacterDetailPresenter.onCreate();
        mBinding.setPresenter(mCharacterDetailPresenter);
    }

    private void initializeDependencyInjector() {
        AvengersApplication avengersApplication = (AvengersApplication) getApplication();

        int avengerId = getIntent().getIntExtra(EXTRA_CHARACTER_ID, -1);

        DaggerAvengerInformationComponent.builder()
            .activityModule(new ActivityModule(this))
            .appComponent(avengersApplication.getAppComponent())
            .avengerInformationModule(new AvengerInformationModule(avengerId))
            .build().inject(this);
    }

    private void initTransitions() {
        final String sharedViewName = getIntent().getStringExtra(
            CharacterListListActivity.EXTRA_IMAGE_TRANSITION_NAME);

        String characterTitle = getIntent().getStringExtra(
            CharacterListListActivity.EXTRA_CHARACTER_NAME);

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
    public void showError(String errorMessage) {
        new AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_error))
            .setPositiveButton(getString(R.string.action_accept), (dialog, which) -> finish())
            .setMessage(errorMessage)
            .setCancelable(false)
            .show();
    }

    @Override
    public void bindCharacter(saulmm.avengers.model.entities.Character character) {
        mBinding.setCharacter(character);
    }


    @BindingAdapter({"bind:characterImage", "bind:presenter"})
    public static void setImageSource(ImageView v, String url, CharacterDetailPresenter detailPresenter) {
        Glide.with(v.getContext()).load(url).asBitmap().into(new BitmapImageViewTarget(v) {
            @Override public void onResourceReady(Bitmap resource,
                GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                v.setImageBitmap(resource);

                detailPresenter.onImageReceived(resource);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCharacterDetailPresenter.onStop();
    }

    @OnClick(R.id.character_comics_container)
    protected void onComicsIndicator() {
        mCharacterDetailPresenter.onComicsIndicatorPressed();
    }

    @OnClick(R.id.character_events_container)
    protected void onEventsIndicator() {
        mCharacterDetailPresenter.onEventIndicatorPressed();
    }

    @OnClick(R.id.character_stories_container)
    protected void onStoriesIndicator() {
        mCharacterDetailPresenter.onStoriesIndicatorPressed();
    }

    @OnClick(R.id.character_series_container)
    protected void onSeriesIndicator() {
        mCharacterDetailPresenter.onSeriesIndicatorPressed();
    }

    public static void start(Context context, String characterName, int characterId) {
        Intent characterDetailItent = new Intent(context, CharacterDetailActivity.class);
        characterDetailItent.putExtra(EXTRA_CHARACTER_NAME, characterName);
        characterDetailItent.putExtra(EXTRA_CHARACTER_ID, characterId);
        context.startActivity(characterDetailItent);
    }
}
