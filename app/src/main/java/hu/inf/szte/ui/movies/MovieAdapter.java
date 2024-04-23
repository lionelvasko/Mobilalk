package hu.inf.szte.ui.movies;

import hu.inf.szte.R;
import hu.inf.szte.model.Movie;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import hu.inf.szte.databinding.MovieItemBinding;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final MovieItemBinding binding;
        private final ImageView movieImage;
        private final TextView movieTitle;

        public MovieViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.movieImage = binding.movieImage;
            this.movieTitle = binding.movieTitle;
        }

        public void bind(Movie movie) {
            movieTitle.setText(movie.getName());

            // Use Picasso to load the image from the URL
            if (movie.getPicture() != null && !movie.getPicture().isEmpty()) {
                Picasso.get()
                        .load(movie.getPicture())
                        .into(movieImage);
            } else {
                // Handle the case where the URL is null or empty
                // For example, you can set a placeholder image
                movieImage.setImageResource(R.drawable.panorama_variant);
                Log.i("MovieAdapter", "No picture URL for movie: " + movie.getName());
                Log.i("MovieAdapter", movie.getPicture());
            }
        }
    }

    public MovieAdapter() {
        this.movies = new ArrayList<>();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout with ViewBinding
        MovieItemBinding binding = MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
}