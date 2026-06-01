# TEST CASE DOCUMENTATION - Path Testing Analysis
## Cinema Booking System - calculatePrice() Method

**Subject:** Softwaredesign & -kwaliteit - Casusopdracht Bioscoop Deel 2  
**Date:** June 2024  
**Approach:** Path Testing (Control Flow Graph Analysis)

---

## 1. INTRODUCTION

This document describes the systematic approach used to design test cases for the `calculatePrice()` method in the `Order` class using the **path testing methodology**. Path testing is a white-box testing technique that identifies independent execution paths through a program and creates test cases to exercise each path.

---

## 2. CONTROL FLOW ANALYSIS

### 2.1 Method Overview

The `calculatePrice()` method calculates the total price of an order based on:
- Whether the order is for students (students get every 2nd ticket free)
- Whether the screening is on a weekday or weekend
- Whether tickets are standard or premium
- Whether the order has 6+ tickets on weekends (qualifies for 10% group discount)

### 2.2 Control Flow Graph (CFG)

The control flow graph visualizes all possible execution paths:

```
Entry (Start)
    ↓
[1] Is orderLines empty?
    ├─ YES → [2] Return 0.0
    └─ NO → [3] Initialize totalPrice = 0.0
    
[4] Check if weekend
    ↓
[5] Loop through tickets (for i in orderLines)
    ├─ For each ticket:
    │  ├─ [6] Get base price
    │  ├─ [7] Is student OR non-weekend?
    │  │  ├─ YES → [8] Is ticket #2, #4, #6... (i+1 % 2 == 0)?
    │  │  │        ├─ YES → [9] Set ticketPrice = 0
    │  │  │        └─ NO → [10] Use basePrice
    │  │  └─ NO → [10] Use basePrice
    │  │
    │  ├─ [11] Is premium AND price > 0?
    │  │  ├─ YES → [12] Add surcharge (€2 student / €3 non-student)
    │  │  └─ NO → [13] Keep ticketPrice as is
    │  │
    │  └─ [14] Add ticketPrice to totalPrice
    │
    └─ Loop completes
    
[15] Is non-student AND weekend AND ticketCount >= 6?
    ├─ YES → [16] Apply 10% discount (totalPrice *= 0.9)
    └─ NO → Continue
    
[17] Round to 2 decimal places
    ↓
[18] Return totalPrice
    ↓
Exit (End)
```

### 2.3 Complexity Metrics

**Cyclomatic Complexity (M):**
- Formula: M = E - N + 2P
- Where: E = edges, N = nodes, P = connected components
- Calculation: M = 18 - 15 + 2(1) = **5**
- Interpretation: Moderate complexity, manageable and testable

**Node Count:** 15 decision/processing nodes  
**Edge Count:** 18 possible transitions  
**Predicate Nodes:** 5 (decision points: 1, 7, 8, 11, 15)

---

## 3. PATH IDENTIFICATION

Using **independent path analysis**, we identify 7 fundamental paths that cover all decision branches:

### Path 1: Empty Order
```
Conditions: orderLines.isEmpty() == true
Nodes: 1 → 2 → Exit
Result: Returns 0.0 immediately
```
**Purpose:** Test boundary condition for empty collection

### Path 2: Single Standard Ticket, Non-Student, Weekday
```
Conditions: 
  - orderLines.size() = 1
  - isStudentOrder = false
  - isWeekend = false (weekday)
  - isPremium = false
  - i+1 % 2 != 0 (odd ticket number)
Nodes: 1 → 3 → 4 → 5 → 6 → 7 → 10 → 11 → 13 → 14 → 15 → 17 → 18 → Exit
Result: Full price (€10.00)
```
**Purpose:** Base case - full price charged with no discounts

