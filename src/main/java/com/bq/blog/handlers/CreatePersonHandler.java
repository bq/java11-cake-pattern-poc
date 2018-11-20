package com.bq.blog.handlers;

import java.util.Optional;

import com.bq.blog.converters.PersonDtoToPersonConverter;
import com.bq.blog.dto.PersonDto;
import com.bq.blog.service.PersonServiceComponent;
import com.bq.blog.storage.DatastoreDao;
import com.bq.blog.storage.InMemoryPersonDaoComponent;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.RoutingContext;

public interface CreatePersonHandler extends
		Handler<RoutingContext>,
		PersonServiceComponent,
		DatastoreDao,
		PersonDtoToPersonConverter {

	Logger LOGGER = LoggerFactory.getLogger(CreatePersonHandler.class);

	@Override
	default void handle(RoutingContext context) {
		LOGGER.info("CreatePersonHandler " + context.getBodyAsString());
		var config = Optional.of(context.vertx().getOrCreateContext().config());
		addEntity(
				// form personDto To person
				apply(
						PersonDto.builder().withPersonAsJson(context.getBodyAsJson()).build()
				)).subscribe(r -> context.response().setStatusCode(HttpResponseStatus.OK.code())
						.putHeader("content-type", "application/json")
				.end(context.getBodyAsJson().put("ID", r).encode()));
	}
}
