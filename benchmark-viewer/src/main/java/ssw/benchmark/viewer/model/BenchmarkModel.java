package ssw.benchmark.viewer.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BenchmarkModel {
    // DONE: Implement model
    private final ObservableList<Measurement> measurements;

    public BenchmarkModel() {
        measurements = FXCollections.observableArrayList(MeasurementData.createData());
    }

    public ObservableList<Measurement> getMeasurements() {
        return measurements;
    }

    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
    }
}