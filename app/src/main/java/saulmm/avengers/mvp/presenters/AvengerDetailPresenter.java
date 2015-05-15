/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import android.content.Intent;


import java.util.ArrayList;

import javax.inject.Inject;

import rx.Subscriber;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.model.Character;
import saulmm.avengers.model.Comic;
import saulmm.avengers.model.rest.ComicsWrapper;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.AvengersListActivity;

public class AvengerDetailPresenter extends Subscriber<Character>  implements Presenter {

    private AvengersDetailView mAvengersDetailView;

    private int mAvengerCharacterId;
    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;
    private final GetCharacterComicsUsecase mGetCharacterComicsUsecase;
    private Intent mIntent;

    @Inject
    public AvengerDetailPresenter(GetCharacterInformationUsecase getCharacterInformationUsecase,
                                  GetCharacterComicsUsecase getCharacterComicsUsecase) {

        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
        mGetCharacterComicsUsecase = getCharacterComicsUsecase;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void attachView(View v) {

        mAvengersDetailView = (AvengersDetailView) v;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

        mIntent = intent;
    }

    public void initializePresenter() {

        mAvengerCharacterId = mIntent.getExtras().getInt(
            AvengersListActivity.EXTRA_CHARACTER_ID);

        mAvengersDetailView.startLoading();

        mGetCharacterInformationUsecase.execute(this);
//        mGetCharacterComicsUsecase.execute(this);
    }

    public void onAvengerComicsReceived (ComicsWrapper comicsWrapper) {

        ArrayList<Comic> comics = comicsWrapper.getmComics();

        for (Comic comic : comics) {
            mAvengersDetailView.addComic(comic);
        }
    }

    @Override
    public void onStop() {

    }

    @Override
    public void onCompleted() {
        
    }

    @Override
    public void onError(Throwable e) {

        System.out.println("[DEBUG]" + " AvengerDetailPresenter onError - " + e.getMessage());

    }

    @Override
    public void onNext(Character character) {

        mAvengersDetailView.stopLoading();
        mAvengersDetailView.showAvengerName(character.getName());
        mAvengersDetailView.showAvengerBio(character.getDescription());

        if (character.getImageUrl() != null)
            mAvengersDetailView.showAvengerImage(character.getImageUrl());
    }
}
