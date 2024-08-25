package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {
	private static String message_from_client = "";
	private static String message_from_server = "";

	private static BufferedWriter write_to_client;
	private static BufferedReader read_from_client;

	public static void main(String[] args) {
		try (Socket client_socket = new Socket("localhost", 30333)) {
			write_to_client = new BufferedWriter(new OutputStreamWriter(client_socket.getOutputStream()));
			read_from_client = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
			try (Scanner scanner = new Scanner(System.in);) {
				while (true) {
					System.out.print("> ");
					message_from_client = scanner.nextLine();
					write_to_client.write(message_from_client);
					write_to_client.write("\n");
					write_to_client.flush();
					if ("close".equals(message_from_client)) break;


					message_from_server = read_from_client.readLine();
					System.out.println("Server sent: " + message_from_server);
				}
			}
			System.out.println("Client closed connection.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
