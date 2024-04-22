package hu.inf.szte.ui.movies;

import hu.inf.szte.model.Movie;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.inf.szte.databinding.MovieItemBinding;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private final MovieItemBinding binding;

        public MovieViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Movie movie) {
            binding.movieTitle.setText(movie.getName());
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