### Path 3: Two Standard Tickets, Student Order (Every 2nd Free)
```
Conditions:
  - orderLines.size() = 2
  - isStudentOrder = true
  - isPremium = false (both standard)
  - Loop iteration 2: i+1 % 2 == 0 (even position)
Nodes: 1 → 3 → 4 → 5 → [loop 1: 6→7→8→9→14] → [loop 2: 6→7→8→9→14] → 15 → 17 → 18 → Exit
Result: €10.00 (1st full price, 2nd free)
```
**Purpose:** Test student discount (every 2nd ticket free)

### Path 4: Single Premium Ticket, Non-Student, Weekday
```
Conditions:
  - orderLines.size() = 1
  - isStudentOrder = false
  - isWeekend = false (weekday)
  - isPremium = true
  - ticketPrice > 0 (not a free ticket)
Nodes: 1 → 3 → 4 → 5 → 6 → 7 → 10 → 11 → 12 → 14 → 15 → 17 → 18 → Exit
Result: Base price + €3 surcharge (€13.00)
```
**Purpose:** Test premium ticket surcharge for non-students

### Path 5: Two Tickets, Non-Student, Weekend (No Group Discount)
```
Conditions:
  - orderLines.size() = 2
  - isStudentOrder = false
  - isWeekend = true
  - isPremium = false
  - ticketCount < 6 (no group discount)
Nodes: 1 → 3 → 4 → 5 → [loop iterations] → 15 → [NO to discount] → 17 → 18 → Exit
Result: Full price × 2 (€20.00)
```
**Purpose:** Test weekend pricing without group discount (< 6 tickets)

### Path 6: Six Premium Tickets, Non-Student, Weekend (10% Group Discount)
```
Conditions:
  - orderLines.size() = 6
  - isStudentOrder = false
  - isWeekend = true
  - isPremium = true
  - ticketCount >= 6
Nodes: 1 → 3 → 4 → 5 → [6 loop iterations] → 15 → [YES to discount] → 16 → 17 → 18 → Exit
Result: (6 × €13) × 0.9 = €70.20
```
**Purpose:** Test 10% group discount for large orders on weekends

### Path 7: Mixed Tickets (Standard + Premium, 2nd Free, No Surcharge on Free)
```
Conditions:
  - orderLines.size() = 2
  - isStudentOrder = true
  - Ticket 1: standard (basePrice)
  - Ticket 2: premium (but becomes free, so no surcharge added)
  - i+1 % 2 == 0 for 2nd ticket
Nodes: 1 → 3 → 4 → 5 → [iteration 1: normal] → [iteration 2: 8→9→11→13] → 15 → 17 → 18 → Exit
Result: €10.00 (premium surcharge waived for free tickets)
```
**Purpose:** Test edge case - premium surcharge not applied to free tickets

---

## 4. TEST CASE DESIGN

### Test Case Matrix

| # | Test Case | Order Type | Weekday? | Tickets | Premium | Qty | Expected Price | Path |
|---|-----------|------------|----------|---------|---------|-----|-----------------|------|
| T1 | Empty Order | - | - | 0 | - | 0 | €0.00 | P1 |
| T2 | Single Std Non-Stu WD | Non-Stu | Yes | Std | No | 1 | €10.00 | P2 |
| T3 | Two Std Stu WD (2nd Free) | Student | Yes | Std | No | 2 | €10.00 | P3 |
| T4 | Single Prem Non-Stu WD | Non-Stu | Yes | Prem | Yes | 1 | €13.00 | P4 |
| T5 | Two Std Non-Stu WE | Non-Stu | No | Std | No | 2 | €20.00 | P5 |
| T6 | Six Prem Non-Stu WE (10%) | Non-Stu | No | Prem | Yes | 6 | €70.20 | P6 |
| T7 | Mixed Std+Prem Stu WD | Student | Yes | Mix | Yes | 2 | €10.00 | P7 |
| T8 | Three Std Stu (Alternating) | Student | Yes | Std | No | 3 | €20.00 | P3+ |
| T9 | Non-Stu WD Two (2nd Free) | Non-Stu | Yes | Std | No | 2 | €10.00 | P3 |
| T10 | Six Std Non-Stu WE (10%) | Non-Stu | No | Std | No | 6 | €54.00 | P6 |
| T11 | Five Std Non-Stu WE (No %) | Non-Stu | No | Std | No | 5 | €50.00 | P5 |
| T12 | Complex Mixed 4 Tix Stu | Student | Yes | Mix | Yes | 4 | €22.00 | P7+ |

