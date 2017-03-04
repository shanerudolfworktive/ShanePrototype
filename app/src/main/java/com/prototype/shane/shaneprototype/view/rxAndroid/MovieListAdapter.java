package com.prototype.shane.shaneprototype.view.rxAndroid;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.prototype.shane.shaneprototype.R;
import com.prototype.shane.shaneprototype.model.rxAndroid.MovieSearchModel;
import com.squareup.picasso.Picasso;

/**
 * Created by shane on 3/1/17.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder>{

    private MovieSearchModel movieSearchModel = new MovieSearchModel();

    private MovieListClickListener movieListClickListener;

    public MovieListAdapter() {
    }

    public void setMovieSearchModel(MovieSearchModel movieSearchModel){
        this.movieSearchModel = movieSearchModel;
    }

    public void setOnMovieListClickListener(MovieListClickListener movieListClickListener){
        this.movieListClickListener = movieListClickListener;
    }

    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final MovieSearchModel.MovieModel movieModel = movieSearchModel.Search.get(position);
        holder.titleTextView.setText(movieModel.Title);
        holder.yearTextView.setText(movieModel.Year);
        Picasso.with(holder.movieImageView.getContext()).load(movieModel.Poster).fit().centerInside().into(holder.movieImageView);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieListClickListener != null) movieListClickListener.onClick(movieModel);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieSearchModel.Search.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView titleTextView;
        public TextView yearTextView;
        public ImageView movieImageView;
        public View rootView;
        public ViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            titleTextView = (TextView) rootView.findViewById(R.id.textViewTitle);
            yearTextView = (TextView) rootView.findViewById(R.id.textViewYear);
            movieImageView = (ImageView) rootView.findViewById(R.id.imageViewMovie);

        }
    }

    interface MovieListClickListener{
        void onClick(MovieSearchModel.MovieModel movieModel);
    }
}
