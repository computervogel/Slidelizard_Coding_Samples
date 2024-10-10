package ue7.client.sending.examples;

import java.io.IOException;
import java.util.Random;

import ue7.client.sending.PositionSendingClient;

public class HausVomNikolausSender {

	private static final Random random = new Random();

	public static void main(String[] args) {
		PositionSendingClient client = new PositionSendingClient("HausVomNikolaus");
		try {
			client.connect();
			pause();
			client.sendPosition(100, 200);
			pause();
			client.sendPosition(200, 100);
			pause();
			client.sendPosition(150, 50);
			pause();
			client.sendPosition(100, 100);
			pause();
			client.sendPosition(200, 200);
			pause();
			client.sendPosition(200, 100);
			pause();
			client.sendPosition(100, 100);
			pause();
			client.sendPosition(100, 200);
			pause();
			client.sendPosition(200, 200);
			pause();
			client.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void pause() {
		try {
			Thread.sleep(random.nextInt(500) + 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
