class HardCodeCredentialRuleSample {

    private String password = "admin123"; // Noncompliant {{Hard-coded credential detected. Load secrets from configuration or a secret manager instead.}}
    private String clientSecret;
    private String username = "service-user";

    HardCodeCredentialRuleSample() {
        apiToken = "ABC-SECRET-KEY"; // Noncompliant {{Hard-coded credential detected. Load secrets from configuration or a secret manager instead.}}
        this.clientSecret = "client-secret"; // Noncompliant {{Hard-coded credential detected. Load secrets from configuration or a secret manager instead.}}
        password = System.getenv("APP_PASSWORD");
    }

    private String apiToken;
}
