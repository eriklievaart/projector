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
import com.eriklievaart.projector.web.controller.StyleController;
import com.eriklievaart.projector.web.socket.PingPong;
import com.eriklievaart.projector.web.socket.TexSocketService;
import com.eriklievaart.toolkit.io.api.FileTool;
import com.eriklievaart.toolkit.io.api.ResourceTool;
import com.eriklievaart.toolkit.lang.api.str.Str;
import com.eriklievaart.toolkit.logging.api.LogTemplate;

public class Activator extends LightningActivator {
	private LogTemplate log = new LogTemplate(getClass());

	@Override
	protected void init(BundleContext context) throws Exception {
		TexSocketService service = new TexSocketService();
		addServiceWithCleanup(WebSocketService.class, service);
		new Thread(new PingPong(service)).start();

		addTemplateSource();
		addPageService(builder -> {
			builder.newRoute("root").mapGet("", () -> new RootController());
			builder.newRoute("css").mapGet("style.css", () -> new StyleController(getCssLoader()));
			builder.newRoute("favicon").mapGet("favicon.ico", () -> new FaviconController());
			builder.newRoute("push.path").mapPost("push/path", () -> new PushPathController(service));
			builder.newRoute("push.body").mapPost("push/body", () -> new PushBodyController(service));
			builder.setSecurity(new PageSecurity((route, ctx) -> true));
		});
	}

	private Supplier<String> getCssLoader() {
		ContextWrapper wrapper = getContextWrapper();
		String path = wrapper.getPropertyString("com.eriklievaart.projector.web.style.file", null);
		if (Str.isBlank(path)) {
			log.info("css classpath loader");
			return () -> ResourceTool.getString(getClass(), "/web/style.css");
		}
		log.info("css file loader: $", path);
		return () -> FileTool.toString(new File(path));
	}
}
