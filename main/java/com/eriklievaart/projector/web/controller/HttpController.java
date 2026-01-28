package com.eriklievaart.projector.web.controller;

import java.io.File;

import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.InputStreamRenderer;

public class HttpController implements PageController {

	private File file;

	public HttpController(File file) {
		this.file = file;
	}

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		response.setRenderer(new InputStreamRenderer(file));
	}
}
