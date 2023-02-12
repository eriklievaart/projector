package com.eriklievaart.projector.web.controller;

import java.io.File;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.StringRenderer;
import com.eriklievaart.projector.web.socket.TexSocketService;
import com.eriklievaart.toolkit.io.api.FileTool;

public class PushController implements PageController {

	private TexSocketService service;

	@Bean
	private Parameters parameters;

	public PushController(TexSocketService service) {
		this.service = service;
	}

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		String file = parameters.getString("file");
		service.push(FileTool.toString(new File(file)));
		response.setRenderer(new StringRenderer("ok"));
	}
}
