package ssw.benchmark.viewer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BenchmarkApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BenchmarkApplication.class.getResource("benchmark-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        BenchmarkController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);
        stage.setTitle("Benchmarks");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}