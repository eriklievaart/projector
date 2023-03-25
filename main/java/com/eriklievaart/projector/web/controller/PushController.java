package com.eriklievaart.projector.web.controller;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.StringRenderer;
import com.eriklievaart.projector.web.socket.TexSocketService;

public class PushController implements PageController {

	private TexSocketService service;

	@Bean
	private Parameters parameters;

	public PushController(TexSocketService service) {
		this.service = service;
	}

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		BodyContext.path = parameters.getString("file");
		response.setRenderer(new StringRenderer("ok"));
		service.push("reload");
	}
}
