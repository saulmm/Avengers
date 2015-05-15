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

import saulmm.avengers.model.Character;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.RecyclerClickListener;

public class AvengersListPresenter implements Presenter, RecyclerClickListener {

    private final List<Character> mAvengersList;
    private final Context mContext;
    private AvengersView mAvengersView;
    private Intent mIntent;

    @Inject public AvengersListPresenter (List<Character> avengers, Context context) {

        mAvengersList = avengers;
        mContext = context;
    }

    @Override
    public void onStart() {

        // Unused
    }

    @Override
    public void attachView(View v) {

        mAvengersView = (AvengersView) v;
        mAvengersView.showAvengersList(mAvengersList);
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

        mIntent = intent;
    }

    @Override
    public void onElementClick(int position) {

        // Todo
    }

    @Override
    public void onStop() {

        // Unused
    }
}
