package com.bq.blog.service;

import static com.bq.blog.service.PersonPredicates.byID;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Stream;

import com.bq.blog.configuration.SharedConfig;
import com.bq.blog.model.Person;
import com.bq.blog.storage.IPersonDaoComponent;

import io.vertx.core.json.JsonObject;
import rx.Single;

public interface PersonServiceComponent extends IPersonDaoComponent {

	Logger LOG = Logger.getLogger(PersonServiceComponent.class.getName());

	default Single<String> addEntity(Person entity) {
		LOG.info("Service layer addEntity");
		entity.setID(UUID.randomUUID().toString());
		this.personDao().addEntity(entity);
		return Single.just(entity.getID());
	}

	default Single<Stream<Person>> fetchEntities(Predicate<Person> predicate) {
		LOG.info("Service layer filterEntities");
		return Single.just(this.personDao().filterEntities(predicate));
	}

	default boolean isEntities(Predicate<Person> predicate) {
		LOG.info("Service layer existEntities");
		return this.personDao().existEntities(predicate);
	}

	default void deleteEntities(Predicate<Person> predicate) {
		LOG.info("Service layer removeEntities");
		this.personDao().removeEntities(predicate);
	}

	default void updateEntity(String ID, Optional<Person> person) {
		if(this.isEntities(byID(ID)) && person.isPresent()){
			this.deleteEntities(byID(ID));
			this.addEntity(person.get());
		}
	}
}
