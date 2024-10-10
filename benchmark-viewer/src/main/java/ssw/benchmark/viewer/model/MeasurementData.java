package ssw.benchmark.viewer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MeasurementData {
    private MeasurementData() {
    }

    public static List<Measurement> createData() {
        final Random rng = new Random(123);

        final List<Measurement> data = new ArrayList<>();

        final var m1 = new Measurement(new Benchmark("typescript", "JetStream 2"));
        for (int i = 0; i < Measurement.ITERATIONS; i++) {
            m1.setIteration(i, rng.nextDouble(121, 150));
        }
        data.add(m1);

        final var m2 = new Measurement(new Benchmark("stockfish", "C++ Suite"));
        for (int i = 0; i < Measurement.ITERATIONS; i++) {
            m2.setIteration(i, rng.nextDouble(2153, 3158));
        }
        data.add(m2);

        final var m3 = new Measurement(new Benchmark("reactors", "Renaissance"));
        for (int i = 0; i < Measurement.ITERATIONS; i++) {
            m3.setIteration(i, rng.nextDouble(0.02, 0.5));
        }
        m3.setThroughput(true);
        data.add(m3);

        final var m4 = new Measurement(new Benchmark("gcc", "SPEC 2017 Integer"));
        for (int i = 0; i < Measurement.ITERATIONS; i++) {
            m4.setIteration(i, rng.nextDouble(0.01, 0.8));
        }
        m4.setThroughput(true);
        data.add(m4);

        return data;
    }
}
