package com.prototype.shane.shaneprototype.view.flickrSharkGrid;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.model.flickrShark.FlickrSearchPhotoModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by shane on 7/9/16.
 */
public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {
    ArrayList<FlickrSearchPhotoModel.PhotoModel> photos;
    Integer itemCount = null;
    public void setData(ArrayList<FlickrSearchPhotoModel.PhotoModel> photos){
        this.photos = photos;
        if(itemCount == null || itemCount == 0) itemCount = this.photos.size();
    }

    OnItemSelectedListener onItemSelectedListener;
    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override
    public MainRecycleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_view_main_shark_display, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int modulizedPosition = position % photos.size();
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectedListener != null) onItemSelectedListener.onItemSelected(holder.imageView.getDrawable(), photos.get(modulizedPosition));
            }
        });
        Picasso.with(holder.imageView.getContext()).load(photos.get(modulizedPosition).thumbNeilUrl).fit().centerInside().into(holder.imageView);
    }

    public void setItemCount(int itemCount){
        this.itemCount =itemCount;
    }

    @Override
    public int getItemCount() {
        return itemCount == null ? 0 : itemCount;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View rootView;

        public ViewHolder(View rootView) {
            super(rootView);
            imageView = (ImageView)rootView.findViewById(R.id.imageViewPhoto);
            this.rootView = rootView;
        }
    }

    public interface OnItemSelectedListener{
        void onItemSelected(Drawable drawable, FlickrSearchPhotoModel.PhotoModel photo);
    }

}


