package ue8.server;

import io.grpc.stub.StreamObserver;
import ue8.protoOut.PositionTracking;
import ue8.protoOut.PositionTrackingServiceGrpc;
import ue8.protoOut.PositionTracking.PositionInfo;
import ue8.protoOut.PositionTracking.PositionRequest;
import ue8.protoOut.PositionTracking.Ack;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class PositionServerImpl extends PositionTrackingServiceGrpc.PositionTrackingServiceImplBase {
    private final Set<StreamObserver<PositionInfo>> clients = new CopyOnWriteArraySet<>();

    @Override
    public StreamObserver<PositionInfo> sendPosition(StreamObserver<PositionTracking.Ack> responseObserver) {
        return new StreamObserver<>() {

            @Override
            public void onNext(PositionInfo value) {
                for (StreamObserver<PositionInfo> client : clients){
                    client.onNext(value);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error in sendPosition: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(Ack.newBuilder().build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<PositionRequest> receivePosition(StreamObserver<PositionTracking.PositionInfo> responseObserver) {
        return new StreamObserver<>() {

            @Override
            public void onNext(PositionRequest value) {
                if(value.getReceive()){
                    clients.add(responseObserver);
                } else {
                    clients.remove(responseObserver);
                }
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error in receivePosition: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                clients.remove(responseObserver);
                System.out.println("Connection closed");
                responseObserver.onCompleted();
            }
        };
    }
}
