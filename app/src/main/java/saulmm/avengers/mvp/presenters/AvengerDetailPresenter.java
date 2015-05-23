package saulmm.avengers.mvp.presenters;

import android.content.Intent;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import saulmm.avengers.Utils;
import saulmm.avengers.domain.GetCharacterComicsUsecase;
import saulmm.avengers.domain.GetCharacterInformationUsecase;
import saulmm.avengers.model.Character;
import saulmm.avengers.model.Comic;
import saulmm.avengers.mvp.views.AvengersDetailView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.activities.AvengersListActivity;

public class AvengerDetailPresenter implements Presenter {

    private AvengersDetailView mAvengersDetailView;

    private int mAvengerCharacterId;
    private final GetCharacterInformationUsecase mGetCharacterInformationUsecase;
    private final GetCharacterComicsUsecase mGetCharacterComicsUsecase;
    private Intent mIntent;

    private Subscription mComicsSubscription;
    private Subscription mCharacterSubscription;

    @Inject
    public AvengerDetailPresenter(GetCharacterInformationUsecase getCharacterInformationUsecase,
                                  GetCharacterComicsUsecase getCharacterComicsUsecase) {

        mGetCharacterInformationUsecase = getCharacterInformationUsecase;
        mGetCharacterComicsUsecase = getCharacterComicsUsecase;
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
            error       -> manageError(error)
        );

        mComicsSubscription = mGetCharacterComicsUsecase.execute().subscribe(
            comics      -> Observable.from(comics).subscribe(comic -> onComicReceived(comic),
            error       -> onAvengerComicError(error)));

        mAvengersDetailView.startLoading();
    }


    private void onComicReceived(Comic comic) {

        mAvengersDetailView.hideComicProgressIfNeeded();
        mAvengersDetailView.addComic(comic);
    }

    private void onAvengerComicError(Throwable trowable) {

        System.out.println("[ERROR]" + " AvengerDetailPresenter, onAvengerComicError (79)- " +
            "" + trowable.getMessage());
    }

    private void manageError(Throwable trowable) {

        System.out.println("[ERROR]" + " AvengerDetailPresenter, manageError (86)- " +
            ""+trowable.getMessage());
    }


    private void onAvengerReceived(Character character) {

        mAvengersDetailView.stopLoading();
        mAvengersDetailView.showAvengerName(character.getName());
        mAvengersDetailView.showAvengerBio(character.getDescription());

        if (character.getImageUrl() != null)
            mAvengersDetailView.showAvengerImage(character.getImageUrl());
    }

    public void onDialogButton(int which) {

        if (which == Utils.DIALOG_ACCEPT)
            System.out.println("[DEBUG]" + " AvengerDetailPresenter onDialogButton - " +
                "Accepted");

        else if (which == Utils.DIALOG_CANCEL)
            System.out.println("[DEBUG]" + " AvengerDetailPresenter onDialogButton - " +
                "Cancelled");

    }
}
