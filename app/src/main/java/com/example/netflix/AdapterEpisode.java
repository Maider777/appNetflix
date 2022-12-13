package com.example.netflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterEpisode extends RecyclerView.Adapter<AdapterEpisode.ExampleViewHolder> {

    private Context mContextEpisode;
    private ArrayList<ItemEpisode> mListEpisode;

    public AdapterEpisode(Context context, ArrayList<ItemEpisode> listEpisode) {
        mContextEpisode = context;
        mListEpisode = listEpisode;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContextEpisode).inflate(R.layout.item_episode,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ItemEpisode currentItem=mListEpisode.get(position);
        String season=currentItem.getsEpisode();
        String imageUrl=currentItem.getsImageEpisode();
        String summary=currentItem.getsSummary();
        holder.mTextViewSummary.setText(summary);
        holder.mTextViewEpisode.setText(season);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageViewEpisode);
    }

    @Override
    public int getItemCount() {
        return mListEpisode.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageViewEpisode;
        public TextView mTextViewEpisode;
        public TextView mTextViewUrlEpisode;
        public TextView mTextViewSummary;

        public ExampleViewHolder(View itemView){
            super(itemView);
            mImageViewEpisode=itemView.findViewById(R.id.image_view_episode);
            mTextViewEpisode=itemView.findViewById(R.id.text_view_episode);
            mTextViewSummary=itemView.findViewById(R.id.text_view_summary);
        }
    }
}
