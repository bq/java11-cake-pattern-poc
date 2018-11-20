package com.bq.blog.storage;


import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bq.blog.model.Person;

public interface InMemoryPersonDaoComponent extends IPersonDaoComponent {

	default DaoBasicOperations personDao() {
		return new PersonDAO();
	}

	class PersonDAO implements DaoBasicOperations {


		@Override
		public void addEntity(Person entity) {
			ShareCollection.INSTANCE.getEntitiesStorage().add(entity);
		}

		@Override
		public Stream<Person> filterEntities(Predicate<Person> predicate) {
			return ShareCollection.INSTANCE.getEntitiesStorage().stream().filter(predicate);
		}

		@Override
		public boolean existEntities(Predicate<Person> predicate) {
			return ShareCollection.INSTANCE.getEntitiesStorage().stream().anyMatch(predicate);
		}

		@Override
		public void removeEntities(Predicate<Person> predicate) {
			ShareCollection.INSTANCE.getEntitiesStorage().removeAll(filterEntities(predicate).collect(Collectors.toList()));
		}
	}
}
