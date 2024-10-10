package ue8.client.sending.examples;

import java.io.IOException;
import java.util.Random;

import ue8.client.sending.PositionSendingClient;

public class SenderB {
	private static final Random random = new Random();

	public static void main(String[] args) {
		PositionSendingClient client = new PositionSendingClient("SenderB");
		try {
			client.connect();
			pause();
			client.sendPosition(90, 10);
			pause();
			client.sendPosition(90, 30);
			pause();
			client.sendPosition(100, 30);
			pause();
			client.sendPosition(100, 10);
			pause();
			client.sendPosition(120, 10);
			pause();
			client.sendPosition(110, 10);
			pause();
			client.sendPosition(110, 20);
			pause();
			client.sendPosition(120, 20);
			pause();
			client.sendPosition(110, 20);
			pause();
			client.sendPosition(110, 30);
			pause();
			client.sendPosition(120, 30);
			pause();
			client.sendPosition(110, 30);
			pause();
			client.sendPosition(110, 10);
			pause();
			client.sendPosition(140, 10);
			pause();
			client.sendPosition(140, 30);
			pause();
			client.sendPosition(140, 10);
			pause();
			client.sendPosition(130, 20);
			pause();
			client.sendPosition(140, 10);
			pause();
			client.sendPosition(160, 10);
			pause();
			client.sendPosition(160, 30);
			pause();
			client.sendPosition(150, 30);
			pause();
			client.sendPosition(150, 10);
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
