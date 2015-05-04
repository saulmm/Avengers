package saulmm.avengers.mvp.presenters;

import javax.inject.Inject;

import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.mvp.views.View;

public class AvengersListPresenter implements Presenter {

    private AvengersView mAvengersView;

    @Inject public AvengersListPresenter () {}

    @Override
    public void attachView(View v) {

        mAvengersView = (AvengersView) v;
    }
}
