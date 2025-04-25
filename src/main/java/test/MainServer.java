package test;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import service.Service;

public class MainServer {
public static void main(String[] args) {
		
		JFrame frame = new JFrame("server");
        JTextArea txt = new JTextArea();
        JScrollPane scroll = new JScrollPane(txt);
        scroll.setBorder(null);
        txt.setBorder(new EmptyBorder(10, 10, 10, 10));
        txt.setEditable(false);
        frame.add(scroll);
        frame.setSize(470, 430);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		int portNumber = 12345;
		try {
            Service.getInstance().startServer(portNumber, txt);
        } catch (Exception e) {
            e.printStackTrace();
            txt.append("Error: " + e + "\n");
        }
	
	}
}
