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
import saulmm.avengers.views.activities.AvengersListActivity;

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
        String characterId = mAvengersList.get(position).getId();

        Intent i = new Intent (mContext, AvengerDetailActivity.class);
        i.putExtra(AvengersListActivity.EXTRA_CHARACTER_ID, characterId);
        mContext.startActivity(i);
    }
}
