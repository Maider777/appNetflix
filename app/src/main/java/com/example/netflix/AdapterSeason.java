package com.example.netflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSeason extends RecyclerView.Adapter<AdapterSeason.ExampleViewHolder> {

    private Context mContextSeason;
    private ArrayList<ItemSeason> mListSeason;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }

    public AdapterSeason(Context context, ArrayList<ItemSeason> list) {
        mContextSeason = context;
        mListSeason = list;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContextSeason).inflate(R.layout.item_season,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ItemSeason currentItem=mListSeason.get(position);
        String imageUrl=currentItem.getsImageUrl();
        String season=currentItem.getsSeason();
        holder.mButtonView.setText("Episodes T"+season);
        holder.mTextViewName.setText(season);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mListSeason.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextViewName;
        public Button mButtonView;

        public ExampleViewHolder(View itemView){
            super(itemView);
            mTextViewName=itemView.findViewById(R.id.text_temporada_numero);
            mImageView=itemView.findViewById(R.id.image_view_season);
            mButtonView=itemView.findViewById(R.id.botonEpisodio);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mListener!=null){
                        int position=getAbsoluteAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