### Test Execution Strategy

**Test Coverage Goals:**
- Line Coverage: > 95%
- Branch Coverage: > 90%
- Path Coverage: 100% of 7 independent paths
- Decision Coverage: 100% of all branch conditions

**Test Order:**
1. First: Test boundary cases (empty order)
2. Then: Test each decision independently
3. Finally: Test complex combinations

---

## 5. IMPLEMENTATION DETAILS

### 5.1 Test Framework

- **Framework:** JUnit 5 (Jupiter)
- **Assertion Library:** JUnit Assertions
- **Build Tool:** Maven 3.8+
- **Java Version:** JDK 17+

### 5.2 Test Naming Convention

Tests follow the pattern: `test[PathNumber]_[Scenario]`

Example: `testPath1_EmptyOrder()` → Tests Path 1 scenario

### 5.3 Test Structure (AAA Pattern)

Each test follows the **Arrange-Act-Assert** pattern:

```java
@Test
public void testPath3_TwoStandardStudent2ndFree() {
    // ARRANGE: Set up test data
    Order order = new Order(3, true);
    order.addOrderLine(new MovieTicket(screening1, false, 5, 10));
    order.addOrderLine(new MovieTicket(screening1, false, 5, 11));
    
    // ACT: Execute the method under test
    double price = order.calculatePrice();
    
    // ASSERT: Verify results
    assertEquals(10.0, price, 0.01);
}
```

---

## 6. COVERAGE METRICS

### 6.1 Code Coverage Report

**Expected Results from JaCoCo:**
- Line Coverage: 95-100%
  - All decision branches executed
  - All loop iterations covered
  - Premium surcharge calculation covered
  - Discount application covered

- Branch Coverage: 90-100%
  - All true/false outcomes tested
  - All conditional paths exercised

- Cyclomatic Complexity: 5 (manageable)

### 6.2 SonarCloud Integration

**Key Metrics:**
- Code Smell Index
- Duplicated Code %
- Technical Debt Ratio
- Security Hotspots
- Test Coverage %

---

## 7. CI/CD PIPELINE

### 7.1 Automated Testing

GitHub Actions workflow (`tests.yml`) automatically:
1. Compiles code on push/PR
2. Runs all unit tests
3. Generates JaCoCo coverage reports
4. Uploads coverage to Codecov
5. Publishes test reports

### 7.2 Quality Gates

The pipeline ensures:
- All tests pass before merge
- Minimum coverage thresholds maintained
- No test regressions introduced

---

## 8. RECOMMENDATIONS

1. **Regular Review:** Update test cases when business rules change
2. **Performance Testing:** Add performance tests for large orders (100+ tickets)
3. **Integration Testing:** Test integration with database/file system
4. **Property-Based Testing:** Consider using QuickCheck for randomized testing
5. **Mutation Testing:** Use PIT to verify test quality

---

## 9. CONCLUSION

Using path testing methodology with control flow graph analysis, we identified 7 independent execution paths through the `calculatePrice()` method. Each path is covered by comprehensive test cases that verify:

- Correctness of discount calculations
- Proper handling of premium surcharges
- Weekend vs. weekday pricing logic
- Student vs. non-student pricing
- Group discount thresholds
- Edge cases and boundary conditions

This systematic approach ensures high code quality, maintainability, and confidence in the pricing logic.

---

**Document Created:** June 2024  
**Test Class:** `OrderPriceCalculationTest.java`  
**Total Test Cases:** 12 + Edge Cases  
**Estimated Coverage:** 95%+
