package com.eriklievaart.projector.web.controller;

import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.InputStreamRenderer;

public class FaviconController implements PageController {

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		response.setContentType("image/png");
		response.setRenderer(new InputStreamRenderer(getClass().getResourceAsStream("/web/favicon.png")));
	}
}
