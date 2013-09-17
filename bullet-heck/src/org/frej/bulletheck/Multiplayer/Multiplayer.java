package org.frej.bulletheck.Multiplayer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import com.badlogic.gdx.math.Vector2;

public class Multiplayer {
	private static final int BUFFER_SIZE = 1024;
	public static final String OP_SHOT = "SHOT";
	public static final String OP_DIE = "DIE";
	public static final String OP_POS = "POS";

	private DatagramSocket sendSocket;
	private DatagramChannel recvChannel;

	private DatagramPacket packet;
	private final String peerAdress;
	private byte[] sendBuffer;
	private byte[] recvBuffer;
	private String message;
	private Vector2 position;
	private Vector2 bulletVelocity;
	private ByteBuffer byteBuffer;

	private final int recvPort;
	private final int sendPort;

	public Multiplayer(String sendPort, String recvPort, String ip) {
		sendBuffer = new byte[BUFFER_SIZE];
		recvBuffer = new byte[BUFFER_SIZE];

		position = new Vector2(0, 0);
		bulletVelocity = new Vector2(0, 0);

		this.sendPort = Integer.decode(sendPort);
		this.recvPort = Integer.decode(recvPort);
		peerAdress = ip;

		try {
			sendSocket = new DatagramSocket();
			recvChannel = DatagramChannel.open();
			recvChannel.configureBlocking(false);
			recvChannel.bind(new InetSocketAddress(this.recvPort));
			byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	}

	public void handleMessage() {
		try {
			if (!recvChannel.isConnected()) {
				connectToOtherPlayer();
			} else {
				int recvBytes = 1;
				while (recvBytes != 0) {
					recvBytes = recvChannel.read(byteBuffer);
					readBuffer();
					decodeMessage();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void connectToOtherPlayer() {
		SocketAddress otherPlayer;
		try {
			otherPlayer = recvChannel.receive(byteBuffer);
			byteBuffer.clear();
			if (otherPlayer != null)
				recvChannel.connect(otherPlayer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void readBuffer() {
		byteBuffer.flip();
		int i = 0;
		while (byteBuffer.hasRemaining()) {
			recvBuffer[i] = byteBuffer.get();
			i++;
		}
		byteBuffer.clear();
	}

	private void decodeMessage() {
		try {
			message = new String(recvBuffer, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
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
		recvBuffer = new byte[BUFFER_SIZE];
	}

	public Vector2 getPosition() {
		return position;
	}

	public void shot(Vector2 bulletVelocity) {
		sendMessage(OP_SHOT + bulletVelocity.x + "," + bulletVelocity.y);
	}

	public void move(Vector2 newPosition) {
		sendMessage(OP_POS + newPosition.x + "," + newPosition.y);

	}

	public Vector2 getBulletVelocity() {
		Vector2 ret = bulletVelocity;
		bulletVelocity = new Vector2(0, 0);
		return ret;
	}

}
