/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import javax.inject.Inject;
import rx.Subscription;
import saulmm.avengers.CharacterDetailsUsecase;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.mvp.views.CharacterDetailView;
import saulmm.avengers.mvp.views.View;

public class CharacterDetailPresenter implements Presenter {
    private CharacterDetailView mCharacterDetailView;

    private final CharacterDetailsUsecase mGetCharacterInformationUsecase;

    private Subscription mCharacterSubscription;
    private int mCharacterId;
    private String mCharacterName;

    @Inject
    public CharacterDetailPresenter(CharacterDetailsUsecase getCharacterInformationUsecase) {
        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
    }

    @Override
    public void onCreate() {
        if (mCharacterId == -1 || mCharacterName == null)
            throw new IllegalStateException();

        mCharacterDetailView.disableScroll();
        askForCharacterDetails();
    }

    public void askForCharacterDetails() {
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

    public void initializePresenter(int characterId, String characterName) {
        mCharacterId = characterId;
        mCharacterName = characterName;
    }

    private void manageCharacterError(Throwable error) {
    }

    private void onCharacterReceived(MarvelCharacter character) {
        mCharacterDetailView.bindCharacter(character);

        if (character.getDescription() != null && !character.getDescription().equals(""))
            mCharacterDetailView.enableScroll();
    }

    public void onComicsIndicatorPressed() {
        mCharacterDetailView.goToCharacterComicsView(mCharacterId);
    }

    public void onSeriesIndicatorPressed() {
        mCharacterDetailView.goToCharacterSeriesView(mCharacterId);
    }

    public void onStoriesIndicatorPressed() {
        mCharacterDetailView.goToCharacterStoriesView(mCharacterId);
    }

    public void onEventIndicatorPressed() {
        mCharacterDetailView.goToCharacterEventsView(mCharacterId);
    }

    public void setCharacterId(int characterId) {
        mCharacterId = characterId;
    }

    public void onImageReceived() {
        mCharacterDetailView.hideRevealViewByAlpha();
    }
}