/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import saulmm.avengers.domain.GetCharactersUsecase;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.RecyclerClickListener;

public class AvengersListPresenter implements Presenter, RecyclerClickListener {

    private final GetCharactersUsecase mCharactersUsecase;
    private AvengersView mAvengersView;
    private final Context mContext;
    private Intent mIntent;

    @Inject public AvengersListPresenter (Context context, GetCharactersUsecase charactersUsecase) {

        mContext = context;
        mCharactersUsecase = charactersUsecase;
    }


    @Override @SuppressWarnings("Convert2MethodRef")
    public void onStart() {

        mCharactersUsecase.execute().subscribe(
            characters -> mAvengersView.showAvengersList(characters),
            error      ->  {

                System.out.println("[DEBUG]" + " AvengersListPresenter onStart - " +
                    "Manage error");
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

//        int characterId = mAvengersList.get(position).getId();
//        Intent i = new Intent (mContext, AvengerDetailActivity.class);
//        i.putExtra(AvengersListActivity.EXTRA_CHARACTER_ID, characterId);
//        mContext.startActivity(i);
    }

    @Override
    public void onStop() {

        // Unused
    }
}
