package ue7.client.sending;

import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.client.ClientManager;
import ue7.common.Pos;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class PositionSendingClient {
	protected final String name;
	private final PositionSendingClientEndpoint endpoint;

	public PositionSendingClient(String name) {
		if (name.contains("reveiver")) {
			throw new RuntimeException("Name of client " + name + " is invalid!");
		}

		this.name = name;
		this.endpoint = new PositionSendingClientEndpoint();
	}

	/**
	 * If not already connected, creates a connection to the server (using
	 * sendingClientEndpoint as an endpoint instance and name as a path parameter).
	 */
	public void connect() {
		// TODO
		if(endpoint.isConnected()) {
			System.out.println("Sending Client " + name + " is already connected!");
			return;
		}

		try{
			ClientManager manager = ClientManager.createClient();
			manager.connectToServer(endpoint, new URI("ws://localhost:8080/ws/position/" + name));
		} catch (URISyntaxException | DeploymentException | IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Uses sendingClientEndpoint to send the coordinates and the client name to the
	 * server.
	 */
	public void sendPosition(double x, double y) throws IOException {
		// TODO
		if(endpoint.isConnected()) {
			endpoint.sendPosition(new Pos(name, x, y));
		} else {
			throw new RuntimeException("Client " + name + " is not connected!");
		}
	}

	/**
	 * If connected, uses sendingClientEndpoint to close the connection.
	 */
	public void closeConnection() throws IOException {
		// TODO
		if(endpoint.isConnected()){
			endpoint.getSession().close();
		} else {
			throw new RuntimeException("Connection closed");
		}
	}

}
