# SonarCloud Configuration

## Setup Instructions

### 1. Connect Repository to SonarCloud

1. Go to [https://sonarcloud.io](https://sonarcloud.io)
2. Sign up with GitHub account
3. Import your repository
4. Generate token (in user account settings)

### 2. Add to GitHub Secrets

Add these secrets to your repository settings:

```
SONAR_TOKEN=<your_sonarcloud_token>
SONAR_HOST_URL=https://sonarcloud.io
```

### 3. Update GitHub Actions Workflow

Add SonarCloud step to `.github/workflows/tests.yml`:

```yaml
- name: SonarCloud Scan
  uses: SonarSource/sonarcloud-github-action@master
  env:
    GITHUB_TOKEN: ${{ secrets.GITHUB_TOKEN }}
    SONAR_TOKEN: ${{ secrets.SONAR_TOKEN }}
  with:
    args: >
      -Dsonar.projectKey=Avans-2-4_Softwaredesign-en-kwaliteit-Avans2-4LU2
      -Dsonar.organization=avans-2-4
```

### 4. Local SonarQube Analysis

Run locally with Maven:

```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=cinema_booking_system \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=<SONAR_TOKEN>
```

## Key Metrics to Monitor

| Metric | Target | Status |
|--------|--------|--------|
| Code Coverage | > 80% | 🎯 |
| Cyclomatic Complexity | < 10 | ✓ 5 |
| Code Smells | 0-5 | 🔍 |
| Security Hotspots | 0 | ✓ |
| Duplicated Lines | < 3% | 🔍 |
| Test Coverage | > 90% | 🎯 |

## Quality Gate Configuration

In SonarCloud project settings:

```
- New code: Coverage > 80%
- New code: Duplicated Lines Density < 3%
- Overall: Technical Debt Ratio < 5%
- Overall: Maintainability Rating A
- Overall: Reliability Rating A
- Overall: Security Rating A
```

## View Reports

After running analysis:
- **SonarCloud Dashboard:** `https://sonarcloud.io/dashboard?id=...`
- **Local Report:** `target/site/sonar/index.html`
- **JaCoCo Report:** `target/site/jacoco/index.html`
