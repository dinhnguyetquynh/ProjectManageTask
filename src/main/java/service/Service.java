
package service;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JTextArea;

import model.Account;

public class Service {

	private static Service instance;
	private static Map<Account, PrintWriter> clients = new HashMap<>();
	
	public static Service getInstance() {
		if (instance == null) {
			instance = new Service();
		}
		return instance;
	}
	
	private Service() {
	}
	
	public PrintWriter getClientOutputStreamByRole(String role) {
		for (Map.Entry<Account, PrintWriter> entry : clients.entrySet()) {
	        if (entry.getKey().getRole().equals(role)) {
	            return entry.getValue();
	        }
	    }
	    return null; 
	}
	
	public void removeClientOutputStream(Account acc) {
		clients.remove(acc);
	}

	public void addClient(Account acc, PrintWriter out) {
		clients.put(acc, out);
	}
	
	
	public void startServer(int portNumber, JTextArea textArea) {
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            textArea.setText("Server đang lắng nghe trên cổng " + portNumber + "\n");
            
            // Lắng nghe tất cả client
            while (true) {
                Socket clientSocket = serverSocket.accept(); // Chờ Client kết nối
                textArea.append("Client đã kết nối: " + clientSocket.getInetAddress() + "\n");

                // Tạo luồng riêng cho mỗi client
                new ClientHandler(clientSocket, textArea).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            textArea.append("Error: " + e + "\n");
        }
	}
}
