package ue7.server;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import ue7.common.Pos;
import ue7.common.PosDecoder;
import ue7.common.PosEncoder;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@jakarta.websocket.server.ServerEndpoint(value = "/position/{clientName}", decoders = PosDecoder.class, encoders = PosEncoder.class)
public class ServerEndpoint {
    private static final Set<Session> senderSessions = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private static final Set<Session> receiverSessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @OnOpen
    public void onOpen(Session session, @PathParam("clientName") String clientName) {
        if ("receiver".equals(clientName)) {
            receiverSessions.add(session);
            System.out.println("Receiver connected: " + clientName);
        } else if (!clientName.contains(" ") && senderSessions.add(session)) {
            System.out.println("Sender connected: " + clientName);
        } else {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, clientName + " already exists"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnMessage
    public void onMessage(Pos pos, Session session) {
        for (Session receiverSession : receiverSessions) {
            if (receiverSession.isOpen()) {
                receiverSession.getAsyncRemote().sendObject(pos);
            }
        }
        System.out.println("Position received from " + pos.name() + ": (" + pos.x() + ", " + pos.y() + ")");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("Error for client: " + session.getId() + " - " + throwable.getMessage());
    }

    @OnClose
    public void onClose(Session session, @PathParam("clientName") String clientName) {
        if ("receiver".equals(clientName)) {
            receiverSessions.remove(session);
            System.out.println("Receiver disconnected: " + clientName);
        } else {
            senderSessions.remove(session);
            System.out.println("Sender disconnected: " + clientName);
        }
    }

    private static void broadCast(Pos pos, String userToSkip){
        for (Session receiverSession : receiverSessions) {
            if (!receiverSession.equals(userToSkip)) {
                receiverSession.getAsyncRemote().sendObject(pos);
            }
        }
    }
}
