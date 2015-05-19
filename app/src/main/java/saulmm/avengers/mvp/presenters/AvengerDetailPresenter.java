package saulmm.avengers.mvp.presenters;

import android.content.Intent;

import java.util.List;

import javax.inject.Inject;

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

        // Unused
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

        mGetCharacterInformationUsecase.execute().subscribe(
            character   -> {onAvengerReceived(character);},
            trowable    -> {manageError (trowable);}
        );

        mGetCharacterComicsUsecase.execute().subscribe(
            comics      -> { onAvengerComicsReceived(comics); },
            trowable    -> { onAvengerComicError(trowable); }
        );
    }

    private void onAvengerComicError(Throwable trowable) {

        System.out.println("[ERROR]" + " AvengerDetailPresenter, onAvengerComicError (79)- " +
            ""+trowable.getMessage());

    }

    private void manageError(Throwable trowable) {

        System.out.println("[ERROR]" + " AvengerDetailPresenter, manageError (86)- " +
            ""+trowable.getMessage());
    }

    public void onAvengerComicsReceived (List<Comic> comics) {

        for (Comic comic : comics) {
            mAvengersDetailView.addComic(comic);
        }
    }

    private void onAvengerReceived(Character character) {

        mAvengersDetailView.stopLoading();
        mAvengersDetailView.showAvengerName(character.getName());
        mAvengersDetailView.showAvengerBio(character.getDescription());

        if (character.getImageUrl() != null)
            mAvengersDetailView.showAvengerImage(character.getImageUrl());
    }
}
