package com.eriklievaart.projector.web.socket;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class TexSocket implements WebSocketListener {
	private LogTemplate log = new LogTemplate(getClass());

	private Session session;
	private TexSocketService service;

	public TexSocket(TexSocketService service) {
		this.service = service;
	}

	@Override
	public void onWebSocketConnect(Session s) {
		this.session = s;
		log.debug(session.hashCode() + " has opened a connection");
		try {
			session.getRemote().sendString("Connection Established");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onWebSocketClose(int status, String reason) {
		service.unregister(this);
		log.debug("Session " + session.hashCode() + " has ended");
	}

	@Override
	public void onWebSocketError(Throwable t) {
		t.printStackTrace();
	}

	@Override
	public void onWebSocketBinary(byte[] payload, int offset, int length) {
	}

	@Override
	public void onWebSocketText(String message) {
		log.trace("Message from " + session.hashCode() + ": " + message);
		try {
			session.getRemote().sendString(message);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void push(String body) {
		try {
			session.getRemote().sendString(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendPing() {
		try {
			session.getRemote().sendPing(ByteBuffer.wrap(new byte[] {}));
		} catch (IOException e) {
			log.warn("error sending ping: $", e.getMessage());
		}
	}
}
