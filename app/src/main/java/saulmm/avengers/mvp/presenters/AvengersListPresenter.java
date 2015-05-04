package saulmm.avengers.mvp.presenters;

import android.content.Context;
import android.content.Intent;

import java.util.List;

import javax.inject.Inject;

import saulmm.avengers.model.Character;
import saulmm.avengers.mvp.views.AvengersView;
import saulmm.avengers.mvp.views.View;
import saulmm.avengers.views.RecyclerClickListener;
import saulmm.avengers.views.activities.AvengerDetailActivity;

public class AvengersListPresenter implements Presenter, RecyclerClickListener {

    private final List<Character> mAvengersList;
    private final Context mContext;
    private AvengersView mAvengersView;

    @Inject public AvengersListPresenter (List<Character> avengers, Context context) {

        mAvengersList = avengers;
        mContext = context;
    }

    @Override
    public void attachView(View v) {

        mAvengersView = (AvengersView) v;
        mAvengersView.showAvengersList(mAvengersList);
    }

    @Override
    public void onElementClick(int position) {

        Intent i = new Intent (mContext, AvengerDetailActivity.class);
        mContext.startActivity(i);
    }
}
