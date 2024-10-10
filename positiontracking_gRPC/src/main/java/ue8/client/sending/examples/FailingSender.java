package ue8.client.sending.examples;

import java.io.IOException;

import ue8.client.sending.PositionSendingClient;

public class FailingSender {
	public static void main(String[] args) {
		PositionSendingClient client = new PositionSendingClient("Failing");
		try {
			client.sendPosition(100, 200);
			throw new RuntimeException("Unconnected - should have thrown an exception but did not");
		} catch (IOException e) {
			System.out.println("Expected exception, as connect() has not been called");
		}
	}
}
