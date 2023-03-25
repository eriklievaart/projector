package com.eriklievaart.projector.web.controller;

import java.io.File;

import com.eriklievaart.toolkit.io.api.FileTool;

public class BodyContext {

	private static String data;
	private static boolean isPath;

	public static void setBody(String body) {
		isPath = false;
		data = body;
	}

	public static void setPath(String path) {
		isPath = true;
		data = path;
	}

	public static String getBody() {
		if (data == null) {
			return "contents not set";
		}
		if (isPath) {
			File file = new File(data);
			if (!file.exists()) {
				return "file not found: " + data;
			}
			if (!file.canRead()) {
				return "file found, but cannot read: " + data;
			}
			return FileTool.toString(file);

		} else {
			return data;
		}
	}
}
