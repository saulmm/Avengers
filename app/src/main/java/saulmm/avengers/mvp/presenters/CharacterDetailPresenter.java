/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.graphics.Bitmap;
import javax.inject.Inject;
import rx.Subscription;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.model.entities.CollectionItem;
import saulmm.avengers.mvp.views.CharacterDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.CollectionActivity;

public class CharacterDetailPresenter implements Presenter {

    private final Context mActivityContext;
    private CharacterDetailView mCharacterDetailView;

    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;

    private Subscription mCharacterSubscription;
    private int mCharacterId;
    private String mCharacterName;

    @Inject
    public CharacterDetailPresenter(GetCharacterInformationUsecase getCharacterInformationUsecase,
        Context activityContext) {

        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
        mActivityContext = activityContext;
    }

    @Override
    public void onCreate() {
        if (mCharacterId == -1 || mCharacterName == null)
            throw new IllegalStateException("initializePresenter was not well initialised");

        mCharacterSubscription = mGetCharacterInformationUsecase.execute()
            .subscribe(this::onCharacterReceived, this::manageCharacterError);
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
        // Unused
    }

    @Override
    public void attachView(View v) {
        mCharacterDetailView = (CharacterDetailView) v;
    }

    @SuppressWarnings("Convert2MethodRef")
    public void initializePresenter(int characterId, String characterName) {
        mCharacterId = characterId;
        mCharacterName = characterName;
    }

    private void manageCharacterError(Throwable error) {
        // TODO
    }

    private void onCharacterReceived(Character character) {
        mCharacterDetailView.bindCharacter(character);
    }

    public void onComicsIndicatorPressed() {
        CollectionActivity.start(mActivityContext, mCharacterId, CollectionItem.COMIC);
    }

    public void onSeriesIndicatorPressed() {
        CollectionActivity.start(mActivityContext, mCharacterId, CollectionItem.SERIES);
    }

    public void onStoriesIndicatorPressed() {
        CollectionActivity.start(mActivityContext, mCharacterId, CollectionItem.STORY);
    }

    public void onEventIndicatorPressed() {
        CollectionActivity.start(mActivityContext, mCharacterId, CollectionItem.EVENT);
    }

    public void setCharacterId(int characterId) {
        mCharacterId = characterId;
    }

    public void onImageReceived(Bitmap resource) {
        mCharacterDetailView.hideRevealViewByAlpha();
        mCharacterDetailView.initActivityColors(resource);
    }
}