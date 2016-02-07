/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;
import saulmm.avengers.GetCharactersUsecase;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.mvp.views.CharacterListView;
import saulmm.avengers.mvp.views.View;

 public class CharacterListPresenter implements Presenter {
    private final GetCharactersUsecase mCharactersUsecase;
    private boolean mIsTheCharacterRequestRunning;
    private Subscription mCharactersSubscription;

    private List<MarvelCharacter> mCharacters = new ArrayList<>();
    private CharacterListView mAvengersView;

    @Inject
    public CharacterListPresenter(GetCharactersUsecase charactersUsecase) {
        mCharactersUsecase = charactersUsecase;
    }

    @Override
    public void onCreate() {
        askForCharacters();
    }


    @Override
    public void onStart() {
        // Unused
    }

    @Override
    public void onStop() {
        // Unused
    }

    @Override
    public void onPause() {
        mAvengersView.hideLoadingMoreCharactersIndicator();
        mCharactersSubscription.unsubscribe();
        mIsTheCharacterRequestRunning = false;
    }

    @Override
    public void attachView(View v) {
        mAvengersView = (CharacterListView) v;
    }

    public void onListEndReached() {
        if (!mIsTheCharacterRequestRunning) askForNewCharacters();
    }

     public void askForCharacters() {
      mIsTheCharacterRequestRunning = true;
        mAvengersView.hideErrorView();
        mAvengersView.showLoadingView();

        mCharactersSubscription = mCharactersUsecase.execute()
            .subscribe(this::onCharactersReceived, this::showErrorView);
    }

     public void onCharactersReceived(List<MarvelCharacter> characters) {
         mCharacters.addAll(characters);
         mAvengersView.bindCharacterList(mCharacters);
         mAvengersView.showCharacterList();
         mAvengersView.hideEmptyIndicator();
         mIsTheCharacterRequestRunning = false;
     }

     public void askForNewCharacters() {
        mAvengersView.showLoadingMoreCharactersIndicator();
        mIsTheCharacterRequestRunning = true;

        mCharactersSubscription = mCharactersUsecase.execute()
            .subscribe(this::onNewCharactersReceived, this::onNewCharactersError);
    }

     private void onNewCharactersError(Throwable error) {
         showGenericError();
         mIsTheCharacterRequestRunning = false;
     }

     private void onNewCharactersReceived(List<MarvelCharacter> newCharacters) {
         mCharacters.addAll(newCharacters);
         mAvengersView.updateCharacterList(GetCharactersUsecase.DEFAULT_CHARACTERS_LIMIT);

         mAvengersView.hideLoadingIndicator();
         mIsTheCharacterRequestRunning = false;
     }

     public void showErrorView(Throwable error) {
        mAvengersView.showUknownErrorMessage();
        mAvengersView.hideLoadingMoreCharactersIndicator();
        mAvengersView.hideEmptyIndicator();
        mAvengersView.hideCharactersList();
    }

    public void showGenericError() {
        mAvengersView.hideLoadingIndicator();
        mAvengersView.showLightError();
    }

    public void onErrorRetryRequest() {
        if (mCharacters.isEmpty())
            askForCharacters();
        else
            askForNewCharacters();
    }

    public void onElementClick(int position) {
        int characterId = mCharacters.get(position).getId();
        String characterName = mCharacters.get(position).getName();
        mAvengersView.showDetailScreen(characterName, characterId);
    }
}
