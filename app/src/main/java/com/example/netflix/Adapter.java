package com.example.netflix;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ExampleViewHolder> implements Filterable {

    private Context mContext;
    private ArrayList<Item> mList;
    private List<Item> exampleListFull;
    private onItemClickListener mListener;

    public interface onItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        mListener=listener;
    }

    public Adapter(Context context, ArrayList<Item> list){
        mContext=context;
        mList=list;
        exampleListFull=new ArrayList<>(list);
    }

    @NonNull
    @Override
    public Adapter.ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);
        return new ExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        Item currentItem=mList.get(position);
        String imageUrl=currentItem.getmImageUrl();
        String serie=currentItem.getmSerie();
        holder.mTextView.setText(serie);
        Picasso.get().load(imageUrl).fit().centerInside().into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ExampleViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView;

        public ExampleViewHolder(View itemView){
            super(itemView);
            mImageView=itemView.findViewById(R.id.image_view);
            mTextView=itemView.findViewById(R.id.text_view_season);

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

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Item> filteredList=new ArrayList<>();
            //si esta vacio, mostrar todos
            if(charSequence == null || charSequence.length() == 0){
                filteredList.addAll(exampleListFull);
            }else{
                //si la serie se encuentra
                String filterPattern=charSequence.toString().toLowerCase().trim();
                for(Item item: exampleListFull){
                    if(item.getmSerie().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mList.clear();
            mList.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}
