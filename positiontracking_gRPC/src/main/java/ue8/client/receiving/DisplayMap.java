package ue8.client.receiving;

import java.util.concurrent.ConcurrentHashMap;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Class for displaying positions.
 */
public class DisplayMap extends Application {
	private static final boolean DEBUG_OUTPUT = true;
	private static final ConcurrentHashMap<String, Point> positions = new ConcurrentHashMap<>();
	private static final Canvas canvas = new Canvas(500, 300);

	/**
	 * Starts the JavaFX application
	 */
	static void show() {
		launch();
	}

	/**
	 * Updates the client information. Marks the current position of the client on
	 * the map and draws a line from the previous position if such a position
	 * existed in the cache.
	 */
	static void update(String name, double x, double y) {
		final Point cur = new Point(x, y);
		Point prev = positions.put(name, cur);
		if (DEBUG_OUTPUT) {
			System.out.println("%s @ %s (before @ %s)".formatted(name, cur, prev));
		}
		// if we have a previous position stored, draw a line from 'prev' to 'cur'
		if (prev != null) {
			canvas.getGraphicsContext2D().strokeLine(prev.x, prev.y, cur.x, cur.y);
		}
		// draw 'cur' as point
		canvas.getGraphicsContext2D().fillOval(cur.x - 1, cur.y - 1, 2, 2);
	}

	/**
	 * Clears the drawing area and writes the given message onto it.
	 */
	static void showError(String message) {
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		canvas.getGraphicsContext2D().fillText(message, 0, 20);
	}

	@Override
	public void start(Stage stage) {
		Scene scene = new Scene(new VBox(canvas));
		stage.setScene(scene);
		stage.setTitle("DisplayMap");
		stage.show();
	}

	private record Point(double x, double y) {
	}
}