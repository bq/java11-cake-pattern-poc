package com.bq.blog.verticles;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

import com.bq.blog.configuration.SharedConfig;
import com.bq.blog.handlers.CreatePersonHandler;
import com.bq.blog.handlers.RemovePersonHandler;
import com.bq.blog.handlers.RetrievePersonHandler;
import com.bq.blog.handlers.Routing;
import com.bq.blog.handlers.StatusHandler;
import com.bq.blog.handlers.UpdatePersonHandler;

import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import rx.Observable;
import io.vertx.config.ConfigRetriever;

public class MainVerticle extends io.vertx.rxjava.core.AbstractVerticle {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainVerticle.class);

	@Override
	public void start(final Future<Void> startedResult) {
		startServer().subscribe(
				t -> {
				},
				t -> LOGGER.error(t.getMessage()),
				() -> {
					startedResult.complete();
				}
		);
	}

	private Observable<HttpServer> startServer() {

		final AtomicReference<Observable<HttpServer>> result = new AtomicReference<>();
		final AtomicReference<HttpServer> httpServerRef = new AtomicReference<>();
		var configPath = Optional.ofNullable(System.getenv("CONFIG_PATH"));

		ConfigStoreOptions fileStore = new ConfigStoreOptions()
				.setType("file")
				.setConfig(new JsonObject().put("path", configPath.orElse("config_prod.json")));

		ConfigStoreOptions sysPropsStore = new ConfigStoreOptions().setType("sys");

		ConfigRetrieverOptions options = new ConfigRetrieverOptions()
				.addStore(fileStore).addStore(fileStore).addStore(sysPropsStore);

		ConfigRetriever retriever = ConfigRetriever.create(getVertx(), options);


			retriever.getConfig(ar -> {
				if (ar.failed()) {
					// Failed to retrieve the configuration
				} else {
					LOGGER.info(MainVerticle.class.getName() + " Running on " + SharedConfig.INSTANCE.getPort().orElse(8080));
					SharedConfig.INSTANCE.init(ar.result());
				}
			});


		HttpServerOptions httpOptions = new HttpServerOptions().setCompressionSupported(true);
		HttpServer  httpServer = vertx.createHttpServer(httpOptions);

		Integer configuredPort = SharedConfig.INSTANCE.getPort().orElse(8080);
		return httpServer.requestHandler(getRouting()::accept).rxListen(configuredPort).toObservable();

	}

	private Router getRouting(){
		Routing routing = new Routing(new StatusHandlerInstance(), new CreatePersonHandlerInstance(), new RemovePersonHandlerInstance(), new RetrievePersonHandlerInstance(), new UpdatePersonHandlerInstance());
		routing.setRouter(Router.router(vertx));
		return routing.get();
	}

	class CreatePersonHandlerInstance implements CreatePersonHandler {};
	class StatusHandlerInstance implements StatusHandler{};
	class RemovePersonHandlerInstance implements RemovePersonHandler {};
	class RetrievePersonHandlerInstance implements RetrievePersonHandler {};
	class UpdatePersonHandlerInstance implements UpdatePersonHandler {};
}