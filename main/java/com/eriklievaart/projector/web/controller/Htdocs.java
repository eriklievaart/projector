package com.eriklievaart.projector.web.controller;

import java.io.File;
import java.util.Set;

import com.eriklievaart.toolkit.lang.api.collection.NewCollection;
import com.eriklievaart.toolkit.logging.api.LogTemplate;
import com.eriklievaart.toolkit.vfs.api.VirtualFileScanner;

public class Htdocs {
	private LogTemplate log = new LogTemplate(getClass());

	private File root;
	private Set<String> paths = NewCollection.set();

	public Htdocs(File file) {
		root = file;
		refresh();
	}

	private void refresh() {
		paths.clear();
		if (!root.isDirectory()) {
			log.info("static dir $ does not exist", root);
			return;
		}
		int skip = root.getAbsolutePath().length() + 1;
		for (File file : new VirtualFileScanner(root).collectAsFileList()) {
			paths.add(file.getAbsolutePath().substring(skip));
		}
	}

	public File lookup(String path) {
		if (!path.startsWith("/web/")) {
			log.info("/web/");
			return null;
		}
		String tail = path.substring(5);
		log.info("tail: " + tail);
		if (!paths.contains(tail)) {
			refresh();
		}
		return paths.contains(tail) ? new File(root, tail) : null;
	}
}
