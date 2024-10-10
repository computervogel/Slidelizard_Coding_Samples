package ue8.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class PositionServer {
    private static final int PORT = 8080;

    public static void main(String[] args) {
        Server server = ServerBuilder.forPort(PORT).addService(new PositionServerImpl()).build();
        try {
            server.start();
            System.out.println("Server started on port " + PORT);
            server.awaitTermination();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            server.shutdown();
        }
    }
}
