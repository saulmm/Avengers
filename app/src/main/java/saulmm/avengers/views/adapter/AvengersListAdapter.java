/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package saulmm.avengers.views.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import java.util.List;
import saulmm.avengers.R;
import saulmm.avengers.entities.MarvelCharacter;
import saulmm.avengers.utils.Utils;
import saulmm.avengers.views.RecyclerClickListener;

public class AvengersListAdapter extends RecyclerView.Adapter<AvengersListAdapter.CharacterViewHolder> {
    private final String NOT_AVAILABLE_URL = "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available.jpg";
    private final RecyclerClickListener mRecyclerListener;
    private final List<MarvelCharacter> mCharacters;

    private Context mContext;

    public AvengersListAdapter(List<MarvelCharacter> avengers, Context context,
        RecyclerClickListener recyclerClickListener) {

        mCharacters = avengers;
        mContext = context;
        mRecyclerListener = recyclerClickListener;
    }

    @Override
    public CharacterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(
            R.layout.item_character, parent, false);

        return new CharacterViewHolder(rootView, mRecyclerListener);
    }

    @Override
    public void onBindViewHolder(CharacterViewHolder holder, int position) {
        holder.bindAvenger(mCharacters.get(position));
    }

    @Override
    public int getItemCount() {
        return mCharacters.size();
    }

    public class CharacterViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.item_avenger_title)              TextView avengerTitleTextView;
        @Bind(R.id.item_avenger_thumb)              ImageView avengerThumbImageView;
        @Bind(R.id.item_avenger_placeholder_name)   TextView avengerPlaceholderTitleTextView;
        @BindColor(R.color.brand_primary)           int mColorPrimary;

        public CharacterViewHolder(View itemView, final RecyclerClickListener recyclerClickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindAvenger(MarvelCharacter character) {
            avengerTitleTextView.setText(character.getName());
            avengerTitleTextView.setTransitionName(Utils.getListTransitionName(getPosition()));

            if (character.getImageUrl().equals(NOT_AVAILABLE_URL)) {
                ColorDrawable colorDrawable = new ColorDrawable(mColorPrimary);
                avengerThumbImageView.setDrawingCacheEnabled(true);
                avengerThumbImageView.setImageDrawable(colorDrawable);

            } else {
                Glide.with(mContext)
                    .load(character.getImageUrl())
                    .crossFade()
                    .into(avengerThumbImageView);
            }
        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {
            itemView.setOnClickListener(v ->
                recyclerClickListener.onElementClick(getPosition(), avengerTitleTextView, avengerThumbImageView));
        }
    }
}
