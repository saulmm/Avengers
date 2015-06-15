package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import saulmm.avengers.R;
import saulmm.avengers.Utils;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.model.Character;
import saulmm.avengers.model.Comic;
import saulmm.avengers.model.rest.exceptions.NetworkErrorException;
import saulmm.avengers.model.rest.exceptions.NetworkTimeOutException;
import saulmm.avengers.model.rest.exceptions.NetworkUknownHostException;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.AvengersListActivity;

public class AvengerDetailPresenter implements Presenter, AdapterView.OnItemSelectedListener {

    private final Context mActivityContext;
    private AvengersDetailView mAvengersDetailView;

    private int mAvengerCharacterId;
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
    public void attachView(View v) {

        mAvengersDetailView = (AvengersDetailView) v;
    }

    @Override
    public void attachIncomingIntent(Intent intent) {

        mIntent = intent;
    }

    @SuppressWarnings("Convert2MethodRef")
    public void initializePresenter() {

        mAvengerCharacterId = mIntent.getExtras().getInt(
            AvengersListActivity.EXTRA_CHARACTER_ID);

        mAvengersDetailView.startLoading();

        mCharacterSubscription = mGetCharacterInformationUsecase.execute().subscribe(
            character   -> onAvengerReceived(character),
            error       -> manageError(error));

        mComicsSubscription = mGetCharacterComicsUsecase.execute().subscribe(
            comics      -> Observable.from(comics).subscribe(comic -> onComicReceived(comic)),
            throwable   -> manageError(throwable));

        mAvengersDetailView.startLoading();
    }


    private void onComicReceived(Comic comic) {

        mAvengersDetailView.stopLoadingComicsIfNeeded();
        mAvengersDetailView.addComic(comic);
    }

    private void manageError(Throwable error) {

        if (error instanceof NetworkUknownHostException)
            mAvengersDetailView.showError("It has not been possible to resolve marvel api");

        if (error instanceof NetworkTimeOutException)
            mAvengersDetailView.showError ("It has ended the waiting time for connecting to the server marvel");

        if (error instanceof NetworkErrorException)
            mAvengersDetailView.showError ("There was a problem with the network");
    }


    private void onAvengerReceived(Character character) {

        mAvengersDetailView.stopLoadingAvengersInformation();
        mAvengersDetailView.showAvengerName(character.getName());
        mAvengersDetailView.showAvengerBio(
            (character.getDescription().equals(""))
                ? "No biography available"
                : character.getDescription());

        if (character.getImageUrl() != null)
            mAvengersDetailView.showAvengerImage(character.getImageUrl());
    }

    public void onDialogButton(int which) {

        if (which == Utils.DIALOG_ACCEPT)
            System.out.println("[DEBUG]" + " AvengerDetailPresenter onDialogButton - " +
                "Accepted");

        if (which == Utils.DIALOG_CANCEL)
            System.out.println("[DEBUG]" + " AvengerDetailPresenter onDialogButton - " +
                "Cancelled");

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {

        if (position != 0) {

            mAvengersDetailView.clearComicsView();
            String[] yearArray = mActivityContext.getResources().getStringArray(R.array.years);
            String selectedYear = yearArray[position];

            mGetCharacterComicsUsecase.filterByYear(selectedYear)
                .subscribe(
                    comic -> onComicReceived(comic));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}
