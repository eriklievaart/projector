package com.eriklievaart.projector.web.controller;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.projector.web.socket.TexSocketService;
import com.eriklievaart.toolkit.io.api.StreamTool;

public class PushBodyController implements PageController {

	private TexSocketService service;

	@Bean
	private HttpServletRequest request;

	public PushBodyController(TexSocketService service) {
		this.service = service;
	}

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		ServletInputStream is = request.getInputStream();
		BodyContext.setBody(StreamTool.toString(is));
		service.push("reload");
	}
}
