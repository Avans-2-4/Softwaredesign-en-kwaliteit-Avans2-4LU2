package domain;

import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private boolean isStudentOrder;
    private List<MovieTicket> orderLines;

    public Order(int orderId, boolean isStudentOrder) {
        this.orderId = orderId;
        this.isStudentOrder = isStudentOrder;
        this.orderLines = new ArrayList<>();
    }

    public void addOrderLine(MovieTicket movieTicket) {
        orderLines.add(movieTicket);
    }

    public int getOrderLines() {
        return orderLines.size();
    }

    public List<MovieTicket> getTickets() {
        return orderLines;
    }

    public int getOrderId() {
        return orderId;
    }

    public boolean isStudentOrder() {
        return isStudentOrder;
    }

    /**
     * Calculates the total price based on the cinema's pricing rules:
     * - Student orders: every 2nd ticket is free
     * - Non-student orders on weekdays: every 2nd ticket is free
     * - Non-student orders on weekends with >= 6 tickets: 10% group discount
     * - Premium tickets add €2 for students, €3 for non-students
     */
    public double calculatePrice() {
        if (orderLines.isEmpty()) {
            return 0.0;
        }

        double totalPrice = 0.0;
        boolean isWeekend = isWeekendScreening();
        int ticketCount = orderLines.size();

        // Calculate base price with discounts
        for (int i = 0; i < orderLines.size(); i++) {
            MovieTicket ticket = orderLines.get(i);
            double basePrice = ticket.getPrice();
            double ticketPrice = basePrice;

            // Apply every 2nd ticket free discount for students or on weekdays
            if (isStudentOrder || !isWeekend) {
                if ((i + 1) % 2 == 0) {
                    // Every 2nd ticket is free
                    ticketPrice = 0.0;
                }
            }

            // Add premium surcharge
            if (ticket.getPremiumTicket() && ticketPrice > 0) {
                double premiumSurcharge = isStudentOrder ? 2.0 : 3.0;
                ticketPrice += premiumSurcharge;
            } else if (ticket.getPremiumTicket() && ticketPrice == 0.0 && ((i + 1) % 2 == 0)) {
                // For free tickets (every 2nd), premium surcharge is also waived
                // This is already handled by ticketPrice == 0.0
            }

            totalPrice += ticketPrice;
        }

        // Apply 10% group discount for non-students on weekends with 6+ tickets
        if (!isStudentOrder && isWeekend && ticketCount >= 6) {
            totalPrice *= 0.9; // 10% discount
        }

        return Math.round(totalPrice * 100.0) / 100.0; // Round to 2 decimal places
    }

    /**
     * Determines if all screenings in this order are on weekends
     * Weekends are Saturday and Sunday
     */
    private boolean isWeekendScreening() {
        if (orderLines.isEmpty()) {
            return false;
        }

        // Check if all tickets are for weekend screenings
        for (MovieTicket ticket : orderLines) {
            DayOfWeek dayOfWeek = ticket.getMovieScreening().getDateAndTime().getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                return false; // At least one is on a weekday
            }
        }
        return true; // All are on weekends
    }

    /**
     * Exports the order to a plain text file
     */
    public void exportToPlainText() {
        String filename = "order_" + orderId + ".txt";
        filename = "export/" + filename;
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("=== CINEMA ORDER #" + orderId + " ===\n");
            writer.write("Order Type: " + (isStudentOrder ? "Student" : "Regular") + "\n");
            writer.write("Number of Tickets: " + orderLines.size() + "\n");
            writer.write("---\n");

            for (int i = 0; i < orderLines.size(); i++) {
                MovieTicket ticket = orderLines.get(i);
                writer.write("\nTicket " + (i + 1) + ":\n");
                writer.write("  Movie: " + ticket.getMovieScreening().getMovie().getTitle() + "\n");
                writer.write("  Screening: " + ticket.getMovieScreening().getDateAndTime() + "\n");
                writer.write("  Seating: " + ticket.getSeating() + "\n");
                writer.write("  Type: " + (ticket.getPremiumTicket() ? "Premium" : "Standard") + "\n");
                writer.write("  Base Price: €" + ticket.getPrice() + "\n");
            }

            writer.write("\n---\n");
            writer.write("TOTAL PRICE: €" + String.format("%.2f", calculatePrice()) + "\n");
            writer.write("===================\n");

            System.out.println("Order exported to: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Exports the order to a JSON file
     */
    public void exportToJSON(String filename) {
        if (filename == null || filename.isEmpty()) {
            filename = "order_" + orderId + ".json";
        }
        filename = "export/" + filename;

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("{\n");
            writer.write("  \"orderId\": " + orderId + ",\n");
            writer.write("  \"isStudentOrder\": " + isStudentOrder + ",\n");
            writer.write("  \"tickets\": [\n");

            for (int i = 0; i < orderLines.size(); i++) {
                MovieTicket ticket = orderLines.get(i);
                writer.write("    {\n");
                writer.write("      \"ticketNumber\": " + (i + 1) + ",\n");
                writer.write("      \"movie\": \"" + escapeJson(ticket.getMovieScreening().getMovie().getTitle()) + "\",\n");
                writer.write("      \"screening\": \"" + ticket.getMovieScreening().getDateAndTime() + "\",\n");
                writer.write("      \"seating\": \"" + ticket.getSeating() + "\",\n");
                writer.write("      \"isPremium\": " + ticket.getPremiumTicket() + ",\n");
                writer.write("      \"basePrice\": " + ticket.getPrice() + "\n");
                writer.write("    }");

                if (i < orderLines.size() - 1) {
                    writer.write(",");
                }
                writer.write("\n");
            }

            writer.write("  ],\n");
            writer.write("  \"totalPrice\": " + calculatePrice() + "\n");
            writer.write("}\n");

            System.out.println("Order exported to: " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Helper method to escape special characters in JSON strings
     */
    private String escapeJson(String input) {
        if (input == null) {
            return "";
        }
        return input.replace("\"", "\\\"")
                   .replace("\\", "\\\\")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}
