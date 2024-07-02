package com.eriklievaart.projector.web.controller;

import com.eriklievaart.jl.core.api.page.AbstractTemplateController;

public class RootController extends AbstractTemplateController {

	private int port;

	public RootController(int port) {
		this.port = port;
	}

	@Override
	public void invoke() throws Exception {
		setTemplate("/web/freemarker/projector.ftlh");

		String body = BodyContext.getBody();
		model.put("body", body);
		model.put("port", port);
	}
}
