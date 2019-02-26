package com.example.flickster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.flickster.adapters.MoviesAdapter;
import com.example.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    //Gives access to the list of movies
    private static final String MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    //the list which will store each movie mobject
    List<Movie> movies;

    //Add RecyclerView support library to the gradle build file - DONE
    //Define a model class to use as the data source - DONE (the Movie Class)
    //Add a RecyclerView to your activity to display the items - DONE
    //Create a custom row layout XML file to visualize the item- DONE (item_movie.xml)
    //Create a RecyclerView.Adapter and ViewHolder to render the item - DONE
    //Bind the adapter to the data source to populate the RecyclerView - DONE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecyclerView binds the java code with the xml layout
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies=new ArrayList<>();


        final MoviesAdapter adapter = new MoviesAdapter(this, movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMovies.setAdapter(adapter);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //JSONObject as opposed to JSONArray because the data we get back is a JSON object
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray movieJsonArray = response.getJSONArray("results");
                    movies.addAll(Movie.fromJsonArray(movieJsonArray));
                    adapter.notifyDataSetChanged();
                    Log.d("smile", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }
}
