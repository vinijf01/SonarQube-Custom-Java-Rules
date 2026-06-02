import java.io.IOException;

class GenericCatchExceptionRuleSample {

    void catchesException() {
        try {
            throw new IOException();
        } catch (Exception e) { // Noncompliant {{Catch a more specific exception type so failures are handled intentionally.}}
            recover();
        }
    }

    void catchesThrowable() {
        try {
            throw new IOException();
        } catch (Throwable e) { // Noncompliant {{Catch a more specific exception type so failures are handled intentionally.}}
            recover();
        }
    }

    void catchesSpecificException() {
        try {
            throw new IOException();
        } catch (IOException e) {
            recover();
        }
    }

    void recover() {
    }
}
