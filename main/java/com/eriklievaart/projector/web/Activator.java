package com.eriklievaart.projector.web;

import java.io.File;
import java.util.function.Supplier;

import org.osgi.framework.BundleContext;

import com.eriklievaart.jl.core.api.osgi.LightningActivator;
import com.eriklievaart.jl.core.api.page.PageSecurity;
import com.eriklievaart.jl.core.api.websocket.WebSocketService;
import com.eriklievaart.osgi.toolkit.api.ContextWrapper;
import com.eriklievaart.projector.web.controller.FaviconController;
import com.eriklievaart.projector.web.controller.PushBodyController;
import com.eriklievaart.projector.web.controller.PushPathController;
import com.eriklievaart.projector.web.controller.RootController;
import com.eriklievaart.projector.web.controller.SupplierController;
import com.eriklievaart.projector.web.socket.PingPong;
import com.eriklievaart.projector.web.socket.TexSocketService;
import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class Activator extends LightningActivator {
	private static final String JQUERY = "/web/jquery-3.7.0.js";
	private LogTemplate log = new LogTemplate(getClass());

	@Override
	protected void init(BundleContext context) throws Exception {
		TexSocketService service = new TexSocketService();
		addServiceWithCleanup(WebSocketService.class, service);
		new Thread(new PingPong(service)).start();
		Supplier<String> cssLoader = getCssLoader();
		int port = getContextWrapper().getPropertyInt("org.osgi.service.http.port", 8000);

		addTemplateSource();
		addPageService(builder -> {
			builder.newRoute("root").mapGet("", () -> new RootController(port));
			builder.newRoute("css").mapGet("style.css", () -> new SupplierController(cssLoader));
			builder.newRoute("favicon").mapGet("favicon.ico", () -> new FaviconController());
			builder.newRoute("push.path").mapPost("push/path", () -> new PushPathController(service));
			builder.newRoute("push.body").mapPost("push/body", () -> new PushBodyController(service));
			builder.newRoute("jquery").mapGet("jquery.js", () -> new SupplierController(resource(JQUERY)));
			builder.setSecurity(new PageSecurity((route, ctx) -> true));
		});
	}

	private Supplier<String> resource(String path) {
		return () -> ResourceTool.getString(getClass(), path);
	}

	private Supplier<String> getCssLoader() {
		ContextWrapper wrapper = getContextWrapper();
		String path = wrapper.getPropertyString("com.eriklievaart.projector.web.style.file", null);
		log.info("configured css file: $", path);
		if (Str.isBlank(path)) {
			log.info("using css classpath loader");
			return () -> ResourceTool.getString(getClass(), "/web/style.css");
		}
		log.info("using css file loader");
		return () -> FileTool.toString(new File(path));
	}
}
