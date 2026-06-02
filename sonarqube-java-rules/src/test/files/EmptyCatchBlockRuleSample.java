class EmptyCatchBlockRuleSample {

    void emptyCatch() {
        try {
            risky();
        } catch (IllegalArgumentException e) { // Noncompliant {{Empty catch blocks silently discard exceptions. Handle, rethrow, or document why ignoring is safe.}}
        }
    }

    void commentOnlyCatch() {
        try {
            risky();
        } catch (RuntimeException e) { // Noncompliant {{Empty catch blocks silently discard exceptions. Handle, rethrow, or document why ignoring is safe.}}
            // documented elsewhere
        }
    }

    void handledCatch() {
        try {
            risky();
        } catch (IllegalStateException e) {
            throw e;
        }
    }

    void risky() {
    }
}
