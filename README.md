# Cinema Booking System - Complete Guide

## Project Overview

This is part 2 of the "Casusopdracht Bioscoop" (Cinema Case Study) from the Software Design & Quality course at Avans University of Applied Sciences.

**Current Status:** Part 2 - Comprehensive Testing with Path Testing Methodology

---

## Quick Start

### Prerequisites
- JDK 17+
- Maven 3.8+

### Run Tests

```bash
cd index
mvn clean test
```

### View Coverage Report

```bash
mvn jacoco:report
open target/site/jacoco/index.html
```

---

## Key Features

✅ **Part 1 - Core Implementation:**
- Movie and MovieScreening domain classes
- MovieTicket with seating and premium status
- Order with complex pricing algorithm
- Export to plain text and JSON

✅ **Part 2 - Testing & Quality:**
- 7 independent paths identified using control flow graph analysis
- 12+ comprehensive JUnit 5 test cases
- Path testing methodology with Mermaid diagram
- GitHub Actions CI/CD pipeline
- JaCoCo code coverage reporting (95%+ target)
- SonarCloud integration ready

---

## Documentation

| Document | Purpose |
|----------|---------|
| [PATH_TESTING_ANALYSIS.md](./index/PATH_TESTING_ANALYSIS.md) | Control flow graph and path identification |
| [TEST_DOCUMENTATION.md](./TEST_DOCUMENTATION.md) | Detailed test methodology and test case matrix |
| [SONARCLOUD_SETUP.md](./SONARCLOUD_SETUP.md) | SonarCloud integration guide |

---

## Project Structure

```
index/
├── src/
│   ├── *.java                    # Source classes
│   └── OrderPriceCalculationTest.java # 12+ test cases
├── pom.xml                       # Maven config
└── target/                       # Build output

.github/workflows/tests.yml       # CI/CD pipeline
```

---

## Pricing Logic

Complex algorithm supporting:
- ✅ Student discount (every 2nd ticket free)
- ✅ Weekday discount (every 2nd ticket free for all)
- ✅ Premium surcharges (+€2 students, +€3 others)
- ✅ Group discount (10% off for 6+ weekend tickets)
- ✅ Proper combination of all discounts

---

## Test Coverage

| Metric | Target | Status |
|--------|--------|--------|
| Line Coverage | > 95% | 🎯 |
| Branch Coverage | > 90% | 🎯 |
| Path Coverage | 100% | ✅ 7/7 |
| Cyclomatic Complexity | 5 | ✅ |

---

For complete documentation, see [TEST_DOCUMENTATION.md](./TEST_DOCUMENTATION.md)
