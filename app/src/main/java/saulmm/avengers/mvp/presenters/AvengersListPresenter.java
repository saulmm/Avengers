package saulmm.avengers.mvp.presenters;

import java.util.List;

import javax.inject.Inject;

import saulmm.avengers.model.Character;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.mvp.views.View;

public class AvengersListPresenter implements Presenter {

    private final List<Character> mAvengersList;
    private AvengersView mAvengersView;

    @Inject public AvengersListPresenter (List<Character> avengers) {

        mAvengersList = avengers;
    }

    @Override
    public void attachView(View v) {

        mAvengersView = (AvengersView) v;
        mAvengersView.showAvengersList(mAvengersList);
    }
}
