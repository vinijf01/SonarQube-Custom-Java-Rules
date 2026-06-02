import java.io.PrintStream;

class NoSystemOutRuleSample {

    void usesSystemOut() {
        System.out.println("Hello"); // Noncompliant {{Avoid using System.out/System.err for application logging. Use a logger instead.}}
        System.err.printf("Error: %s", "boom"); // Noncompliant {{Avoid using System.out/System.err for application logging. Use a logger instead.}}
        System.out.format("Value: %s", "ok"); // Noncompliant {{Avoid using System.out/System.err for application logging. Use a logger instead.}}
    }

    void usesOtherStream() {
        PrintStream out = System.out;
        out.println("Allowed by current rule implementation");
    }
}
