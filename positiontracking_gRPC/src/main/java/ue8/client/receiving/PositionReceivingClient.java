package ue8.client.receiving;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import ue8.protoOut.PositionTracking.*;
import ue8.protoOut.PositionTrackingServiceGrpc;

import java.util.concurrent.TimeUnit;

public class PositionReceivingClient {
	private static final String ADDRESS = "localhost";
	private static final int PORT = 8080;

	public static void main(String[] args) throws InterruptedException {
		// Start the GUI
		Thread guiThread = new Thread(DisplayMap::show);
		guiThread.start();

		ManagedChannel channel = ManagedChannelBuilder.forAddress(ADDRESS, PORT).usePlaintext().build();
		PositionTrackingServiceGrpc.PositionTrackingServiceStub asyncStub = PositionTrackingServiceGrpc.newStub(channel);

		StreamObserver<PositionInfo> responseObserver = new StreamObserver<>() {
			@Override
			public void onNext(PositionInfo value) {
				DisplayMap.update(value.getName(), value.getX(), value.getY());
			}

			@Override
			public void onError(Throwable t) {
				System.out.println("Error: " + t.getMessage());
			}

			@Override
			public void onCompleted() {
				System.out.println("Connection closed");
			}
		};

		StreamObserver<PositionRequest> sub = asyncStub.receivePosition(responseObserver);
		sub.onNext(PositionRequest.newBuilder().setReceive(true).build());

		// We wait for the GUI to be closed
		try {
			guiThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		sub.onCompleted();
		channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
	}
}
