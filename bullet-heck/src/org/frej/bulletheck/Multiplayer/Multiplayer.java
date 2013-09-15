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
	private Vector2 bulletVelocity;
	
	private int recvPort;
	private int sendPort;

	public Multiplayer(String sendPort,String recvPort) {
		sendBuffer = new byte[1024];
		reciveBuffer = new byte[1024];
		
		position = new Vector2(0, 0);
		bulletVelocity = new Vector2(0, 0);
		
		message = "";
		
		this.sendPort = Integer.decode(sendPort);
		this.recvPort = Integer.decode(recvPort);

		try {
			sendSocket = new DatagramSocket();
			recvSocket = new DatagramSocket(this.recvPort);
			recvSocket.setSoTimeout(1000);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void setPeerAdress(String peerAdress) {
		this.peerAdress = peerAdress;
	}

	private void sendMessage(String message) {
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
			String[] pos = message.substring(OP_POS.length()).split(",");
			position = new Vector2(Float.parseFloat(pos[0]),
					Float.parseFloat(pos[1]));
		}
		if (message.contains(OP_SHOT)) {
			String[] vel = message.substring(OP_SHOT.length()).split(",");
			bulletVelocity = new Vector2(Float.parseFloat(vel[0]),
					Float.parseFloat(vel[1]));
		}
		else {
			bulletVelocity=new Vector2(0,0);
		}

	}

	public Vector2 getPosition() {
		return position;
	}

	public void shot(Vector2 bulletPosition) {
		sendMessage(OP_SHOT+bulletPosition.x+","+bulletPosition.y);
	}
	public void move(Vector2 newPosition){
		sendMessage(OP_POS+newPosition.x+","+newPosition.y);
		
	}

	public Vector2 getBulletVelocity() {
		return bulletVelocity;
	}

}
