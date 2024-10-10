package ue7.server;

import java.util.Collections;
import java.util.Map;

public class Server {
    public static void main(String[] args) {
        Map<String, Object> map = Collections.emptyMap();
        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 8080, "/ws", map, ServerEndpoint.class);
        try {
            server.start();
            System.out.println("Server started on ws://localhost:8080/ws/position/{clientName}");
            Thread.currentThread().join();// Keeping the server running
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
