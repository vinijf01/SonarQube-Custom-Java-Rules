class LoggedOnlyCatchBlockRuleSample {

    private static final FakeLogger logger = new FakeLogger();
    private static final Metrics metrics = new Metrics();

    void catchOnlyLoggerCall() {
        try {
            fail();
        } catch (IllegalStateException e) { // Noncompliant {{Logging an exception without recovery or rethrowing can hide failures.}}
            logger.error("Processing failed", e);
        }
    }

    void catchOnlyPrintStackTrace() {
        try {
            fail();
        } catch (IllegalArgumentException e) { // Noncompliant {{Logging an exception without recovery or rethrowing can hide failures.}}
            e.printStackTrace();
        }
    }

    void catchAndRethrow() {
        try {
            fail();
        } catch (IllegalStateException e) {
            logger.error("Processing failed", e);
            throw e;
        }
    }

    void catchMetricsOnly() {
        try {
            fail();
        } catch (IllegalStateException e) {
            metrics.increment();
        }
    }

    void fail() {
        throw new IllegalStateException();
    }

    static class FakeLogger {
        void error(String message, Throwable error) {
        }
    }

    static class Metrics {
        void increment() {
        }
    }
}
