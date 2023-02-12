package com.eriklievaart.projector.web.socket;

import java.util.List;
import java.util.function.Supplier;

import com.eriklievaart.jl.core.api.websocket.WebSocketService;
import com.eriklievaart.toolkit.lang.api.collection.NewCollection;

public class TexSocketService implements WebSocketService {

	private List<TexSocket> sockets = NewCollection.concurrentList();

	@Override
	public String getPath() {
		return "/web/socket";
	}

	@Override
	public Supplier<?> webSocketSupplier() {
		return () -> {
			TexSocket socket = new TexSocket(this);
			sockets.add(socket);
			return socket;
		};
	}

	public void push(String body) {
		for (TexSocket socket : sockets) {
			socket.push(body);
		}
	}

	public void unregister(TexSocket socket) {
		sockets.remove(socket);
	}

	public void pingPong() {
		for (TexSocket socket : sockets) {
			socket.sendPing();
		}
	}
}
