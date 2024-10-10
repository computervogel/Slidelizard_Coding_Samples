module ssw.benchmark.viewer {
    requires javafx.controls;
    requires javafx.fxml;


    opens ssw.benchmark.viewer to javafx.fxml;
    exports ssw.benchmark.viewer;
    exports ssw.benchmark.viewer.model;
}