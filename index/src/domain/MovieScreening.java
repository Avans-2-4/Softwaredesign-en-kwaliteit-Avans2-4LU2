package domain;

import java.time.LocalDateTime;

public class MovieScreening {
    private Movie movie;
    private LocalDateTime dateAndTime;
    private double pricePerSeat;

    public MovieScreening(Movie movie, LocalDateTime dateAndTime, double pricePerSeat) {
        this.movie = movie;
        this.dateAndTime = dateAndTime;
        this.pricePerSeat = pricePerSeat;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    @Override
    public String toString() {
        return movie.toString() + " - " + dateAndTime + " (€" + pricePerSeat + ")";
    }
}
