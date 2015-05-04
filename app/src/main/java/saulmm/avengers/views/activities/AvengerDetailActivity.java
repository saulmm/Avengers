package saulmm.avengers.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import saulmm.avengers.R;
import saulmm.avengers.mvp.views.AvengersDetailView;

public class AvengerDetailActivity extends Activity implements AvengersDetailView {

    @InjectView(R.id.activity_avenger_detail_progress)  ProgressBar mProgress;
    @InjectView(R.id.activity_avenger_detail_biography) TextView mBiographyTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avenger_detail);
        ButterKnife.inject(this);
    }

    @Override
    public void startLoading() {

        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoading() {

        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showAvengerBio(String text) {

        mBiographyTextView.setVisibility(View.VISIBLE);
    }
}
