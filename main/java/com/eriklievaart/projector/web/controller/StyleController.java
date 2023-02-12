package com.eriklievaart.projector.web.controller;

import java.util.function.Supplier;

import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.StringRenderer;

public class StyleController implements PageController {

	private Supplier<String> source;

	public StyleController(Supplier<String> source) {
		this.source = source;
	}

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		response.setRenderer(new StringRenderer(source.get()));
	}
}
