package com.bq.blog.handlers;

import java.util.Calendar;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.RoutingContext;

public interface StatusHandler extends Handler<RoutingContext> {

    Logger LOGGER = LoggerFactory.getLogger(StatusHandler.class);

    @Override
    default void handle(RoutingContext context) {

        LOGGER.info("Status " + Calendar.getInstance().getTimeInMillis());
        context.response().putHeader("content-type", "application/json")
                .end(new JsonObject().put("status", "Show must go on").encode());
    }
}
