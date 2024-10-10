package ssw.benchmark.viewer.model;

public class Benchmark {
    private final String name;
    private final String suite;

    public Benchmark(String name, String suite) {
        this.name = name;
        this.suite = suite;
    }

    public String getName() {
        return name;
    }

    public String getSuite() {
        return suite;
    }
}
