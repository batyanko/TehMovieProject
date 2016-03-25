package com.example.batyanko.tehmovieproject;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 */
public class GridMainFragment extends Fragment {

    private final String MY_KEY = "нъцки";

    private ArrayList<Thumb> thumbList;
    private ThumbAdapter thumbAdapter;
    private GridView gridView;
    private int gridSize = 4;
    private String[][] movieData;

    public GridMainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_grid, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_movietask) {
            Log.e("Cyk", "bahmaamu");
            String sortOrder = PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getString(getString(R.string.pref_key_sort_criteria), getString(R.string.pref_default_sort_criteria));

            MovieTask movieTask = new MovieTask();
            movieTask.execute(sortOrder);
            return true;
        }
        if (id == R.id.action_tester) {
            String sortOrder = PreferenceManager.getDefaultSharedPreferences(getActivity())
                    .getString(getString(R.string.pref_key_sort_criteria), getString(R.string.pref_default_sort_criteria));
            Log.v("CalledPreference", sortOrder);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.grid_main, container, false);

        final Thumb[] thumbArray = {
                new Thumb(R.drawable.blacks),
                new Thumb(R.drawable.whitesnake),
                new Thumb(R.drawable.lemmy),
                new Thumb(R.drawable.lemmy),
                new Thumb(R.drawable.lemmy),
                new Thumb(R.drawable.lemmy),
                new Thumb(R.drawable.saxon),
                new Thumb(R.drawable.acdc)
        };

        thumbList = new ArrayList<Thumb>(Arrays.asList(thumbArray));

        thumbAdapter = new ThumbAdapter(getActivity(), thumbList);


        gridView = (GridView) rootView.findViewById(R.id.movie_gridview);
        gridView.setAdapter(thumbAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thumb thumb = thumbAdapter.getItem(position);
                String[] movieData = new String[7];
                movieData[0] = thumb.id;
                movieData[1] = thumb.posterPath;
                movieData[2] = thumb.originalTitle;
                movieData[3] = thumb.overview;
                movieData[4] = thumb.voteAverage;
                movieData[5] = thumb.releaseDate;
                movieData[6] = thumb.posterAdress;

                Intent intent = new Intent(getActivity(), DetailActivity.class)
                        .putExtra(Intent.EXTRA_TEXT, movieData);
                        //.putExtra(Intent.EXTRA_TEXT, movieId);
                startActivity(intent);
            }
        });

        /*        Picasso.with(getActivity()).load("http://i.imgur.com/DvpvklR.png")
                .into((ImageView) rootView.findViewById(R.id.AltMainImage));*/


        return rootView;
    }

    private String[][] getMovieDataFromJSON(String JSONString) throws JSONException {

        //JSON Objects
        final String OWM_RESULTS = "results";

        JSONObject moviesJSON = new JSONObject(JSONString);
        JSONArray movieResults = moviesJSON.getJSONArray(OWM_RESULTS);
        int resultsCount = movieResults.length();

        String[][] movieDataOutput = new String[7][resultsCount];   //make +1 room for URLs

        for (int i = 0; i < resultsCount; i++) {
            movieDataOutput[0][i] = movieResults.getJSONObject(i).getString("id");
            movieDataOutput[1][i] = movieResults.getJSONObject(i).getString("poster_path");
            movieDataOutput[2][i] = movieResults.getJSONObject(i).getString("original_title");
            movieDataOutput[3][i] = movieResults.getJSONObject(i).getString("overview");
            movieDataOutput[4][i] = movieResults.getJSONObject(i).getString("vote_average");
            movieDataOutput[5][i] = movieResults.getJSONObject(i).getString("release_date");
            Log.v("MovieData " + i,
                    " " + movieDataOutput[0][i]
                    + " " + movieDataOutput[1][i]
                    + " " + movieDataOutput[2][i]
                    + " " + movieDataOutput[3][i]
                    + " " + movieDataOutput[4][i]
                    + " " + movieDataOutput[5][i]);
        }

        return movieDataOutput;

    }

    private String getPosterIdFromJSON(String JSONString) throws JSONException {

        JSONObject imagesJSON = new JSONObject(JSONString);
        JSONArray posters = imagesJSON.getJSONArray("posters");

        String posterId = "/ue4aK01qLd1DPMuGPnAlW2q3QId.jpg";

        try {
            posterId = posters.getJSONObject(0).getString("file_path");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posterId;
    }

    public class MovieTask extends AsyncTask<String, Void, String[][]> {

        private final String LOG_TAG = MovieTask.class.getSimpleName();

        @Override
        protected String[][] doInBackground(String... params) {

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String moviesJsonStr;
            String imagesJsonString = null;

            try {
                final String DISCOVER_BASE_URL = "http://api.themoviedb.org/3/discover/movie";
                final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
                final String SORT_PARAM = "sort_by";
                final String KEY_PARAM = "api_key";

                Uri moviesUri = Uri.parse(DISCOVER_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM, params[0])
                        .appendQueryParameter(KEY_PARAM, MY_KEY).build();

                Log.v("BuiltURI", moviesUri.toString());

                URL moviesUrl = new URL(moviesUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) moviesUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }

                moviesJsonStr = buffer.toString();

                Log.v(LOG_TAG, moviesJsonStr);

                try {
                    movieData = getMovieDataFromJSON(moviesJsonStr);

                    //Get image Ids from the image JSON
                    for (int j = 0; j < gridSize; j++) {

                        /*//Images JSON URL
                        Uri.Builder imagesBuilder = new Uri.Builder();
                        imagesBuilder.scheme("http")
                                .authority("api.themoviedb.org")
                                .appendPath("3")
                                .appendPath("movie")
                                .appendPath("MOVIE_ID") //Replace with Movie ID
                                .appendPath("images")
                                .appendQueryParameter(KEY_PARAM, MY_KEY).build();

                        String imagesString = imagesBuilder.build().toString();
                        URL imagesUrl = new URL(imagesString);*/
                        //Log.v("images URL", imagesString);

                        String posterId = movieData[1][j];

                        Uri.Builder imageUrlBuilder = new Uri.Builder();

                        imageUrlBuilder.scheme("http")
                                .authority("image.tmdb.org")
                                .appendPath("t")
                                .appendPath("p")
                                .appendPath("w342")
                                .appendEncodedPath(posterId)
                                .build();

                        String posterURLString = imageUrlBuilder.build().toString();
                        Log.v("Poster URL", posterURLString);
                        movieData[6][j] = posterURLString;
                    }
                    return movieData;


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("ABEEEEEEEEEEEEEEEEEEEE", "Error closing stream", e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[][] movieData) {

            Log.v("SampleResultURL", movieData[1][1]);

            thumbAdapter.clear();
            for (int i=0; i<gridSize; i++){
                thumbList.add(
                        new Thumb(
                                movieData[0][i],
                                movieData[1][i],
                                movieData[2][i],
                                movieData[3][i],
                                movieData[4][i],
                                movieData[5][i],
                                movieData[6][i])
                );
            }
            thumbAdapter.notifyDataSetChanged();

           /* for (String posterAdress : result) {
                thumbAdapter.add(posterAdress);
            }*/
        }
    }
}
