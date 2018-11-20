package com.bq.blog.handlers;

import com.bq.blog.converters.PersonDtoToPersonConverter;
import com.bq.blog.dto.PersonDto;
import com.bq.blog.service.PersonServiceComponent;
import com.bq.blog.storage.InMemoryPersonDaoComponent;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.RoutingContext;

import java.util.Optional;

public interface UpdatePersonHandler extends
		Handler<RoutingContext> ,
		PersonServiceComponent,
		PersonDtoToPersonConverter,
		InMemoryPersonDaoComponent {

	Logger LOGGER = LoggerFactory.getLogger(UpdatePersonHandler.class);

	@Override
	default void handle(RoutingContext context) {
		var personID = context.request().getParam("id");
		var personToSave = PersonDto.builder().withPersonAsJson(context.getBodyAsJson()).build();

		LOGGER.info("UpdatePersonHandler " + personID);
		this.updateEntity(personID, Optional.of(apply(personToSave)));
		context.response().setStatusCode(HttpResponseStatus.NO_CONTENT.code()).putHeader("content-type", "application/json").end();
	}
}
