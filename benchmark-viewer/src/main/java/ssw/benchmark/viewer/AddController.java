package ssw.benchmark.viewer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import ssw.benchmark.viewer.model.Benchmark;
import ssw.benchmark.viewer.model.BenchmarkModel;
import ssw.benchmark.viewer.model.Measurement;


public class AddController {
    @FXML
    private GridPane addView;

    // DONE: define JavaFX components

    @FXML
    private TextField name;

    @FXML
    private TextField suite;

    @FXML
    private Label status;

    private Stage stage;
    private BenchmarkModel model;

    public void setModel(BenchmarkModel model) {
        this.model = model;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // DONE: handle actions

    @FXML
    protected void addAction() {
        if(!(suite.getText().isEmpty() || name.getText().isEmpty())) {
            final Measurement measurement = new Measurement(new Benchmark(name.getText(), suite.getText()));
            model.addMeasurement(measurement);
            stage.close();
        } else {
            status.setText("Empty Fields!");
        }
    }
}
