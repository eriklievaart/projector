package com.eriklievaart.projector.web.socket;

import com.eriklievaart.toolkit.lang.api.date.TimestampTool;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class PingPong implements Runnable {
	private LogTemplate log = new LogTemplate(getClass());

	private TexSocketService service;

	public PingPong(TexSocketService service) {
		this.service = service;
	}

	@Override
	public void run() {
		try {
			while (true) {
				service.pingPong();
				Thread.sleep(TimestampTool.ONE_MINUTE);
			}
		} catch (InterruptedException e) {
			log.warn(e);
		}
	}
}
