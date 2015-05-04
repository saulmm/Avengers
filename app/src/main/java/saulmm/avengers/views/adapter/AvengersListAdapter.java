package saulmm.avengers.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import saulmm.avengers.R;
import saulmm.avengers.model.Character;

public class AvengersListAdapter extends RecyclerView.Adapter<AvengersListAdapter.AvengerViewHolder> {

    private List<Character> avengers;
    private Context context;

    public AvengersListAdapter(List<Character> avengers, Context context) {

        this.avengers = avengers;
        this.context = context;
    }

    @Override
    public AvengerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(
            R.layout.item_avenger, parent, false);

        return new AvengerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(AvengerViewHolder holder, int position) {

        holder.bindAvenger(avengers.get(position));
    }

    @Override
    public int getItemCount() {

        return avengers.size();
    }

    public class AvengerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.item_avenger_title) TextView avengerTitleTextView;
        @InjectView(R.id.item_avenger_thumb) ImageView avengerThumbImageView;

        public AvengerViewHolder(View itemView) {

            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void bindAvenger(Character character) {

            avengerTitleTextView.setText(character.getTitle());

            Glide.with(context)
                .load(character.getThumbnail())
                .into(avengerThumbImageView);
        }
    }
}
