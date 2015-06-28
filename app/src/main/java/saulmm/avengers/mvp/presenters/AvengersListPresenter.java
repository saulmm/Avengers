/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import saulmm.avengers.R;
import saulmm.avengers.domain.GetCharactersUsecase;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.model.rest.exceptions.NetworkUknownHostException;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.RecyclerClickListener;
import saulmm.avengers.views.activities.AvengerDetailActivity;
import saulmm.avengers.views.activities.AvengersListActivity;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class AvengersListPresenter implements Presenter, RecyclerClickListener {

    private final GetCharactersUsecase mCharactersUsecase;
    private final Context mContext;

    private boolean mIsTheCharacterRequestRunning;

    private List<Character> mCharacters;
    private AvengersView mAvengersView;
    private Intent mIntent;


    @Inject
    public AvengersListPresenter (Context context, GetCharactersUsecase charactersUsecase) {

        mContext = context;
        mCharactersUsecase = charactersUsecase;
        mCharacters = new ArrayList<>();
    }

    @Override @SuppressWarnings("Convert2MethodRef")
    public void onStart() {

        askForCharacters();
    }

    @Override
    public void attachView(View v) {

        mAvengersView = (AvengersView) v;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

        mIntent = intent;
    }

    @Override
    public void onElementClick(int position) {

        int characterId = mCharacters.get(position).getId();
        Intent i = new Intent (mContext, AvengerDetailActivity.class);
        i.putExtra(AvengersListActivity.EXTRA_CHARACTER_ID, characterId);
        mContext.startActivity(i);
    }

    @Override
    public void onStop() {

        // Unused
    }

    public void onListEndReached() {
        
        if (!mIsTheCharacterRequestRunning) {

            mAvengersView.showLoadingIndicator();
            mIsTheCharacterRequestRunning = true;

            askForNewCharacters();
        }
    }

    @SuppressWarnings("Convert2MethodRef")
    private void askForCharacters() {

        showLoadingUI();

        mCharactersUsecase.execute().subscribe(
            characters -> {

                mCharacters.addAll(characters);
                mAvengersView.showAvengersList(mCharacters);
                mAvengersView.hideEmptyIndicator();
            },

            error -> showErrorView(error)
        );
    }

    private void askForNewCharacters() {

        mAvengersView.showLoadingIndicator();

        mCharactersUsecase.executeIncreasingOffset().subscribe(

            newCharacters -> {

                mCharacters.addAll(newCharacters);
                mAvengersView.showAvengersList();;
                mAvengersView.hideLoadingIndicator();
                mIsTheCharacterRequestRunning = false;
            },

            error -> {
                showGenericError();
                mIsTheCharacterRequestRunning = false;
            }
        );
    }

    private void showLoadingUI() {

        mAvengersView.hideErrorView();
        mAvengersView.showLoadingView();
    }

    private void showErrorView(Throwable error) {

        if (error instanceof NetworkUknownHostException) {

            String errorMessage = mContext.getString(R.string.error_network_uknownhost);
            mAvengersView.showErrorView(errorMessage);
        }

        mAvengersView.hideEmptyIndicator();
        mAvengersView.hideAvengersList();
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
}
