import domain.Movie;
import domain.MovieScreening;
import domain.MovieTicket;
import domain.Order;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Create movies
        Movie movie1 = new Movie("Avatar 2");
        Movie movie2 = new Movie("Oppenheimer");

        // Create screenings
        // Monday screening (weekday)
        MovieScreening screening1 = new MovieScreening(
                movie1,
                LocalDateTime.of(2024, 6, 3, 19, 30), // Monday
                10.0
        );

        // Saturday screening (weekend)
        MovieScreening screening2 = new MovieScreening(
                movie1,
                LocalDateTime.of(2024, 6, 8, 20, 0), // Saturday
                12.0
        );

        MovieScreening screening3 = new MovieScreening(
                movie2,
                LocalDateTime.of(2024, 6, 8, 18, 0), // Saturday
                11.0
        );

        movie1.addScreening(screening1);
        movie1.addScreening(screening2);
        movie2.addScreening(screening3);

        System.out.println("=== CINEMA BOOKING SYSTEM ===\n");

        // Example 1: Student order with 2 tickets on weekday
        System.out.println("--- Example 1: Student Order (Weekday) ---");
        Order order1 = new Order(1, true);
        order1.addOrderLine(new MovieTicket(screening1, false, 5, 10));
        order1.addOrderLine(new MovieTicket(screening1, false, 5, 11));
        System.out.println("Order ID: " + order1.getOrderId());
        System.out.println("Student Order: " + order1.isStudentOrder());
        System.out.println("Number of Tickets: " + order1.getOrderLines());
        System.out.println("Total Price: €" + String.format("%.2f", order1.calculatePrice()));
        System.out.println("Expected: €10.00 (2nd ticket free for students)\n");

        // Example 2: Non-student order with 3 tickets on weekday
        System.out.println("--- Example 2: Non-Student Order (Weekday) ---");
        Order order2 = new Order(2, false);
        order2.addOrderLine(new MovieTicket(screening1, false, 5, 1));
        order2.addOrderLine(new MovieTicket(screening1, true, 5, 2));
        order2.addOrderLine(new MovieTicket(screening1, false, 5, 3));
        System.out.println("Order ID: " + order2.getOrderId());
        System.out.println("Student Order: " + order2.isStudentOrder());
        System.out.println("Number of Tickets: " + order2.getOrderLines());
        System.out.println("Total Price: €" + String.format("%.2f", order2.calculatePrice()));
        System.out.println("Expected: €23.00 (1st: €10, 2nd free (premium), 3rd: €13)\n");

        // Example 3: Non-student order with 6 premium tickets on weekend (10% group discount)
        System.out.println("--- Example 3: Non-Student Order (Weekend, 6+ tickets) ---");
        Order order3 = new Order(3, false);
        for (int i = 0; i < 6; i++) {
            order3.addOrderLine(new MovieTicket(screening2, true, 6, i + 1));
        }
        System.out.println("Order ID: " + order3.getOrderId());
        System.out.println("Student Order: " + order3.isStudentOrder());
        System.out.println("Number of Tickets: " + order3.getOrderLines());
        System.out.println("Total Price: €" + String.format("%.2f", order3.calculatePrice()));
        System.out.println("Expected: €85.50 (6 premium tickets at €15 each = €90, 10% discount)\n");

        // Export examples
        System.out.println("--- Exporting Orders ---");
        order1.exportToPlainText();
        order2.exportToJSON("order_2.json");
        order3.exportToJSON(null); // Uses default filename
    }
}