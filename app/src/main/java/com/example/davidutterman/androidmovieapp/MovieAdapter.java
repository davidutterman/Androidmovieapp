package com.example.davidutterman.androidmovieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.davidutterman.androidmovieapp.model.Movie;
import com.squareup.picasso.Picasso;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Movies mMovies;
    private final Context mContext;
    private final MovieAdapterOnClickHandler mClickHandler;

    MovieAdapter(Context context, MovieAdapterOnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
    }

    void setMovies(Movies movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int itemId = R.layout.movie_list_item;
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(itemId, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String moviePosterUrl = mMovies.list.get(position).getPosterUrl(mContext);
        Picasso.with(mContext)
                .load(moviePosterUrl)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        ViewHolder(View view) {
            super(view);
            mImageView = (ImageView) view.findViewById(R.id.movie_poster);
            mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            Movie movie = mMovies.list.get(getAdapterPosition());
            mClickHandler.onClick(movie);
        }
    }
}