package katas;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import model.Bookmark;
import model.Movie;
import model.MovieList;
import util.DataUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    Goal: Retrieve each video's id, title, middle interesting moment time, and smallest box art url
    DataSource: DataUtil.getMovies()
    Output: List of ImmutableMap.of("id", 5, "title", "some title", "time", new Date(), "url", "someUrl")
*/
public class Kata9 {
    public static List<Map> execute() {
        List<MovieList> movieLists = DataUtil.getMovieLists();
        List<Map> query = movieLists.stream()
                .map(moviesList -> moviesList.getVideos())
                .flatMap(movies -> movies.stream())
                        .map(movie -> ImmutableMap.of("id", movie.getId(), "title", movie.getTitle(), "time", movie.getInterestingMoments()
                                        .stream()
                                        .filter(interestingMoment -> interestingMoment.getType().equals("Middle"))
                                        .findAny()
                                        .get()
                                        .getTime(),
                                "url", movie.getBoxarts()
                                        .stream()
                                        .reduce((boxArt, boxArt2) -> boxArt.getHeight() * boxArt.getWidth() < boxArt2.getHeight() * boxArt2.getWidth() ? boxArt : boxArt2)
                                        .get()
                                        .getUrl()))
                        .collect(Collectors.toUnmodifiableList());
//                .collect(Collectors.toUnmodifiableList());

        return query;
    }
}
