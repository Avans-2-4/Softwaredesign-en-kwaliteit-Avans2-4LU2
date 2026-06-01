package domain;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String title;
    private List<MovieScreening> screenings;

    public Movie(String title) {
        this.title = title;
        this.screenings = new ArrayList<>();
    }

    public void addScreening(MovieScreening screening) {
        screenings.add(screening);
    }

    public String getTitle() {
        return title;
    }

    public List<MovieScreening> getScreenings() {
        return screenings;
    }

    @Override
    public String toString() {
        return title;
    }
}
