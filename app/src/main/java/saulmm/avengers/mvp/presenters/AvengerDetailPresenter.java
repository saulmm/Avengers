/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.AdapterView;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscription;
import saulmm.avengers.R;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.model.entities.Comic;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.AvengersListActivity;

public class AvengerDetailPresenter implements Presenter, AdapterView.OnItemSelectedListener {

    private final Context mActivityContext;
    private AvengersDetailView mAvengersDetailView;

    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;
    private final GetCharacterComicsUsecase mGetCharacterComicsUsecase;
    private Intent mIntent;

    private Subscription mComicsSubscription;
    private Subscription mCharacterSubscription;

    @Inject
    public AvengerDetailPresenter(GetCharacterInformationUsecase getCharacterInformationUsecase,
                                  GetCharacterComicsUsecase getCharacterComicsUsecase,
                                  Context activityContext) {

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

        if (!mComicsSubscription.isUnsubscribed())
            mComicsSubscription.unsubscribe();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void attachView(View v) {

        mAvengersDetailView = (AvengersDetailView) v;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

        mIntent = intent;
    }

    @SuppressWarnings("Convert2MethodRef")
    public void initializePresenter() {
        String characterName = mIntent.getExtras().getString(
            AvengersListActivity.EXTRA_CHARACTER_NAME);

        mAvengersDetailView.startLoading();

        mCharacterSubscription = mGetCharacterInformationUsecase.execute().subscribe(
            this::onCharacterReceived, this::manageCharacterError);

        mComicsSubscription = mGetCharacterComicsUsecase.execute()
            .subscribe(comics-> {
                if (!comics.isEmpty())
                    Observable.from(comics).subscribe(comic -> onComicReceived(comic));

                else {
                    mAvengersDetailView.stopLoadingComicsIfNeeded();
                    mAvengersDetailView.hideComics();

                }
            }, throwable -> manageComicsError(throwable));

        mAvengersDetailView.startLoading();
        mAvengersDetailView.showAvengerName(characterName);
    }

    private void manageComicsError(Throwable throwable) {
        mAvengersDetailView.stopLoadingComicsIfNeeded();
        mAvengersDetailView.hideComics();
    }

    private void manageCharacterError(Throwable error) {
        // TODO
    }

    private void onComicReceived(Comic comic) {
        mAvengersDetailView.stopLoadingComicsIfNeeded();
        mAvengersDetailView.addComic(comic);
    }

    private void onCharacterReceived(Character character) {
        mAvengersDetailView.stopLoadingAvengersInformation();
        mAvengersDetailView.showAvengerBio(
            (character.getDescription().equals("")) ? "No biography available"
                : character.getDescription());


        if (character.getImageUrl() != null)
            mAvengersDetailView.showAvengerImage(character.getImageUrl());
    }

    public void onDialogButton(int which) {
        //if (which == Utils.DIALOG_ACCEPT)
        //    // TODO
        //if (which == Utils.DIALOG_CANCEL)
        //    // TODO
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
        if (position != 0) {
            mAvengersDetailView.clearComicsView();
            String[] yearArray = mActivityContext.getResources().getStringArray(R.array.years);
            String selectedYear = yearArray[position];

            mGetCharacterComicsUsecase.filterByYear(selectedYear)
                .subscribe(this::onComicReceived);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    public void onCharacterBitmapReceived(Bitmap resource) {
        mAvengersDetailView.hideRevealViewByAlpha();
        mAvengersDetailView.initActivityColors(resource);
    }
}
