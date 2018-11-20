package com.bq.blog.handlers;

import com.bq.blog.service.PersonServiceComponent;
import com.bq.blog.storage.InMemoryPersonDaoComponent;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.RoutingContext;

import static com.bq.blog.service.PersonPredicates.byID;

public interface RemovePersonHandler extends
		Handler<RoutingContext>,
		PersonServiceComponent,
		InMemoryPersonDaoComponent {

	Logger LOGGER = LoggerFactory.getLogger(RemovePersonHandler.class);

	@Override
	default void handle(RoutingContext context) {
		String personID = context.request().getParam("id");
		LOGGER.info("RemovePersonHandler personID " + personID);
		this.deleteEntities(byID(personID));
		context.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code()).putHeader("content-type", "application/json");
	}
}
