package ue8.client.sending.examples;

import java.io.IOException;
import java.util.Random;

import ue8.client.sending.PositionSendingClient;

public class SenderA {
	private static final Random random = new Random();

	public static void main(String[] args) {
		PositionSendingClient client = new PositionSendingClient("SenderA");
		try {
			client.connect();
			pause();
			client.sendPosition(17, 1);
			pause();
			client.sendPosition(7, 18);
			pause();
			client.sendPosition(12, 10);
			pause();
			client.sendPosition(21, 15);
			pause();
			client.sendPosition(26, 6);
			pause();
			client.sendPosition(16, 23);
			pause();
			client.sendPosition(25, 28);
			pause();
			client.sendPosition(35, 11);
			pause();
			client.sendPosition(25, 28);
			pause();
			client.sendPosition(33, 33);
			pause();
			client.sendPosition(43, 16);
			pause();
			client.sendPosition(42, 38);
			pause();
			client.sendPosition(52, 21);
			pause();
			client.sendPosition(69, 31);
			pause();
			client.sendPosition(60, 26);
			pause();
			client.sendPosition(50, 43);
			pause();
			client.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected static void pause() {
		try {
			Thread.sleep(random.nextInt(500) + 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
