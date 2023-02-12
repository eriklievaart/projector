package com.eriklievaart.projector.web.controller;

import com.eriklievaart.jl.core.api.page.AbstractTemplateController;

public class RootController extends AbstractTemplateController {

	@Override
	public void invoke() throws Exception {
		setTemplate("/web/freemarker/projector.ftlh");
	}
}
