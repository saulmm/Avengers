package saulmm.avengers.views.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import saulmm.avengers.AvengersApplication;
import saulmm.avengers.R;
import saulmm.avengers.injector.components.DaggerAvengerInformationComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.injector.modules.AvengerInformationModule;
import saulmm.avengers.model.Comic;
import saulmm.avengers.mvp.presenters.AvengerDetailPresenter;
import saulmm.avengers.mvp.views.AvengersDetailView;

public class AvengerDetailActivity extends Activity implements AvengersDetailView {

    @InjectView(R.id.activity_avenger_detail_progress)  ProgressBar mProgress;
    @InjectView(R.id.activity_avenger_detail_container) LinearLayout mDetailContainer;
    @InjectView(R.id.activity_avenger_detail_biography) TextView mBiographyTextView;
    @InjectView(R.id.activity_avenger_detail_name)      TextView mAvengerName;
    @InjectView(R.id.activity_avenger_image)            ImageView mAvengerImageView;

    @Inject AvengerDetailPresenter avengerDetailPresenter;

    private View comicView;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avenger_detail);
        ButterKnife.inject(this);

        initializeDependencyInjector();
        initializePresenter();
    }

    @Override
    protected void onStart() {

        super.onStart();
        avengerDetailPresenter.onStart();
    }

    private void initializePresenter() {

        avengerDetailPresenter.attachView(this);
        avengerDetailPresenter.attachIncomingIntent(getIntent());
        avengerDetailPresenter.initializePresenter();
    }

    private void initializeDependencyInjector() {

        AvengersApplication avengersApplication = (AvengersApplication) getApplication();

        int avengerId = getIntent().getIntExtra(AvengersListActivity.EXTRA_CHARACTER_ID, -1);

        DaggerAvengerInformationComponent.builder()
            .activityModule(new ActivityModule(this))
            .appComponent(avengersApplication.getAppComponent())
            .avengerInformationModule(new AvengerInformationModule(avengerId))
            .build().inject(this);
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
        mBiographyTextView.setText(text);
    }

    @Override
    public void showAvengerImage(String url) {

        Glide.with(this).load(url).into(mAvengerImageView);
    }

    @Override
    public void showAvengerName(String name) {

        mAvengerName.setText(name);
    }

    @Override
    public void addComic(Comic comic) {

        View comicView = LayoutInflater.from(this).inflate(
                R.layout.item_comic, null, true);

        TextView comicTitleTextView = ButterKnife.findById(comicView, R.id.item_comic_title);
        TextView comicDescTextView = ButterKnife.findById(comicView, R.id.item_comic_description);
        ImageView comicCoverImageView = ButterKnife.findById(comicView, R.id.item_comic_cover);

        comicTitleTextView.setText(comic.getTitle());

        if (comic.getFirstImageUrl() != null)
            Glide.with(this).load(comic.getFirstImageUrl()).into(comicCoverImageView);

        if (comic.getFirstTextObject() != null)
            comicDescTextView.setText(Html.fromHtml(comic.getFirstTextObject()));

        mDetailContainer.addView(comicView);
    }

    @Override
    protected void onStop() {

        super.onStop();
        avengerDetailPresenter.onStop();
    }
}
