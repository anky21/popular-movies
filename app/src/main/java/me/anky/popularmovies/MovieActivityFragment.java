package me.anky.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A fragment containing the grid view of movie posters
 */
public class MovieActivityFragment extends Fragment {

    private PopularMovieAdapter popularMovieAdapter;

    private ArrayList<PopularMovie> movieList;

    PopularMovie[] popularMovies = {
            new PopularMovie("/5N20rQURev5CNDcMjHVUZhpoCNC.jpg", "Captain America: Civil War",
                    "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.",
                    6.81, "2016-04-27"),
            new PopularMovie("/zSouWWrySXshPCT4t3UKCQGayyo.jpg", "X-Men: Apocalypse",
                    "After the re-emergence of the world's first mutant, world-destroyer Apocalypse, the X-Men must unite to defeat his extinction level plan.",
                    6.09, "2016-05-18"),
            new PopularMovie("/6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg", "The Legend of Tarzan",
                    "Tarzan, having acclimated to life in London, is called back to his former home in the jungle to investigate the activities at a mining encampment.",
                    4.95, "2016-06-29"),
            new PopularMovie("/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg", "Suicide Squad",
                    "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                    5.91, "2016-08-03"),
            new PopularMovie("/5N20rQURev5CNDcMjHVUZhpoCNC.jpg", "Captain America: Civil War",
                    "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.",
                    6.81, "2016-04-27"),
            new PopularMovie("/zSouWWrySXshPCT4t3UKCQGayyo.jpg", "X-Men: Apocalypse",
                    "After the re-emergence of the world's first mutant, world-destroyer Apocalypse, the X-Men must unite to defeat his extinction level plan.",
                    6.09, "2016-05-18"),
            new PopularMovie("/6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg", "The Legend of Tarzan",
                    "Tarzan, having acclimated to life in London, is called back to his former home in the jungle to investigate the activities at a mining encampment.",
                    4.95, "2016-06-29"),
            new PopularMovie("/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg", "Suicide Squad",
                    "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                    5.91, "2016-08-03"),
            new PopularMovie("/5N20rQURev5CNDcMjHVUZhpoCNC.jpg", "Captain America: Civil War",
                    "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.",
                    6.81, "2016-04-27"),
            new PopularMovie("/zSouWWrySXshPCT4t3UKCQGayyo.jpg", "X-Men: Apocalypse",
                    "After the re-emergence of the world's first mutant, world-destroyer Apocalypse, the X-Men must unite to defeat his extinction level plan.",
                    6.09, "2016-05-18"),
            new PopularMovie("/6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg", "The Legend of Tarzan",
                    "Tarzan, having acclimated to life in London, is called back to his former home in the jungle to investigate the activities at a mining encampment.",
                    4.95, "2016-06-29"),
            new PopularMovie("/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg", "Suicide Squad",
                    "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                    5.91, "2016-08-03"),
            new PopularMovie("/5N20rQURev5CNDcMjHVUZhpoCNC.jpg", "Captain America: Civil War",
                    "Following the events of Age of Ultron, the collective governments of the world pass an act designed to regulate all superhuman activity. This polarizes opinion amongst the Avengers, causing two factions to side with Iron Man or Captain America, which causes an epic battle between former allies.",
                    6.81, "2016-04-27"),
            new PopularMovie("/zSouWWrySXshPCT4t3UKCQGayyo.jpg", "X-Men: Apocalypse",
                    "After the re-emergence of the world's first mutant, world-destroyer Apocalypse, the X-Men must unite to defeat his extinction level plan.",
                    6.09, "2016-05-18"),
            new PopularMovie("/6FxOPJ9Ysilpq0IgkrMJ7PubFhq.jpg", "The Legend of Tarzan",
                    "Tarzan, having acclimated to life in London, is called back to his former home in the jungle to investigate the activities at a mining encampment.",
                    4.95, "2016-06-29"),
            new PopularMovie("/e1mjopzAS2KNsvpbpahQ1a6SkSn.jpg", "Suicide Squad",
                    "From DC Comics comes the Suicide Squad, an antihero team of incarcerated supervillains who act as deniable assets for the United States government, undertaking high-risk black ops missions in exchange for commuted prison sentences.",
                    5.91, "2016-08-03")
    };

    public MovieActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {
            movieList = new ArrayList<PopularMovie>(Arrays.asList(popularMovies));
        } else {
            movieList = savedInstanceState.getParcelableArrayList("movies");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("movies", movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.movie_activity_fragment, container, false);
        popularMovieAdapter = new PopularMovieAdapter(getActivity(), movieList);

        // Get a reference to the GridView, and attach this adapter to it.
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_activity_grid_view);
        gridView.setAdapter(popularMovieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailedActivity.class);
                PopularMovie movieData = movieList.get(i);
                intent.putExtra("movieData", movieData);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
