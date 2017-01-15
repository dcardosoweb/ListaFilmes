package com.example.danielgoncalves.listafilmes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    @SuppressLint("ValidFragment")
    public class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            MovieEntity movie = getIntent().getExtras().getParcelable("movie");
            TextView txvTitle = (TextView) rootView.findViewById(R.id.txvTitle);
            TextView txvRating = (TextView) rootView.findViewById(R.id.txvRating);
            TextView txvReleaseDate = (TextView) rootView.findViewById(R.id.txvReleaseDate);
            TextView txvSysnopsys = (TextView) rootView.findViewById(R.id.txvSynopsys);
            ImageView imvCover = (ImageView) rootView.findViewById(R.id.imgCover);

            txvSysnopsys.setText(movie.getSynopsis());
            txvReleaseDate.setText(movie.getReleaseDate());
            txvRating.setText(movie.getRating().toString());
            Picasso.with(getContext()).load(movie.getCoverPath()).into(imvCover);
            txvTitle.setText(movie.getTitle());
            return rootView;
        }
    }

    public static class DetailFragment extends Fragment {
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();

        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            Intent intent = getActivity().getIntent();
            if (intent != null && intent.hasExtra("movie")) {
                MovieEntity movie = intent.getExtras().getParcelable("movie");
                TextView txvTitle = (TextView) rootView.findViewById(R.id.txvTitle);
                TextView txvRating = (TextView) rootView.findViewById(R.id.txvRating);
                TextView txvReleaseDate = (TextView) rootView.findViewById(R.id.txvReleaseDate);
                TextView txvSysnopsys = (TextView) rootView.findViewById(R.id.txvSynopsys);
                ImageView imvCover = (ImageView) rootView.findViewById(R.id.imgCover);

                txvSysnopsys.setText(movie.getSynopsis());
                txvReleaseDate.setText(movie.getReleaseDate());
                txvRating.setText(movie.getRating().toString());
                Picasso.with(getContext()).load(movie.getCoverPath()).into(imvCover);
                txvTitle.setText(movie.getTitle());
            }

            return rootView;
        }
    }

}
