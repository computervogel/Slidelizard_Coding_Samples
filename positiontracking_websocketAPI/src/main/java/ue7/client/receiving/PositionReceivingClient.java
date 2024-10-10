package ue7.client.receiving;

import jakarta.websocket.ContainerProvider;
import jakarta.websocket.WebSocketContainer;

import java.net.URI;

public class PositionReceivingClient {
	private PositionReceivingClientEndpoint endpoint;

	public static void main(String[] args) {
		// Start the GUI
		Thread guiThread = new Thread(DisplayMap::show);
		guiThread.start();

		// TODO Connect to the server

		PositionReceivingClient client = new PositionReceivingClient();

		try {
			client.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// We wait for the GUI to be closed
		try {
			guiThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// TODO Close the connection to the server (as the GUI has been closed now)
		try {
			client.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void connect() throws Exception {
		endpoint = new PositionReceivingClientEndpoint();
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		container.connectToServer(endpoint, URI.create("ws://localhost:8080/ws/position/receiver"));
	}

	public void close() throws Exception {
		if (endpoint.isConnected()) {
			endpoint.getSession().close();
		}
	}
}
