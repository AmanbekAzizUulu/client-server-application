package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApplication {
	private static BufferedReader read_from_server;
	private static BufferedWriter write_to_server;

	public static void main(String[] args) throws Exception {

		try (ServerSocket server_socket = new ServerSocket(30333)) {
			Socket accepted_socket_connection = server_socket.accept();
			System.out.println("Сlient: <" + accepted_socket_connection.getInetAddress().getHostName() + "> connected successfully!");
			try {
					read_from_server = new BufferedReader(new InputStreamReader(accepted_socket_connection.getInputStream()));
					write_to_server = new BufferedWriter(new OutputStreamWriter(accepted_socket_connection.getOutputStream()));

					while (true) {
						String message_from_client = read_from_server.readLine();
						if ("close".equals(message_from_client))break;

						System.out.println("Client sent: " + message_from_client);
						write_to_server.write(message_from_client + " - accepted\n");
						write_to_server.flush();
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Client closed connection.");
		}

	}

}
