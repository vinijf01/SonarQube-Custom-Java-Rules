# SonarQube Custom Java Rules Plugin

Custom SonarQube plugin for Java that demonstrates how to build, register, test, and validate AST-based static analysis rules with SonarQube Server and IntelliJ.

This repository is positioned as a focused portfolio project: small in scope, but reproducible, tested, and easy to review.

## What This Repo Contains

This repo has two separate Maven modules:

- `sonarqube-java-rules/`: the actual SonarQube plugin packaged as `sonar-plugin`
- `sonarqube-rule-test/`: a sample Java project used as a manual analysis target in SonarQube and IntelliJ

Important:

- the repository root is **not** a Maven project
- run Maven commands inside one of the two module directories

## Current Rule Set

| Rule Key | Name | Description | Severity |
| --- | --- | --- | --- |
| `empty-catch-block` | Empty Catch Block | Detects `catch` blocks that silently ignore exceptions | MAJOR |
| `generic-catch-exception` | Generic Catch Exception | Detects `catch (Exception/RuntimeException/Throwable)` usage | MAJOR |
| `hardcoded-credential` | Hard-coded Credential | Detects hard-coded secrets in declarations and simple assignments | CRITICAL |
| `logged-only-catch` | Logged-only Catch Block | Detects `catch` blocks that only log or call `printStackTrace()` | MAJOR |
| `no-system-out` | No System.out / System.err | Detects `System.out` / `System.err` printing methods | MINOR |

## Tech Stack

- Java 17
- Maven
- SonarQube Server
- SonarQube Plugin API
- SonarSource Java analyzer API
- Docker Compose
- SonarQube for IDE (IntelliJ)
- GitHub Actions

## Architecture

```text
Java source code
    -> SonarQube Java analyzer
    -> Custom plugin extensions
    -> Rule visitors
    -> Issues in SonarQube Web UI and IntelliJ
```

Plugin wiring is intentionally simple:

- `CustomRulesPlugin`: plugin entrypoint
- `JavaRulesDefinition`: rule metadata and repository definition
- `JavaCheckRegistrar`: registration of Java check classes
- `rules/*`: one rule per class, each extending `IssuableSubscriptionVisitor`

## Java Baseline

Both modules compile with **Java 17**:

- `sonarqube-java-rules/`
- `sonarqube-rule-test/`

This keeps local builds, CI, and IntelliJ analysis on a single JDK baseline.

## Local Prerequisites

- Java 17
- Maven
- Docker and Docker Compose
- IntelliJ IDEA with **SonarQube for IDE** plugin for IDE validation

## Build And Validate

### 1. Run automated rule tests

```bash
cd sonarqube-java-rules && mvn -q test
```

### 2. Package the plugin JAR

```bash
cd sonarqube-java-rules && mvn -q -DskipTests package
```

Expected artifact:

```text
sonarqube-java-rules/target/sonarqube-java-rules-1.0-SNAPSHOT.jar
```

### 3. Copy the plugin into SonarQube

```bash
cp sonarqube-java-rules/target/sonarqube-java-rules-1.0-SNAPSHOT.jar plugins/
```

### 4. Start SonarQube locally

```bash
docker-compose up -d
```

If you replaced the plugin JAR, restart the container so SonarQube reloads the plugin:

```bash
docker restart sonarqube
```

### 5. Activate the rules in SonarQube

1. Open SonarQube Web UI at `http://localhost:9000`
2. Go to **Rules**
3. Filter by repository **Custom Java Rules**
4. Activate the rules in a Quality Profile
5. Assign that Quality Profile to the sample project

### 6. Compile the sample analysis target

```bash
cd sonarqube-rule-test && mvn -q test
```

### 7. Analyze the sample project in SonarQube

```bash
cd sonarqube-rule-test && mvn sonar:sonar
```

## IntelliJ Validation

To validate the custom rules in IntelliJ, use **SonarQube for IDE** in **Connected Mode**.

1. Install or update the **SonarQube for IDE** plugin.
2. Open the `sonarqube-rule-test/` folder in IntelliJ.
3. If IntelliJ detects `.sonarlint/connectedMode.json`, choose **Use configuration**.
4. If needed, create a connection in `Settings -> Tools -> SonarQube for IDE` to `http://localhost:9000` using a **user token**.
5. In `Settings -> Tools -> SonarQube for IDE -> Project Settings`, bind the project to project key `sonarqube-rule-test`.
6. Build the project with `Build -> Build Project` so Java analysis has up-to-date bytecode.
7. Right-click `src/main/java` or an individual file and run **Analyze with SonarQube for IDE**.
8. If issues still do not appear, restart IntelliJ and check the **SonarQube for IDE -> Log** tab.

Quick checklist:

- connected mode is active
- project key is `sonarqube-rule-test`
- the server quality profile contains the custom rules
- the IntelliJ project has been built

## Automated Testing Strategy

Automated rule tests live in `sonarqube-java-rules/`.

- test classes: `src/test/java/...`
- Java fixtures: `src/test/files/...`
- verifier: SonarSource `java-checks-testkit` with `CheckVerifier`

Each rule is covered with focused samples for compliant and noncompliant behavior.

The sample module `sonarqube-rule-test/` is intentionally separate and is used only for end-to-end manual validation in SonarQube and IntelliJ.

## CI

GitHub Actions workflow: `.github/workflows/ci.yml`

CI currently verifies:

1. plugin rule tests
2. plugin packaging
3. sample project compilation

It also uploads the built plugin JAR as a workflow artifact.

## Repository Layout

```text
.
├── .github/workflows/ci.yml
├── docker-compose.yml
├── plugins/
├── sonarqube-java-rules/
│   ├── src/main/java/com/vini/sonarqube/
│   └── src/test/
└── sonarqube-rule-test/
    └── src/main/java/com/vini/sonarqube/
```

## Current Limitations

- rule metadata is still defined directly in Java, not externalized under `src/main/resources`
- local deployment is still manual: build -> copy JAR -> restart SonarQube
- CI does not yet run a Docker-based end-to-end SonarQube analysis
- some rules intentionally use straightforward syntax-based detection rather than deeper semantic or control-flow analysis

## Evidence

- Custom rules listed in SonarQube: 
    ![CustomJavaRules.jpg](CustomJavaRules.jpg)
- Rule details in SonarQube: 
    ![CustomJavaRules-1.jpg](CustomJavaRules-1.jpg)
- Issues detected in IntelliJ: 
    ![CustomJavaRules-2.jpg](CustomJavaRules-2.jpg)
- Issues detected in SonarQube Web UI: 
    ![CustomJavaRules-3.jpg](CustomJavaRules-3.jpg)

## License

This project is maintained as a learning and portfolio project.
