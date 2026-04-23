package com.eriklievaart.projector.web.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.ResponseBuilder;
import com.eriklievaart.jl.core.api.page.PageController;
import com.eriklievaart.jl.core.api.render.InputStreamRenderer;
import com.eriklievaart.jl.core.api.render.StringRenderer;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class HtdocsController implements PageController {
	private LogTemplate log = new LogTemplate(getClass());

	@Bean
	private HttpServletRequest request;

	private Htdocs htdocs;

	public HtdocsController(Htdocs docs) {
		this.htdocs = docs;
	}

	@Override
	public void invoke(ResponseBuilder response) throws Exception {
		String uri = request.getRequestURI();
		log.debug("URI: " + uri);

		File file = htdocs.lookup(uri);
		if (file == null) {
			response.setRenderer(new StringRenderer("path not found: " + uri));
			response.setStatusCode(404);
		} else {
			response.setRenderer(new InputStreamRenderer(file));
		}
	}
}
