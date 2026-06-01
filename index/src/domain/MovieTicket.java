package domain;

public class MovieTicket {
    private MovieScreening movieScreening;
    private int seatRow;
    private int seatNr;
    private boolean isPremium;

    public MovieTicket(MovieScreening movieScreening, boolean isPremiumReservation, int seatRow, int seatNr) {
        this.movieScreening = movieScreening;
        this.isPremium = isPremiumReservation;
        this.seatRow = seatRow;
        this.seatNr = seatNr;
    }

    public MovieScreening getMovieScreening() {
        return movieScreening;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public int getSeatNr() {
        return seatNr;
    }

    public boolean getPremiumTicket() {
        return isPremium;
    }

    public double getPrice() {
        return movieScreening.getPricePerSeat();
    }

    public String getSeating() {
        return "Row " + seatRow + ", Seat " + seatNr;
    }

    @Override
    public String toString() {
        return movieScreening.getMovie().getTitle() + " - " + getSeating() + 
               (isPremium ? " (Premium)" : "") + " - €" + getPrice();
    }
}
