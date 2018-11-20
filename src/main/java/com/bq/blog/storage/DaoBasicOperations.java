package com.bq.blog.storage;

import java.util.function.Predicate;
import java.util.stream.Stream;

import com.bq.blog.model.Person;

public interface DaoBasicOperations {

	void addEntity(Person entity);

	Stream<Person> filterEntities(Predicate<Person> predicate);

	boolean existEntities(Predicate<Person> predicate);

	void removeEntities(Predicate<Person> predicate);
}
