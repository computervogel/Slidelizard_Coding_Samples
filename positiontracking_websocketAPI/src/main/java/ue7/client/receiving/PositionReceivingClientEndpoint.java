package ue7.client.receiving;

import jakarta.websocket.*;
import ue7.common.Pos;
import ue7.common.PosEncoder;
import ue7.common.PosDecoder;

@ClientEndpoint(encoders = PosEncoder.class, decoders = PosDecoder.class)
public class PositionReceivingClientEndpoint {
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        System.out.println("Connected to server");
    }

    @OnMessage
    public void onMessage(Pos pos) {
        System.out.println("Received position: " + pos);
        DisplayMap.update(pos.name(), pos.x(), pos.y());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        System.out.println("Session closed: " + closeReason.getReasonPhrase());
    }

    public boolean isConnected() {
        return session != null && session.isOpen();
    }

    public Session getSession() {
        return session;
    }
}
