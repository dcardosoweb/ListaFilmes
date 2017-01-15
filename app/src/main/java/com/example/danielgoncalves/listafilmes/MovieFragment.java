package com.example.danielgoncalves.listafilmes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MovieFragment extends Fragment {
    public ArrayAdapter<MovieEntity> arrayAdapter;
    public  ListView ltvMovie;
    private ArrayList<MovieEntity> movieData;
    Context thiscontext;

    public MovieFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        loadMovies();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieData = new ArrayList<>();
        }
        else {
            movieData = savedInstanceState.getParcelableArrayList("movies");
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        movieData = new ArrayList<>();
        thiscontext = container.getContext();
        arrayAdapter = new MovieAdapter(thiscontext, movieData);

        ltvMovie = (ListView) rootView.findViewById(R.id.list_view_movie);
        ltvMovie.setAdapter(arrayAdapter);
        ltvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent detailIntent = new Intent(thiscontext, DetailActivity.class);
                detailIntent.putExtra("movie", (MovieEntity)adapterView.getAdapter().getItem(i));
                thiscontext.startActivity(detailIntent);
            }
        });
        return rootView;
    }

    private void loadMovies(){
        FetchMovieTask movieTask = new FetchMovieTask();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String listTypeVal = sharedPref.getString(getString(R.string.pref_list_type_key), getString(R.string.pref_list_type_default));
        movieTask.execute(listTypeVal);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, MovieEntity[]> {

        @Override
        protected void onPostExecute(MovieEntity[] resultStrs) {
            super.onPostExecute(resultStrs);

            arrayAdapter.clear();
            for (MovieEntity M : resultStrs) {
                arrayAdapter.add(M);
            }
        }

        private final String LOG_TAG = FetchMovieTask.class.getSimpleName();

        @Override
        protected MovieEntity[] doInBackground(String... params) {
            {

                HttpURLConnection urlConnection = null;
                BufferedReader reader = null;
                String movieDBStrJson = null;

                try {

                    final String MOVIEDB_BASE_URL =
                            "https://api.themoviedb.org/3/movie/"+params[0]+"?";
                    final String APPKEY_PARAM = "api_key";
                    final String LANGUAGE_PARAM = "language";
                    final String API_KEY ="";//Put your API KEY HERE

                    Uri builtUri = Uri.parse(MOVIEDB_BASE_URL).buildUpon()
                            .appendQueryParameter(APPKEY_PARAM, API_KEY)
                            .appendQueryParameter(LANGUAGE_PARAM, "pt-BR")
                            .build();

                    URL url = new URL(builtUri.toString());
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();
                    Log.e("Do server call", "Info ");

                    // Read the input stream into a String
                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuffer buffer = new StringBuffer();
                    if (inputStream == null) {
                        movieDBStrJson = null;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line + "\n");
                    }

                    if (buffer.length() == 0) {
                        // Stream was empty.  No point in parsing.
                        movieDBStrJson = null;
                    }
                    movieDBStrJson = buffer.toString();

                    Log.e(LOG_TAG, "MovieDB Json string:  " + movieDBStrJson);

                } catch (IOException e) {
                    Log.e("PlaceholderFragment", "Error ", e);
                    // If the code didn't successfully get the weather data, there's no point in attempting
                    // to parse it.
                    movieDBStrJson = null;
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (final IOException e) {
                            Log.e("PlaceholderFragment", "Error closing stream", e);
                        }
                    }
                }

                try {
                    return getMovieDataFromJson(movieDBStrJson.toString());
                } catch (JSONException e) {
                    Log.e(LOG_TAG, e.getMessage(), e);
                    e.printStackTrace();
                }
                return null;
            }
        }

        private MovieEntity[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String MOVIE_COVER_IMAGE = "poster_path";
            final String MOVIE_ID = "id";
            final String MOVIE_TITLE = "title";
            final String MOVIE_VOTE_AVERAGE = "vote_average";
            final String MOVIE_RELEASE_DATE = "release_date";
            final String MOVIE_OVERVIEW = "overview";
            final String JSON_ROOT = "results";

            MovieEntity movie = null;
            final String coverBasePath="http://image.tmdb.org/t/p/w342/";
            final String DETAILBasePath="https://api.themoviedb.org/3/movie/";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(JSON_ROOT);
            MovieEntity[] movieResult = new MovieEntity[ movieArray.length()];

            movieData = new ArrayList<>();

            for(int i = 0; i < movieArray.length(); i++) {

                JSONObject movieObj = movieArray.getJSONObject(i);
                movie = new MovieEntity();

                movie.setCoverPath(coverBasePath+movieObj.getString(MOVIE_COVER_IMAGE));
                movie.setTitle(movieObj.getString(MOVIE_TITLE));
                movie.setSynopsis(movieObj.getString(MOVIE_OVERVIEW));
                movie.setReleaseDate(movieObj.getString(MOVIE_RELEASE_DATE));
                movie.setRating(movieObj.getDouble(MOVIE_VOTE_AVERAGE));
                movie.setDetailURL(DETAILBasePath +movieObj.getString(MOVIE_ID));
                movieData.add(movie);
            }
            return movieData.toArray(movieResult);
        }
    }
}
