package com.eriklievaart.projector.web.controller;

import java.io.File;
import java.util.Date;

import com.eriklievaart.jl.core.api.page.AbstractTemplateController;
import com.eriklievaart.toolkit.io.api.FileTool;

public class RootController extends AbstractTemplateController {

	@Override
	public void invoke() throws Exception {
		setTemplate("/web/freemarker/projector.ftlh");

		String path = BodyContext.path;

		if (path == null) {
			model.put("body", new Date() + " unknown file");
			return;
		}
		File file = new File(path);
		if (!file.exists()) {
			model.put("body", "file not found: " + path);
			return;
		}
		if (!file.canRead()) {
			model.put("body", "file found, but cannot read: " + path);
			return;
		}
		model.put("body", FileTool.toString(file));
	}
}
