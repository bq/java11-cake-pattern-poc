package com.bq.blog.handlers;

import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.ResponseTimeHandler;
import io.vertx.rxjava.ext.web.handler.StaticHandler;

public class Routing {

    private static final Logger LOGGER = LoggerFactory.getLogger(Routing.class);

    private StatusHandler statusHandler;
    private CreatePersonHandler createPersonHandler;
    private RemovePersonHandler removePersonHandler;
    private RetrievePersonHandler retrievePersonHandler;
    private UpdatePersonHandler updatePersonHandler;

    private Router router;

    public Routing( StatusHandler statusHandler, CreatePersonHandler createPersonHandler, RemovePersonHandler removePersonHandler, RetrievePersonHandler retrievePersonHandler, UpdatePersonHandler updatePersonHandler) {

        this.statusHandler = statusHandler;
        this.createPersonHandler = createPersonHandler;
        this.removePersonHandler = removePersonHandler;
        this.retrievePersonHandler = retrievePersonHandler;
        this.updatePersonHandler = updatePersonHandler;
    }

    public Router get() {
        long bodyLimit = 1024;

        router.route().handler(ResponseTimeHandler.create());

        router.get("/pablopeople/v1/version").handler(statusHandler);
        router.get("/pablopeople/v1/person").handler(retrievePersonHandler);
        router.post("/pablopeople/v1/person").handler(BodyHandler.create().setBodyLimit(bodyLimit * bodyLimit));
        router.post("/pablopeople/v1/person").handler(createPersonHandler);
        router.put("/pablopeople/v1/person/:id").handler(BodyHandler.create().setBodyLimit(bodyLimit * bodyLimit));
        router.put("/pablopeople/v1/person/:id").handler(updatePersonHandler);
        router.delete("/pablopeople/v1/person/:id").handler(removePersonHandler);

        router.route().handler(BodyHandler.create().setBodyLimit(bodyLimit * bodyLimit));
        // Enable the web console to access swagger ui
        router.route().handler(StaticHandler.create());

        LOGGER.info("Routing get");
        for (var r: router.getRoutes()){
            if(r.getPath() != null) {
                LOGGER.info("PATH: " + r.getPath());
            }
        }

        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }
}
