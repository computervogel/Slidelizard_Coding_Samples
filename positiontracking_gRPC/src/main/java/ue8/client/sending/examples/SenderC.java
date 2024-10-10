package ue8.client.sending.examples;

import java.io.IOException;
import java.util.Random;

import ue8.client.sending.PositionSendingClient;

public class SenderC {
	private static final Random random = new Random();

	public static void main(String[] args) {
		PositionSendingClient client = new PositionSendingClient("SenderC");
		try {
			client.connect();
			pause();
			client.sendPosition(170, 10);
			pause();
			client.sendPosition(180, 10);
			pause();
			client.sendPosition(180, 30);
			pause();
			client.sendPosition(180, 10);
			pause();
			client.sendPosition(190, 10);
			pause();
			client.sendPosition(190, 30);
			pause();
			client.sendPosition(190, 10);
			pause();
			client.sendPosition(200, 10);
			pause();
			client.sendPosition(200, 20);
			pause();
			client.sendPosition(190, 20);
			pause();
			client.sendPosition(200, 30);
			pause();
			client.sendPosition(210, 30);
			pause();
			client.sendPosition(220, 10);
			pause();
			client.sendPosition(215, 20);
			pause();
			client.sendPosition(210, 10);
			pause();
			client.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void pause() {
		try {
			Thread.sleep(random.nextInt(500) + 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
