package saulmm.avengers.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import saulmm.avengers.R;
import saulmm.avengers.model.Character;
import saulmm.avengers.views.adapter.AvengersListAdapter;
import saulmm.avengers.views.mvp.AvengersView;


public class AvengersListActivity extends Activity implements AvengersView {

    @InjectView(R.id.activity_avengers_recycler) RecyclerView mAvengersRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avengers_list);
        ButterKnife.inject(this);

        initializeRecyclerView();
        showAvengersList(createFakeCharacters());

    }

    private void initializeRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAvengersRecycler.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void showAvengersList(List<Character> avengers) {

        AvengersListAdapter avengersListAdapter = new AvengersListAdapter(avengers, this);
        mAvengersRecycler.setAdapter(avengersListAdapter);
    }

    // Temp
    public ArrayList<Character> createFakeCharacters () {

        ArrayList<Character> characters = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            characters.add(new Character());
        }

        return characters;
    }
}
