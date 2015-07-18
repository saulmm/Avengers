/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
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
import saulmm.avengers.model.entities.Character;
import saulmm.avengers.views.RecyclerClickListener;

public class AvengersListAdapter extends RecyclerView.Adapter<AvengersListAdapter.AvengerViewHolder> {

    private final RecyclerClickListener recyclerListener;
    private final List<Character> avengers;

    private Context context;

    public AvengersListAdapter(List<Character> avengers, Context context, RecyclerClickListener recyclerClickListener) {

        this.avengers = avengers;
        this.context = context;
        this.recyclerListener = recyclerClickListener;
    }

    @Override
    public AvengerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rootView = LayoutInflater.from(context).inflate(
            R.layout.item_character, parent, false);

        return new AvengerViewHolder(rootView, recyclerListener);
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

        public AvengerViewHolder(View itemView, final RecyclerClickListener recyclerClickListener) {

            super(itemView);
            ButterKnife.inject(this, itemView);
            bindListener(itemView, recyclerClickListener);
        }

        public void bindAvenger(Character character) {

            avengerTitleTextView.setText(character.getName());
            avengerThumbImageView.setImageResource(character.getImageResource());

            Glide.with(context)
                .load(character.getImageUrl())
                .crossFade()
                .into(avengerThumbImageView);
        }

        private void bindListener(View itemView, final RecyclerClickListener recyclerClickListener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    recyclerClickListener.onElementClick(getPosition(),
                        avengerThumbImageView);
                }
            });
        }
    }
}
