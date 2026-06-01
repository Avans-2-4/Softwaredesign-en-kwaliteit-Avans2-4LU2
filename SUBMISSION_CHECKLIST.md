# SUBMISSION CHECKLIST - Part 2

## Assignment Requirements

### 1. Test Cases ✅
- [x] Determine test cases using path testing method
- [x] 7 independent paths identified
- [x] 12+ core test cases implemented
- [x] Edge cases and boundary conditions covered
- [x] All paths have minimum 1 test case

**File:** `OrderPriceCalculationTest.java`

### 2. Control Flow Graph ✅
- [x] Graaf/flowchart created
- [x] All decision nodes identified
- [x] All paths shown
- [x] Mermaid diagram for visualization

**File:** `PATH_TESTING_ANALYSIS.md`

### 3. Documentation ✅
- [x] Explanation of path testing methodology
- [x] Control flow graph analysis
- [x] Test case matrix with scenarios
- [x] Expected vs. actual calculations
- [x] Cyclomatic complexity calculation

**Files:** 
- `PATH_TESTING_ANALYSIS.md` (1000+ words)
- `TEST_DOCUMENTATION.md` (1500+ words)

### 4. Repository ✅
- [x] Code committed to GitHub
- [x] Professional structure
- [x] Clear organization
- [x] Proper documentation

**Repository:** `https://github.com/Avans-2-4/Softwaredesign-en-kwaliteit-Avans2-4LU2`

### 5. CI/CD Pipeline ✅
- [x] GitHub Actions workflow created
- [x] Automatic test execution on push/PR
- [x] Maven build configured
- [x] Test reports generated
- [x] Coverage reports integrated

**File:** `.github/workflows/tests.yml`

### 6. Code Quality Integration ✅
- [x] SonarCloud setup guide provided
- [x] SonarQube configuration file created
- [x] JaCoCo coverage plugin configured
- [x] Test coverage tracking enabled
- [x] Cyclomatic complexity analysis ready

**Files:**
- `SONARCLOUD_SETUP.md` (setup guide)
- `sonar-project.properties` (configuration)

---

## Implementation Summary

### Test Cases: 12+

| # | Path | Test Case | Status |
|---|------|-----------|--------|
| 1 | P1 | Empty order | ✅ |
| 2 | P2 | Single standard (non-stu, WD) | ✅ |
| 3 | P3 | Two standard (stu, 2nd free) | ✅ |
| 4 | P4 | Single premium (non-stu) | ✅ |
| 5 | P5 | Two weekend (non-stu, no%) | ✅ |
| 6 | P6 | Six premium (non-stu, 10%) | ✅ |
| 7 | P7 | Mixed (2nd free, no premium) | ✅ |
| 8 | E1 | Three standard (alternating) | ✅ |
| 9 | E2 | Non-stu WD (2nd free) | ✅ |
| 10 | E3 | Six standard WE (10%) | ✅ |
| 11 | E4 | Five tickets (no discount) | ✅ |
| 12 | E5 | Complex mixed 4 tickets | ✅ |
| 13 | E6 | Precision/rounding test | ✅ |

### Metrics

- **Paths Identified:** 7/7 ✅
- **Cyclomatic Complexity:** 5 (moderate)
- **Test Coverage Target:** 95%+ 🎯
- **Line Coverage Target:** > 95% 🎯
- **Branch Coverage Target:** > 90% 🎯

### Files Created/Modified

#### Source Code (6 classes)
- [x] Movie.java
- [x] MovieScreening.java
- [x] MovieTicket.java
- [x] Order.java (with calculatePrice + export)
- [x] TicketExportFormat.java
- [x] Main.java

#### Test Code (1 class)
- [x] OrderPriceCalculationTest.java (12+ tests)

#### Documentation (4 files)
- [x] PATH_TESTING_ANALYSIS.md (control flow graph + paths)
- [x] TEST_DOCUMENTATION.md (detailed methodology)
- [x] SONARCLOUD_SETUP.md (quality setup guide)
- [x] TEST_DOCUMENTATION.md (updated)

#### Configuration (4 files)
- [x] pom.xml (Maven with JUnit 5, JaCoCo)
- [x] .github/workflows/tests.yml (CI/CD)
- [x] sonar-project.properties (SonarQube config)
- [x] QUICK_START.md (quick reference)

#### Updated
- [x] README.md (comprehensive guide)

---

## Quality Metrics

### Code Coverage
- [x] Line coverage tracking (JaCoCo)
- [x] Branch coverage tracking
- [x] Path coverage: 7/7 paths ✅
- [x] Expected coverage: 95%+

### Test Results
- [x] All tests automated (JUnit 5)
- [x] AAA pattern used (Arrange-Act-Assert)
- [x] Descriptive test names
- [x] Clear assertions with delta tolerance

### Documentation Quality
- [x] Path methodology explained
- [x] Control flow graph included (Mermaid)
- [x] Test case matrix provided
- [x] Expected results documented
- [x] Cyclomatic complexity calculated

### Process Quality
- [x] Version control (GitHub)
- [x] Automated testing (GitHub Actions)
- [x] Coverage tracking (JaCoCo)
- [x] Quality gates (SonarCloud ready)

---

## Running the Submission

### Execute All Tests
```bash
cd index
mvn clean test
```
**Expected:** All 12+ tests pass ✅

### Generate Coverage Report
```bash
mvn jacoco:report
open target/site/jacoco/index.html
```
**Expected:** 95%+ coverage ✅

### View Documentation
1. Open `PATH_TESTING_ANALYSIS.md` - See Mermaid diagram and paths
2. Open `TEST_DOCUMENTATION.md` - See detailed methodology
3. Open `QUICK_START.md` - See quick reference

---

## Validation Checklist

- [x] Code compiles without errors
- [x] All 12+ tests pass
- [x] Coverage report generates
- [x] CI/CD pipeline configured
- [x] Documentation complete
- [x] Mermaid diagram clear
- [x] Test cases cover all 7 paths
- [x] README updated
- [x] QUICK_START guide provided
- [x] SonarCloud setup documented

---

## Group Work Attribution

This submission was prepared as part of **groupwork (4 students)**:
- [ ] Student 1: _________________
- [ ] Student 2: _________________
- [ ] Student 3: _________________
- [ ] Student 4: _________________

---

## Final Status

✅ **READY FOR SUBMISSION**

All requirements met:
- Path testing methodology implemented
- Control flow graph documented
- 12+ test cases covering 7 paths
- CI/CD pipeline configured
- Code quality integration ready
- Documentation complete
- Test coverage 95%+ achievable

---

**Last Updated:** June 2024  
**Assignment:** Part 2 - Testing & Quality  
**Status:** Complete ✅
