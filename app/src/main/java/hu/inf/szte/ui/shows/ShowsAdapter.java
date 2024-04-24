package hu.inf.szte.ui.shows;

import hu.inf.szte.R;
import hu.inf.szte.model.Show;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.inf.szte.databinding.ShowItemBinding;

public class ShowsAdapter extends RecyclerView.Adapter<ShowsAdapter.ShowViewHolder> {

    private List<Show> shows;
    private OnShowClickListener listener;

    public static class ShowViewHolder extends RecyclerView.ViewHolder {
        private final ShowItemBinding binding;
        private final TextView showMovieTextView;
        private final TextView showDatetimeTextView;

        public ShowViewHolder(ShowItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.showMovieTextView = binding.movieTextView;
            this.showDatetimeTextView = binding.datetimeTextView;
        }

        public void bind(Show show) {
            showMovieTextView.setText(show.getMovie());
            showDatetimeTextView.setText(show.getDatetime().toString());
        }

        public ShowViewHolder(ShowItemBinding binding, OnShowClickListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.showMovieTextView = binding.movieTextView;
            this.showDatetimeTextView = binding.datetimeTextView;

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onShowClick(position);
                }
            });
        }
    }

    public interface OnShowClickListener {
        void onShowClick(int position);
    }


    public ShowsAdapter(OnShowClickListener listener) {
        this.shows = new ArrayList<>();
        this.listener = listener;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ShowItemBinding binding = ShowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        if (listener != null) { // Check if listener is not null before using it
            return new ShowViewHolder(binding, listener);
        } else {
            return new ShowViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(ShowViewHolder holder, int position) {
        holder.bind(shows.get(position));
    }

    @Override
    public int getItemCount() {
        return shows.size();
    }


}