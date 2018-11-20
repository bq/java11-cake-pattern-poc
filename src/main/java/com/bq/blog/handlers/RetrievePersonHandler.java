package com.bq.blog.handlers;

import com.bq.blog.converters.PersonToPersonDto;
import com.bq.blog.service.PersonServiceComponent;
import com.bq.blog.storage.InMemoryPersonDaoComponent;
import com.google.gson.Gson;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.RoutingContext;

import static com.bq.blog.service.PersonPredicates.alwaysTrue;

import java.util.ArrayList;
import java.util.stream.Collectors;

public interface RetrievePersonHandler extends
		Handler<RoutingContext>,
		PersonServiceComponent,
		PersonToPersonDto,
		InMemoryPersonDaoComponent {

	Logger LOGGER = LoggerFactory.getLogger(RetrievePersonHandler.class);

	@Override
	default void handle(RoutingContext context) {
		LOGGER.info("RetrievePersonHandler");
        this.fetchEntities(alwaysTrue()).subscribe(peopleRetrieved -> {

			context.response().putHeader("content-type", "application/json")
					.end(new Gson().toJson(convertToStream(peopleRetrieved).collect(Collectors.toList()), ArrayList.class));
        });
	}
}
