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

public class AdapterCast extends RecyclerView.Adapter<AdapterCast.ExampleViewHolder> {

    private Context mContextCast;
    private ArrayList<ItemCast> mListCast;

    public AdapterCast(Context context, ArrayList<ItemCast> listCast) {
        mContextCast = context;
        mListCast = listCast;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContextCast).inflate(R.layout.item_cast,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ItemCast currentItem=mListCast.get(position);
        String name=currentItem.getsName();
        String image=currentItem.getsImage();
        holder.mTextViewCast.setText(name);
        Picasso.get().load(image).fit().centerInside().into(holder.mImageViewCast);
    }

    @Override
    public int getItemCount() {
        return mListCast.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public TextView mTextViewCast;
        public ImageView mImageViewCast;

        public ExampleViewHolder(View itemView){
            super(itemView);
            mTextViewCast=itemView.findViewById(R.id.text_view_cast);
            mImageViewCast=itemView.findViewById(R.id.image_view_cast);
        }
    }
}
