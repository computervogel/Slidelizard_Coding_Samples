package ssw.benchmark.viewer.model;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Measurement {
    public static final int ITERATIONS = 6;
    public static final String THROUGHPUT = "op/s";
    public static final String TIME = "s";

    private final Benchmark benchmark;
    private final ObservableList<DoubleProperty> iterations;
    private final BooleanProperty throughput = new SimpleBooleanProperty(false);
    private final DoubleBinding mean;

    public Measurement(Benchmark benchmark) {
        this.benchmark = benchmark;
        this.iterations = FXCollections.observableArrayList();
        for(int i = 0; i < ITERATIONS; i++) {
            SimpleDoubleProperty p = new SimpleDoubleProperty();
            p.set(0.0);
            iterations.add(p);
        }
        this.mean = new DoubleBinding() {
            {
                super.bind(throughput);
                for(int i = 0; i < ITERATIONS; i++) {
                    super.bind(iterations.get(i));
                }
            }
            @Override
            protected double computeValue() {
                if(throughput.getValue()){
                    double sum = 0;
                    for (DoubleProperty iteration : iterations) {
                        if(iteration.get() != 0) {
                            sum += 1 / iteration.get();
                        }
                    }
                    return sum / iterations.size();
                } else {
                    double sum = 0;
                    for (DoubleProperty iteration : iterations) {
                        sum += iteration.get();
                    }
                    return sum / iterations.size();
                }
            }
        };
    }

    public Benchmark getBenchmark() {
        return benchmark;
    }

    public void setIteration(int index, double value) {
        iterations.get(index).set(value);
    }

    public ObservableList<DoubleProperty> getIterations() {
        return iterations;
    }

    public void setThroughput(boolean value) {
        throughput.set(value);
    }

    public BooleanProperty getThroughput() {
        return throughput;
    }

    public String getName() {
        return benchmark.getName();
    }

    public String getSuite() {
        return benchmark.getSuite();
    }

    public DoubleBinding getMean(){
        return mean;
    }
}
