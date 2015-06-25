/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import javax.inject.Inject;

import saulmm.avengers.domain.GetCharactersUsecase;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.RecyclerClickListener;
import saulmm.avengers.views.activities.AvengerDetailActivity;
import saulmm.avengers.views.activities.AvengersListActivity;

public class AvengersListPresenter implements Presenter, RecyclerClickListener {

    private final GetCharactersUsecase mCharactersUsecase;
    private AvengersView mAvengersView;
    private final Context mContext;
    private Intent mIntent;
    private List<Character> mCharacters;

    @Inject public AvengersListPresenter (Context context, GetCharactersUsecase charactersUsecase) {

        mContext = context;
        mCharactersUsecase = charactersUsecase;
    }


    @Override @SuppressWarnings("Convert2MethodRef")
    public void onStart() {

        mCharactersUsecase.execute().subscribe(
            characters1 -> {

                mCharacters = characters1;
                mAvengersView.showAvengersList(mCharacters);
            }
        );
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
        
        System.out.println("[DEBUG]" + " AvengersListPresenter onListEndReached - " +
            "Reached");

    }
}
