package ue8.client.sending.examples;

import java.io.IOException;
import java.util.Random;

import ue8.client.sending.PositionSendingClient;

public class SpiralSender {

	private static final Random random = new Random();

	public static void main(String[] args) {
		PositionSendingClient client = new PositionSendingClient("Spiral");

		double x = 350;
		double y = 150;

		try {
			client.connect();
			for (int i = 0; i < 100; i++) {
				double dx = Math.cos(i / 5d) * i;
				double dy = Math.sin(i / 5d) * i;
				client.sendPosition(x + dx, y + dy);
				pause();
			}
			client.closeConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void pause() {
		try {
			Thread.sleep(random.nextInt(50) + 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
