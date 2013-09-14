package org.frej.bulletheck.Multiplayer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.badlogic.gdx.math.Vector2;

public class Multiplayer {
	public static final int PORT = 9000;
	public static final String OP_SHOT = "SHOT";
	public static final String OP_DIE = "DIE";
	public static final String OP_POS = "POS";

	private DatagramSocket sendSocket;
	private DatagramSocket recvSocket;

	private DatagramPacket packet;
	private String peerAdress = "localhost";
	private byte[] sendBuffer;
	private byte[] reciveBuffer;
	private String message;
	private Vector2 position;
	
	private int recvPort;
	private int sendPort;

	public Multiplayer(String sendPort,String recvPort) {
		sendBuffer = new byte[1024];
		reciveBuffer = new byte[1024];
		position = new Vector2(0, 0);
		message = "";
		this.sendPort = Integer.decode(sendPort);
		this.recvPort = Integer.decode(recvPort);

		try {
			sendSocket = new DatagramSocket();
			recvSocket = new DatagramSocket(this.recvPort);
			recvSocket.setSoTimeout(100);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void setPeerAdress(String peerAdress) {
		this.peerAdress = peerAdress;
	}

	public void sendMessage(String message) {
		try {
			sendBuffer = message.getBytes("utf-8");
			packet = new DatagramPacket(sendBuffer, sendBuffer.length,
					InetAddress.getByName(peerAdress), sendPort);
			sendSocket.send(packet);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//System.out.println("Wysłałem: " + message);
	}

	public void handleMessage() {
		try {
			reciveBuffer = new byte[1024];
			packet = new DatagramPacket(reciveBuffer, reciveBuffer.length);
			recvSocket.receive(packet);
			message = new String(packet.getData(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
		}
		//System.out.println("Otrzymałem: " + message);
		if (message.contains(OP_POS)) {
			String[] pos = message.substring(3).split(",");
			position = new Vector2(Float.parseFloat(pos[0]),
					Float.parseFloat(pos[1]));
		}

	}

	public Vector2 getPosition() {
		return position;
	}

}
