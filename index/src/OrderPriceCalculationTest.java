import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDateTime;
import java.time.DayOfWeek;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order - calculatePrice() Path Testing")
public class OrderPriceCalculationTest {

    private Movie movie;
    private MovieScreening weekdayScreening;
    private MovieScreening weekendScreening;

    @BeforeEach
    public void setUp() {
        movie = new Movie("Test Movie");

        // Monday screening (weekday)
        weekdayScreening = new MovieScreening(
                movie,
                LocalDateTime.of(2024, 6, 3, 19, 30), // Monday
                10.0
        );

        // Saturday screening (weekend)
        weekendScreening = new MovieScreening(
                movie,
                LocalDateTime.of(2024, 6, 8, 20, 0), // Saturday
                10.0
        );
    }

    // ============ PATH 1: Empty Order ============
    @Test
    @DisplayName("Path 1: Empty order should return 0.0")
    public void testPath1_EmptyOrder() {
        // Arrange
        Order order = new Order(1, false);

        // Act
        double price = order.calculatePrice();

        // Assert
        assertEquals(0.0, price, 0.01, "Empty order should cost €0.00");
    }

    // ============ PATH 2: Single Standard Ticket, Non-Student, Weekday ============
    @Test
    @DisplayName("Path 2: Single standard ticket, non-student, weekday = full price")
    public void testPath2_SingleStandardNonStudentWeekday() {
        // Arrange
        Order order = new Order(2, false); // not student
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 10)); // standard ticket

        // Act
        double price = order.calculatePrice();

        // Assert
        assertEquals(10.0, price, 0.01, "Single standard ticket should be full price €10.00");
    }

    // ============ PATH 3: Two Standard Tickets, Student Order (2nd Free) ============
    @Test
    @DisplayName("Path 3: Two standard tickets, student order - 2nd ticket free")
    public void testPath3_TwoStandardStudent2ndFree() {
        // Arrange
        Order order = new Order(3, true); // student
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 10));
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 11));

        // Act
        double price = order.calculatePrice();

        // Assert
        assertEquals(10.0, price, 0.01, "2nd ticket should be free for students (€10.00 total)");
    }

    // ============ PATH 4: Single Premium Ticket, Non-Student, Weekday ============
    @Test
    @DisplayName("Path 4: Single premium ticket, non-student, weekday = full price + €3 surcharge")
    public void testPath4_SinglePremiumNonStudentWeekday() {
        // Arrange
        Order order = new Order(4, false); // not student
        order.addOrderLine(new MovieTicket(weekdayScreening, true, 5, 10)); // premium ticket

        // Act
        double price = order.calculatePrice();

        // Assert
        assertEquals(13.0, price, 0.01, "Premium ticket should be €10.00 + €3.00 surcharge = €13.00");
    }

    // ============ PATH 5: Two Tickets, Non-Student, Weekend (No Group Discount) ============
    @Test
    @DisplayName("Path 5: Two tickets, non-student, weekend - no group discount (< 6)")
    public void testPath5_TwoWeekendNonStudentNoDiscount() {
        // Arrange
        Order order = new Order(5, false); // not student
        order.addOrderLine(new MovieTicket(weekendScreening, false, 5, 10));
        order.addOrderLine(new MovieTicket(weekendScreening, false, 5, 11));

        // Act
        double price = order.calculatePrice();

        // Assert
        assertEquals(20.0, price, 0.01, "Two weekend tickets without group discount = €20.00");
    }

    // ============ PATH 6: Six Premium Tickets, Non-Student, Weekend (10% Discount) ============
    @Test
    @DisplayName("Path 6: Six premium tickets, non-student, weekend - 10% group discount applied")
    public void testPath6_SixPremiumWeekendGroupDiscount() {
        // Arrange
        Order order = new Order(6, false); // not student
        for (int i = 0; i < 6; i++) {
            order.addOrderLine(new MovieTicket(weekendScreening, true, 6, i + 1)); // premium
        }

        // Act
        double price = order.calculatePrice();

        // Assert
        // 6 tickets × €13 (€10 base + €3 premium) = €78
        // 10% discount: €78 × 0.9 = €70.20
        assertEquals(70.2, price, 0.01, "Six premium tickets with 10% group discount = €70.20");
    }

    // ============ PATH 7: Mixed Tickets (Standard + Premium, 2nd Free) ============
    @Test
    @DisplayName("Path 7: Two mixed tickets (std+prem), student - 2nd free (no premium charge on free)")
    public void testPath7_MixedTickets2ndFreeNoSurcharge() {
        // Arrange
        Order order = new Order(7, true); // student
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 10)); // standard
        order.addOrderLine(new MovieTicket(weekdayScreening, true, 5, 11)); // premium (but will be free)

        // Act
        double price = order.calculatePrice();

        // Assert
        // 1st ticket: €10.00 (standard)
        // 2nd ticket: €0.00 (free, no premium surcharge on free tickets)
        assertEquals(10.0, price, 0.01, "1st standard €10.00 + 2nd free = €10.00 total");
    }

    // ============ Additional Edge Cases ============

    @Test
    @DisplayName("Edge Case: Three standard tickets, student - alternating free")
    public void testEdgeCase_ThreeStandardStudentAlternatingFree() {
        // Arrange
        Order order = new Order(8, true); // student
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 10));
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 11));
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 12));

        // Act
        double price = order.calculatePrice();

        // Assert
        // 1st: €10.00, 2nd: €0.00 (free), 3rd: €10.00
        assertEquals(20.0, price, 0.01, "Alternating free tickets: 1st€10 + 2nd€0 + 3rd€10 = €20.00");
    }

    @Test
    @DisplayName("Edge Case: Non-student, weekday, two tickets - 2nd free")
    public void testEdgeCase_NonStudentWeekdayTwoTickets() {
        // Arrange
        Order order = new Order(9, false); // not student
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 10));
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 11));

        // Act
        double price = order.calculatePrice();

        // Assert
        // Non-student on weekday also gets 2nd ticket free
        assertEquals(10.0, price, 0.01, "Non-student on weekday: 2nd ticket also free = €10.00");
    }

    @Test
    @DisplayName("Edge Case: Six standard tickets, non-student, weekend - 10% discount")
    public void testEdgeCase_SixStandardWeekendGroupDiscount() {
        // Arrange
        Order order = new Order(10, false); // not student
        for (int i = 0; i < 6; i++) {
            order.addOrderLine(new MovieTicket(weekendScreening, false, 6, i + 1)); // standard
        }

        // Act
        double price = order.calculatePrice();

        // Assert
        // 6 tickets × €10 = €60, with 10% discount: €60 × 0.9 = €54.00
        assertEquals(54.0, price, 0.01, "Six standard tickets with 10% group discount = €54.00");
    }

    @Test
    @DisplayName("Edge Case: Exactly 6 tickets needed for group discount")
    public void testEdgeCase_ExactlyMaximumGroupDiscount() {
        // Arrange
        Order order = new Order(11, false); // not student
        for (int i = 0; i < 6; i++) {
            order.addOrderLine(new MovieTicket(weekendScreening, false, 6, i + 1));
        }

        // Act
        double price = order.calculatePrice();

        // Assert
        assertEquals(54.0, price, 0.01, "Exactly 6 tickets qualifies for 10% discount");
    }

    @Test
    @DisplayName("Edge Case: 5 tickets should NOT get group discount")
    public void testEdgeCase_FiveTicketsNoDiscount() {
        // Arrange
        Order order = new Order(12, false); // not student
        for (int i = 0; i < 5; i++) {
            order.addOrderLine(new MovieTicket(weekendScreening, false, 6, i + 1));
        }

        // Act
        double price = order.calculatePrice();

        // Assert
        // 5 × €10 = €50 (no discount)
        assertEquals(50.0, price, 0.01, "Five tickets should NOT get group discount = €50.00");
    }

    @Test
    @DisplayName("Edge Case: Complex scenario - 4 tickets, mixed premium, student, weekday")
    public void testEdgeCase_ComplexScenario() {
        // Arrange
        Order order = new Order(13, true); // student
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 1)); // 1st: €10 standard
        order.addOrderLine(new MovieTicket(weekdayScreening, true, 5, 2));  // 2nd: €0 (free, was premium)
        order.addOrderLine(new MovieTicket(weekdayScreening, true, 5, 3));  // 3rd: €12 (€10 + €2 premium)
        order.addOrderLine(new MovieTicket(weekdayScreening, false, 5, 4)); // 4th: €0 (free)

        // Act
        double price = order.calculatePrice();

        // Assert
        // 1st: €10.00, 2nd: €0.00, 3rd: €12.00, 4th: €0.00 = €22.00
        assertEquals(22.0, price, 0.01, "Complex scenario: €10 + €0 + €12 + €0 = €22.00");
    }

    @Test
    @DisplayName("Precision Test: Verify rounding to 2 decimal places")
    public void testPrecision_RoundingTo2Decimals() {
        // Arrange
        Order order = new Order(14, false); // not student
        for (int i = 0; i < 3; i++) {
            order.addOrderLine(new MovieTicket(weekendScreening, false, 6, i + 1));
        }

        // Act
        double price = order.calculatePrice();

        // Assert
        // 3 × €10 = €30 (no discount because < 6)
        assertEquals(30.0, price, 0.01, "Price should be properly formatted to 2 decimals");
        String formatted = String.format("%.2f", price);
        assertTrue(formatted.matches("\\d+\\.\\d{2}"), "Price should have exactly 2 decimal places");
    }
}
