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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.transition.Transition;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.BindInt;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import javax.inject.Inject;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.databinding.ActivityAvengerDetailBinding;
import saulmm.avengers.entities.CollectionItem;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.injector.components.DaggerAvengerInformationComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.mvp.presenters.CharacterDetailPresenter;
import saulmm.avengers.mvp.views.CharacterDetailView;
import saulmm.avengers.utils.OnCharacterImageCallback;
import saulmm.avengers.utils.TransitionUtils;
import saulmm.avengers.views.utils.AnimUtils;

public class CharacterDetailActivity extends AppCompatActivity implements CharacterDetailView {
    private static final String EXTRA_CHARACTER_NAME    = "character.name";
    public static final String EXTRA_CHARACTER_ID       = "character.id";

    @Bind(R.id.character_collapsing)                    CollapsingToolbarLayout mCollapsing;
    @BindInt(R.integer.duration_medium)                 int mAnimMediumDuration;
    @BindInt(R.integer.duration_huge)                   int mAnimHugeDuration;
    @BindColor(R.color.brand_primary_dark)              int mColorPrimaryDark;

    @Inject CharacterDetailPresenter mCharacterDetailPresenter;
    private ActivityAvengerDetailBinding mBinding;

    private OnCharacterImageCallback onCharacterImageCallback = new OnCharacterImageCallback() {

        @Override
        public void onReceive(Bitmap bitmap) {
            initActivityColors(bitmap);
        }
    };

    private int mScrolleableAppbarLayoutFlags;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeDependencyInjector();
        initializeBinding();
        initButterknife();
        initializePresenter();
        initToolbar();
        initTransitions();
    }

    @Override
    public void disableScroll() {
        mScrolleableAppbarLayoutFlags = ((AppBarLayout.LayoutParams) mCollapsing.getLayoutParams()).getScrollFlags();
        AppBarLayout.LayoutParams layoutParams = ((AppBarLayout.LayoutParams) mCollapsing.getLayoutParams());
        layoutParams.setScrollFlags(0);
        mCollapsing.setLayoutParams(layoutParams);
    }

    private void initializeBinding() {
        mBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_avenger_detail);

        mBinding.setImageCallback(onCharacterImageCallback);
    }


    public void initActivityColors(Bitmap sourceBitmap) {
        Palette.from(sourceBitmap)
            .generate(palette -> {
                View statsRevealView = mBinding.characterStatsReveal;
                View imageCover = mBinding.characterImageReveal;
                int darkVibrant = palette.getDarkVibrantColor(mColorPrimaryDark);

                mBinding.setDarkSwatch(palette.getDarkVibrantSwatch());

                ValueAnimator colorAnimation = ValueAnimator.ofArgb(mColorPrimaryDark, darkVibrant);
                colorAnimation.setDuration(mAnimHugeDuration);
                colorAnimation.addUpdateListener(animator -> {
                    imageCover.setBackgroundColor((Integer) animator.getAnimatedValue());
                });

                colorAnimation.start();

                statsRevealView.getViewTreeObserver().addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override public void onGlobalLayout() {
                            statsRevealView.getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);

                            AnimUtils.showRevealEffect(statsRevealView,
                                statsRevealView.getWidth() / 2, 0, null);

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
            CharacterListActivity.EXTRA_IMAGE_TRANSITION_NAME);

        String characterTitle = getIntent().getStringExtra(
            CharacterListActivity.EXTRA_CHARACTER_NAME);

        Transition enterTransition = TransitionUtils.buildSlideTransition(Gravity.BOTTOM);
        enterTransition.setDuration(mAnimMediumDuration);

        getWindow().setEnterTransition(enterTransition);
        getWindow().setReturnTransition(TransitionUtils.buildSlideTransition(Gravity.BOTTOM));

        View collapsingToolbar = mBinding.characterCollapsing;
        View imageReveal = mBinding.characterImageReveal;
        collapsingToolbar.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override public void onGlobalLayout() {
                    collapsingToolbar.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    int width = imageReveal.getWidth();
                    int height = imageReveal.getHeight();

                    AnimUtils.showRevealEffect(imageReveal, width / 2, height / 2, null);
                }
            });
    }

    @Override
    public void hideRevealViewByAlpha() {
        mBinding.characterImageReveal.animate().alpha(0f).setDuration(
            mAnimHugeDuration).start();
    }

    private void initToolbar() {
        mBinding.characterToolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void showError(String errorMessage) {
        new AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_error))
            .setPositiveButton(getString(R.string.action_accept), (dialog, which) -> finish())
            .setMessage(errorMessage)
            .setCancelable(false).show();
    }

    @Override
    public void bindCharacter(MarvelCharacter character) {
        mBinding.setCharacter(character);
    }

    @Override
    public void enableScroll() {
        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) mCollapsing.getLayoutParams();
        layoutParams.setScrollFlags(mScrolleableAppbarLayoutFlags);
        mCollapsing.setLayoutParams(layoutParams);
    }

    @Override
    public void goToCharacterComicsView(int characterId) {
        CollectionActivity.start(this, characterId, CollectionItem.COMICS);
    }

    @Override
    public void goToCharacterSeriesView(int characterId) {
        CollectionActivity.start(this, characterId, CollectionItem.SERIES);
    }

    @Override
    public void goToCharacterEventsView(int characterId) {
        CollectionActivity.start(this, characterId, CollectionItem.EVENTS);
    }

    @Override
    public void goToCharacterStoriesView(int characterId) {
        CollectionActivity.start(this, characterId, CollectionItem.STORIES);
    }

    @BindingAdapter({"source", "presenter", "callback"})
    public static void setImageSource(ImageView v, String url,
          CharacterDetailPresenter detailPresenter, OnCharacterImageCallback imageCallback) {

        Glide.with(v.getContext()).load(url).asBitmap().into(new BitmapImageViewTarget(v) {
            @Override public void onResourceReady(Bitmap resource,
                GlideAnimation<? super Bitmap> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                v.setImageBitmap(resource);
                imageCallback.onReceive(resource);
                detailPresenter.onImageReceived();

            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCharacterDetailPresenter.onStop();
    }

    public static void start(Context context, String characterName, int characterId) {
        Intent characterDetailItent = new Intent(context, CharacterDetailActivity.class);
        characterDetailItent.putExtra(EXTRA_CHARACTER_NAME, characterName);
        characterDetailItent.putExtra(EXTRA_CHARACTER_ID, characterId);
        context.startActivity(characterDetailItent);
    }
}
