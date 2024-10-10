package ssw.benchmark.viewer;

import javafx.beans.binding.StringBinding;
import javafx.util.StringConverter;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ssw.benchmark.viewer.model.BenchmarkModel;
import ssw.benchmark.viewer.model.Measurement;

import java.io.IOException;

public class BenchmarkController {
    // DONE: define JavaFX components

    @FXML
    private TableView<Measurement> table;

    @FXML
    private TableColumn<Measurement, String> name;

    @FXML
    private TableColumn<Measurement, String> suite;

    @FXML
    TableColumn<Measurement, Double> firstIteration;

    @FXML
    TableColumn<Measurement, Double> secondIteration;

    @FXML
    TableColumn<Measurement, Double> thirdIteration;

    @FXML
    TableColumn<Measurement, Double> fourthIteration;

    @FXML
    TableColumn<Measurement, Double> fifthIteration;

    @FXML
    TableColumn<Measurement, Double> sixthIteration;

    private final BenchmarkModel model;
    private Stage primaryStage;

    public BenchmarkController() {
        this.model = new BenchmarkModel();
        this.primaryStage = null;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    protected void initialize() {
        // DONE: initialize components
        table.setItems(model.getMeasurements());
        table.setEditable(true);
        table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        setUpIterations(firstIteration, 0);
        setUpIterations(secondIteration, 1);
        setUpIterations(thirdIteration, 2);
        setUpIterations(fourthIteration, 3);
        setUpIterations(fifthIteration, 4);
        setUpIterations(sixthIteration, 5);

        TableColumn<Measurement, Boolean> throughput = new TableColumn<>("Throughput");
        throughput.setCellFactory(CheckBoxTableCell.forTableColumn(throughput));
        throughput.setOnEditCommit(e -> e.getRowValue().setThroughput(e.getNewValue()));
        throughput.setCellValueFactory(data -> data.getValue().getThroughput());
        table.getColumns().add(throughput);

        TableColumn<Measurement, Double> mean = new TableColumn<>("Mean");
        mean.setCellValueFactory(data -> data.getValue().getMean().asObject());
        table.getColumns().add(mean);


        TableColumn<Measurement, String> metric = new TableColumn<>("Metric");
        metric.setCellValueFactory(data -> new StringBinding() {
            {
                super.bind(data.getValue().getThroughput());
            }
            @Override
            protected String computeValue() {
                return data.getValue().getThroughput().get() ? Measurement.THROUGHPUT : Measurement.TIME;
            }
        });
        table.getColumns().add(metric);

    }

    // DONE: handle actions
    @FXML
    protected void addBenchmark() throws IOException {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("/ssw/benchmark/viewer/add-view.fxml"));
            HBox hBox = fxmlloader.load();
            Stage popOutStage = new Stage();

            AddController addController = fxmlloader.getController();
            addController.setModel(model);
            addController.setStage(popOutStage);

            popOutStage.setTitle("Add Benchmark");
            popOutStage.initModality(Modality.WINDOW_MODAL);
            popOutStage.initOwner(primaryStage);
            popOutStage.setScene(new Scene(hBox));
            popOutStage.show();
        } catch (IOException e) {
            throw new IOException("Cannot load FXML AddView");
        }
    }

    @FXML
    protected void removeBenchmark() {
       Measurement measurement = table.getSelectionModel().getSelectedItem();
        if (measurement != null) {
            model.getMeasurements().remove(measurement);
        }
    }

    private void setUpIterations(TableColumn<Measurement, Double> col, int idx){
        col.setCellFactory(TextFieldTableCell.forTableColumn(new MyStringConverter()));
        col.setOnEditCommit(e -> e.getRowValue().setIteration(idx, e.getNewValue()));
        col.setCellValueFactory(data -> data.getValue().getIterations().get(idx).asObject());
    }

    private static class MyStringConverter extends StringConverter<Double> {

        @Override
        public String toString(Double aDouble) {
            return String.format("%.2f", aDouble);
        }

        @Override
        public Double fromString(String s) {
            try{
                double d = Double.parseDouble(s);
                return Math.max(d, 0.0);
            }catch (NumberFormatException e) {
                return 0.0;
            }
        }
    }
}