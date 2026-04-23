package com.eriklievaart.projector.web.file;

import java.io.File;

import com.eriklievaart.jl.core.api.Bean;
import com.eriklievaart.jl.core.api.Parameters;
import com.eriklievaart.jl.core.api.page.AbstractTemplateController;
import com.eriklievaart.toolkit.io.api.FileTool;

public class EditorController extends AbstractTemplateController {

	@Bean
	private Parameters parameters;
	private File backup = new File("tmp/backup");
	private File file;

	public EditorController(File file) {
		this.file = file.isFile() ? file : null;
		backup.mkdir();
	}

	@Override
	public void invoke() throws Exception {
		if (parameters.contains("raw")) {
			String backupName = System.currentTimeMillis() + "-" + file.getName();
			FileTool.copyFile(file, new File(backup, backupName));
			FileTool.writeStringToFile(parameters.getString("raw"), file);
		}
		if (file != null && file.isFile()) {
			model.put("raw", FileTool.toString(file));
		} else {
			model.put("raw", "no data");
		}
		setTemplate("/web/freemarker/editor.ftlh");
	}
}
