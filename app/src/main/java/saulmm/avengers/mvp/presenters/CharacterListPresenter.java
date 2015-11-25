/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.widget.ImageView;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Subscription;
import saulmm.avengers.domain.GetCharactersUsecase;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.model.rest.exceptions.NetworkUknownHostException;
import saulmm.avengers.model.rest.exceptions.ServerErrorException;
import saulmm.avengers.mvp.views.CharacterListView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.utils.Utils;
import saulmm.avengers.views.RecyclerClickListener;
import saulmm.avengers.views.activities.CharacterDetailActivity;

 public class CharacterListPresenter implements Presenter, RecyclerClickListener {
    private final GetCharactersUsecase mCharactersUsecase;
    private final Context mContext;

    private boolean mIsTheCharacterRequestRunning;
    private Subscription mCharactersSubscription;

    private List<Character> mCharacters;
    private CharacterListView mAvengersView;

    @Inject
    public CharacterListPresenter(Context context, GetCharactersUsecase charactersUsecase) {
        mContext = context;
        mCharactersUsecase = charactersUsecase;
        mCharacters = new ArrayList<>();
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

    @SuppressWarnings("Convert2MethodRef")
    private void askForCharacters() {
      mIsTheCharacterRequestRunning = true;
        mAvengersView.hideErrorView();
        mAvengersView.showLoadingView();

        mCharactersSubscription = mCharactersUsecase.execute()
            .subscribe(characters -> {
                mCharacters.addAll(characters);
                mAvengersView.bindCharacterList(mCharacters);
                mAvengersView.showCharacterList();
                mAvengersView.hideEmptyIndicator();
                mIsTheCharacterRequestRunning = false;

            }, error -> {
                showErrorView(error);
            });

    }

    private void askForNewCharacters() {
        mAvengersView.showLoadingMoreCharactersIndicator();
        mIsTheCharacterRequestRunning = true;

        mCharactersSubscription = mCharactersUsecase.executeIncreasingOffset()
            .subscribe(

            newCharacters -> {
                mCharacters.addAll(newCharacters);
                mAvengersView.updateCharacterList (
                    GetCharactersUsecase.CHARACTERS_LIMIT);

                mAvengersView.hideLoadingIndicator();
                mIsTheCharacterRequestRunning = false;
            },

            error -> {
                showGenericError();
                mIsTheCharacterRequestRunning = false;
            }
        );
    }

    private void showErrorView(Throwable error) {
        if (error instanceof NetworkUknownHostException) {
            mAvengersView.showConnectionErrorMessage();

        } else if (error instanceof ServerErrorException) {
            mAvengersView.showServerErrorMessage();

        } else {
            mAvengersView.showUknownErrorMessage();
        }

        mAvengersView.hideLoadingMoreCharactersIndicator();
        mAvengersView.hideEmptyIndicator();
        mAvengersView.hideCharactersList();
    }

    private void showGenericError() {
        mAvengersView.hideLoadingIndicator();
        mAvengersView.showLightError();
    }

    public void onErrorRetryRequest() {
        if (mCharacters.isEmpty())
            askForCharacters();
        else
            askForNewCharacters();
    }

    @Override
    public void onElementClick(int position, android.view.View clickedView,
        ImageView avengerThumbImageView) {
        int characterId = mCharacters.get(position).getId();
        String characterName = mCharacters.get(position).getName();
        String sharedElementName = Utils.getListTransitionName(position);
        CharacterDetailActivity.start(mContext, characterName, characterId);
    }
}
