package ue7.client.sending.examples;

import java.io.IOException;
import java.util.Random;

import ue7.client.sending.PositionSendingClient;

public class SenderD {
	private static final Random random = new Random();

	public static void main(String[] args) {
		PositionSendingClient client = new PositionSendingClient("SenderD");
		try {
			client.connect();
			pause();
			client.sendPosition(9, 114);
			pause();
			client.sendPosition(16, 121);
			pause();
			client.sendPosition(2, 135);
			pause();
			client.sendPosition(16, 121);
			pause();
			client.sendPosition(23, 128);
			pause();
			client.sendPosition(9, 142);
			pause();
			client.sendPosition(23, 128);
			pause();
			client.sendPosition(30, 135);
			pause();
			client.sendPosition(23, 142);
			pause();
			client.sendPosition(16, 135);
			pause();
			client.sendPosition(16, 149);
			pause();
			client.sendPosition(37, 142);
			pause();
			client.sendPosition(34, 153);
			pause();
			client.sendPosition(27, 145);
			pause();
			client.sendPosition(34, 153);
			pause();
			client.sendPosition(30, 163);
			pause();
			client.sendPosition(44, 149);
			pause();
			client.sendPosition(37, 170);
			pause();
			client.sendPosition(51, 156);
			pause();
			client.sendPosition(65, 170);
			pause();
			client.sendPosition(61, 174);
			pause();
			client.sendPosition(65, 170);
			pause();
			client.sendPosition(58, 163);
			pause();
			client.sendPosition(51, 170);
			pause();
			client.sendPosition(58, 177);
			pause();
			client.sendPosition(51, 184);
			pause();
			client.sendPosition(44, 177);
			pause();
			client.sendPosition(48, 174);
			pause();
			client.sendPosition(44, 177);
			pause();
			client.sendPosition(58, 191);
			pause();
			client.sendPosition(72, 177);
			pause();
			client.sendPosition(79, 184);
			pause();
			client.sendPosition(72, 191);
			pause();
			client.sendPosition(65, 184);
			pause();
			client.sendPosition(72, 177);
			pause();
			client.sendPosition(93, 198);
			pause();
			client.sendPosition(79, 213);
			pause();
			client.sendPosition(72, 206);
			pause();
			client.sendPosition(86, 191);
			pause();
			client.sendPosition(107, 213);
			pause();
			client.sendPosition(104, 216);
			pause();
			client.sendPosition(107, 213);
			pause();
			client.sendPosition(100, 206);
			pause();
			client.sendPosition(93, 213);
			pause();
			client.sendPosition(100, 220);
			pause();
			client.sendPosition(93, 227);
			pause();
			client.sendPosition(86, 220);
			pause();
			client.sendPosition(107, 241);
			pause();
			client.sendPosition(100, 234);
			pause();
			client.sendPosition(107, 227);
			pause();
			client.sendPosition(114, 234);
			pause();
			client.sendPosition(107, 227);
			pause();
			client.sendPosition(114, 220);
			pause();
			client.sendPosition(121, 227);
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
