package ue8.client.sending;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ue8.protoOut.PositionTrackingServiceGrpc;
import ue8.protoOut.PositionTrackingServiceGrpc.*;
import ue8.protoOut.PositionTracking.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PositionSendingClient {
	protected final String name;
	private PositionTrackingServiceStub asyncStub;
	private StreamObserver<PositionInfo> requestObserver;
	private ManagedChannel channel;

	private final String ADDRESS = "localhost";
	private final int PORT = 8080;

	public PositionSendingClient(String name) {
		this.name = name;
	}

	/**
	 * If not already connected, creates a connection to the server.
	 */
	public void connect() {
		if (isConnected()) {
			System.out.println("Already connected");
			return;
		}

		channel = ManagedChannelBuilder.forAddress(ADDRESS, PORT).usePlaintext().build();
		asyncStub = PositionTrackingServiceGrpc.newStub(channel);
		StreamObserver<Ack> responseObserver = new StreamObserver<>() {
			@Override
			public void onNext(Ack value) {
				System.out.println("Received Ack: " + value);
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("Received Error: " + t);
			}

			@Override
			public void onCompleted() {
				System.out.println("Received Completed");
			}
		};
		requestObserver = asyncStub.sendPosition(responseObserver);
	}

	/**
	 * Sends the coordinates and the client name to the server.
	 */
	public void sendPosition(double x, double y) throws IOException {
		if(isConnected()) {
			PositionInfo position = PositionInfo.newBuilder().setName(name).setX(x).setY(y).build();
			requestObserver.onNext(position);
		} else {
			throw new RuntimeException("Not connected");
		}
	}

	/**
	 * If connected, tells the server that no more data will follow, and closes the
	 * connection.
	 */
	public void closeConnection() throws IOException {
		try {
			if(isConnected()){
				requestObserver.onCompleted();
			}
			channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean isConnected() {
		return asyncStub != null && !channel.isShutdown();
	}
}
