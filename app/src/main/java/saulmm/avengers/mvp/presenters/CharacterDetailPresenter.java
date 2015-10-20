/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import javax.inject.Inject;
import rx.Subscription;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.mvp.views.CharacterDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.CharacterListListActivity;

public class CharacterDetailPresenter implements Presenter {

    private final Context mActivityContext;
    private CharacterDetailView mCharacterDetailView;

    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;
    private final GetCharacterComicsUsecase mGetCharacterComicsUsecase;
    private Intent mIntent;

    private Subscription mCharacterSubscription;

    @Inject
    public CharacterDetailPresenter(GetCharacterInformationUsecase getCharacterInformationUsecase,
        GetCharacterComicsUsecase getCharacterComicsUsecase, Context activityContext) {

        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
        mGetCharacterComicsUsecase = getCharacterComicsUsecase;
        mActivityContext = activityContext;
    }

    @Override
    public void onCreate() {
        // Unused
    }

    @Override
    public void onStart() {
        // Unused
    }

    @Override
    public void onStop() {
        if (!mCharacterSubscription.isUnsubscribed())
            mCharacterSubscription.unsubscribe();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {

        mCharacterDetailView = (CharacterDetailView) v;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

        mIntent = intent;
    }

    @SuppressWarnings("Convert2MethodRef")
    public void initializePresenter() {
        String characterName = mIntent.getExtras().getString(
            CharacterListListActivity.EXTRA_CHARACTER_NAME);

        mCharacterDetailView.startLoading();

        mCharacterSubscription = mGetCharacterInformationUsecase.execute().subscribe(
            this::onCharacterReceived, this::manageCharacterError);

        mCharacterDetailView.startLoading();
        mCharacterDetailView.showAvengerName(characterName);
    }

    private void manageCharacterError(Throwable error) {
        // TODO
    }

    private void onCharacterReceived(Character character) {
        mCharacterDetailView.stopLoadingAvengersInformation();
        mCharacterDetailView.showAvengerBio(
            (character.getDescription().equals("")) ? "No biography available"
                : character.getDescription());

        mCharacterDetailView.showComicsAmount(character.getComics().getAvailable());
        mCharacterDetailView.showStoriesAmount(character.getStories().getAvailable());
        mCharacterDetailView.showEventsAmount(character.getEvents().getAvailable());
        mCharacterDetailView.showSeriesAmount(character.getSeries().getAvailable());

        if (character.getImageUrl() != null)
            mCharacterDetailView.showAvengerImage(character.getImageUrl());
    }

    public void onCharacterBitmapReceived(Bitmap resource) {
        mCharacterDetailView.hideRevealViewByAlpha();
        mCharacterDetailView.initActivityColors(resource);
    }
}
