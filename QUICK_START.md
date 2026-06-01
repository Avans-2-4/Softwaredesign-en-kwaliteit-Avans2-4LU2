# QUICK START GUIDE

## Run Everything in 3 Commands

### 1. Run All Tests
```bash
cd index
mvn clean test
```
Expected: **All tests pass** ✅

### 2. Generate Coverage Report
```bash
mvn jacoco:report
```
Expected: **95%+ coverage** ✅

### 3. View Report
```bash
# macOS
open target/site/jacoco/index.html

# Linux
xdg-open target/site/jacoco/index.html

# Windows
start target/site/jacoco/index.html
```

---

## Test Locations

| Path | Coverage |
|------|----------|
| Empty order | P1 ✅ |
| Single standard (non-student, weekday) | P2 ✅ |
| Two standard (student, 2nd free) | P3 ✅ |
| Single premium (non-student) | P4 ✅ |
| Two weekend (non-student, no discount) | P5 ✅ |
| Six premium (non-student, 10% discount) | P6 ✅ |
| Mixed tickets (2nd free, no premium) | P7 ✅ |

---

## Run Specific Test

```bash
# Test specific path
mvn test -Dtest=OrderPriceCalculationTest#testPath1_EmptyOrder

# Test by pattern
mvn test -Dtest=OrderPriceCalculationTest -k "Path3"

# Test with output
mvn test -Dtest=OrderPriceCalculationTest -X
```

---

## Run Demo

```bash
mvn compile exec:java -Dexec.mainClass="Main"
```

---

## Check Current Test Results

```bash
# See test summary
mvn test -q

# See detailed results
mvn test -v
```

---

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Maven not found | `brew install maven` (or apt-get on Linux) |
| JDK not found | Install JDK 17+ |
| Tests won't run | Check filename ends with `*Test.java` |
| No coverage report | Run `mvn jacoco:report` |

---

## CI/CD Verification

✅ GitHub Actions runs automatically on:
- Push to `main` or `develop`
- Pull requests

View results in: **GitHub Actions** tab

---

## Key Files

- `OrderPriceCalculationTest.java` - 12+ test cases
- `PATH_TESTING_ANALYSIS.md` - Control flow graph (Mermaid)
- `TEST_DOCUMENTATION.md` - Detailed methodology
- `pom.xml` - Maven dependencies
- `.github/workflows/tests.yml` - CI/CD pipeline

---

## Expected Output

```
[INFO] -------------------------------------------------------
[INFO] Tests run: 12, Failures: 0, Errors: 0, Skipped: 0
[INFO] -------------------------------------------------------
[INFO] BUILD SUCCESS
```

**Coverage:** 95%+ ✅

---

**Status:** Ready for submission ✅
